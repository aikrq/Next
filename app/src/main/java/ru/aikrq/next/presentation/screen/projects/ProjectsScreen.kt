/*******************************************************************************
 * Copyright (c) 2025. Aikrq
 * All rights reserved.
 ******************************************************************************/

package ru.aikrq.next.presentation.screen.projects

import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.WindowInsetsSides
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
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
import androidx.compose.material3.Icon
import androidx.compose.material3.LoadingIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Immutable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import ru.aikrq.next.model.projects.SketchwareProject
import ru.aikrq.next.presentation.component.ProjectItem
import ru.aikrq.next.presentation.component.RestoreProjectItem
import ru.aikrq.next.presentation.theme.ADAPTIVE_SURFACE_CONTAINER

@SuppressLint("NewApi")
@OptIn(ExperimentalMaterial3ExpressiveApi::class, ExperimentalPermissionsApi::class)
@Composable
fun ProjectsScreen(
    padding: PaddingValues,
    onProjectClick: (String) -> Unit,
) {
    val viewModel = hiltViewModel<ProjectsViewModel>()
    val uiState by viewModel.uiState.collectAsState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(padding)
            .windowInsetsPadding(
                WindowInsets.systemBars.only(WindowInsetsSides.Horizontal)
            )
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
fun ProjectList(projects: ImmutableList<SketchwareProject>) {
    val lazyListState = rememberLazyListState()

    LazyColumn(
        state = lazyListState,
        modifier = Modifier
            .padding(end = 16.dp, start = 16.dp, bottom = 16.dp)
            .clip(RoundedCornerShape(18.dp))
    ) {
        itemsIndexed(items = projects) { index, project ->
            ProjectItem(
                model = project.getConfig(),
                locations = project.locations
            )

            if (index != projects.size - 1) {
                Spacer(Modifier.height(4.dp))
            }
        }
    }
}