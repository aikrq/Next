package ru.aikrq.next.presentation.screen.projects

import android.Manifest
import android.annotation.SuppressLint
import android.os.Environment
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.WindowInsetsSides
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.only
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.systemBars
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.Sort
import androidx.compose.material3.ExperimentalMaterial3ExpressiveApi
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.LoadingIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Immutable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.MultiplePermissionsState
import com.google.accompanist.permissions.rememberMultiplePermissionsState
import ru.aikrq.next.model.projects.SketchwareProject
import ru.aikrq.next.presentation.component.ProjectItem
import ru.aikrq.next.presentation.component.RestoreProjectItem
import ru.aikrq.next.presentation.theme.ADAPTIVE_SURFACE_CONTAINER

@SuppressLint("NewApi")
@OptIn(ExperimentalMaterial3ExpressiveApi::class, ExperimentalPermissionsApi::class)
@Composable
fun ProjectsScreen(
    padding: PaddingValues,
    onPermissionNeed: @Composable (permissionsState: MultiplePermissionsState, allFilesAccessCheckCallback: @Composable () -> Unit) -> Unit,
    allFilesAccessCheck: @Composable (() -> Unit) -> Unit
) {
    val viewModel: ProjectsViewModel = viewModel()
    val uiState by viewModel.uiState.collectAsState()
    val totalPermissions = rememberMultiplePermissionsState(
        permissions = listOf(
            Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE
        )
    )

    if (!totalPermissions.allPermissionsGranted) {
        onPermissionNeed(totalPermissions) {
            allFilesAccessCheck {
                viewModel.loadProjects()
            }
        }
    }

    if (totalPermissions.allPermissionsGranted && Environment.isExternalStorageManager()) {
        viewModel.loadProjects()
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(padding)
            .windowInsetsPadding(WindowInsets.systemBars.only(WindowInsetsSides.Horizontal))
    ) {
        RestoreProjectItem()

        Box(
            modifier = Modifier
                .padding(top = 8.dp)
                .fillMaxSize()
                .clip(RoundedCornerShape(topStart = 18.dp, topEnd = 18.dp))
                .background(ADAPTIVE_SURFACE_CONTAINER),
        ) {
            Column {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(start = 16.dp, top = 8.dp, end = 4.dp, bottom = 4.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "Projects",
                        modifier = Modifier.weight(1f),
                        style = MaterialTheme.typography.titleLarge.copy(fontWeight = FontWeight.Medium)
                    )
                    Icon(
                        modifier = Modifier
                            .size(48.dp)
                            .padding(12.dp),
                        imageVector = Icons.AutoMirrored.Filled.Sort,
                        contentDescription = null
                    )
                }

                when (uiState) {
                    is ProjectsUiState.Loading -> {
                        Box(
                            modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center
                        ) {
                            LoadingIndicator()
                        }
                    }

                    is ProjectsUiState.Success -> {
                        ProjectList(ImmutableList((uiState as ProjectsUiState.Success).projects))
                    }
                }
            }
        }
    }
}

@Immutable
data class ImmutableList<T>(
    val wrapped: List<T> = listOf()
) : List<T> by wrapped

@Composable
fun ProjectList(
    projects: ImmutableList<SketchwareProject>,
    modifier: Modifier = Modifier
) {
    val lazyListState = rememberLazyListState()
    val topShape = remember {
        RoundedCornerShape(
            topStart = 18.dp, topEnd = 18.dp, bottomStart = 4.dp, bottomEnd = 4.dp
        )
    }
    val middleShape = remember { RoundedCornerShape(4.dp) }
    val bottomShape = remember {
        RoundedCornerShape(
            bottomStart = 18.dp,
            bottomEnd = 18.dp,
            topStart = 4.dp,
            topEnd = 4.dp,
        )
    }

    LazyColumn(
        state = lazyListState,
        modifier = modifier,
    ) {
        itemsIndexed(
            items = projects,
            key = { index, project -> project.getConfig().projectId }) { index, project ->
            ProjectItem(
                modifier = Modifier.padding(
                    bottom = when (index) {
                        projects.size - 1 -> 8.dp
                        else -> 0.dp
                    }
                ),
                configModel = project.getConfig(),
                locations = project.locations,
                shape = when (index) {
                    0 -> topShape
                    projects.size - 1 -> bottomShape
                    else -> middleShape
                }
            )

            if (index != projects.size - 1) {
                HorizontalDivider(
                    thickness = 4.dp, color = ADAPTIVE_SURFACE_CONTAINER
                )
            }
        }
    }
}