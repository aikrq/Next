/*******************************************************************************
 * Copyright (c) 2025. Aikrq
 * All rights reserved.
 ******************************************************************************/

package ru.aikrq.next.presentation.screen

import android.os.Build
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts.RequestMultiplePermissions
import androidx.activity.result.contract.ActivityResultContracts.StartActivityForResult
import androidx.annotation.RequiresApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.displayCutout
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.systemBars
import androidx.compose.foundation.layout.union
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Construction
import androidx.compose.material.icons.filled.Storage
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.ListItem
import androidx.compose.material3.ListItemDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.compose.LocalLifecycleOwner
import androidx.lifecycle.viewmodel.compose.viewModel
import ru.aikrq.next.core.manager.PermissionManager.Companion.REQUIRED_PERMISSIONS
import ru.aikrq.next.presentation.theme.ADAPTIVE_SURFACE_CONTAINER
import ru.aikrq.next.presentation.theme.NextTheme
import ru.aikrq.next.presentation.theme.ON_SURFACE_VARIANT_ALPHA

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PermissionScreen(onPermissionGiven: () -> Unit) {
    val viewModel: PermissionViewModel = viewModel()
    val state = viewModel.state.collectAsState()
    val lifecycleOwner = LocalLifecycleOwner.current
    val lifecycleState by lifecycleOwner.lifecycle.currentStateFlow.collectAsState()
    var hasRequestedPermissions by remember { mutableStateOf(false) }

    val requestPermissionsLauncher =
        rememberLauncherForActivityResult(RequestMultiplePermissions()) { _ ->
            hasRequestedPermissions = true
            viewModel.onPermissionChange()
        }

    val requestAllFilesPermissionLauncher =
        rememberLauncherForActivityResult(StartActivityForResult()) { _ ->
            viewModel.onPermissionChange()
        }

    LaunchedEffect(lifecycleState) {
        when (lifecycleState) {
            Lifecycle.State.RESUMED -> viewModel.checkPermissions()
            else -> {}
        }
    }

    @RequiresApi(Build.VERSION_CODES.R)
    fun openSettings() {
        requestAllFilesPermissionLauncher.launch(viewModel.createSettingsIntent())
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(20.dp)
            .windowInsetsPadding(WindowInsets.systemBars.union(WindowInsets.displayCutout))
    ) {
        Icon(
            modifier = Modifier
                .padding(top = 24.dp)
                .size(48.dp)
                .clip(CircleShape)
                .background(ADAPTIVE_SURFACE_CONTAINER)
                .padding(8.dp),
            imageVector = Icons.Default.Construction,
            contentDescription = null
        )

        Text(
            modifier = Modifier.padding(top = 16.dp),
            text = "Grant permissions for the application",
            style = MaterialTheme.typography.headlineMedium
        )

        Text(
            modifier = Modifier.padding(top = 12.dp),
            text = "You have to grant access to these permissions in order to use the app.",
            color = ON_SURFACE_VARIANT_ALPHA,
            style = MaterialTheme.typography.bodyLarge
        )

        LazyColumn(
            modifier = Modifier
                .padding(top = 20.dp)
                .clip(RoundedCornerShape(18.dp))
        ) {
            item {
                ListItem(
                    headlineContent = { Text("Storage access") },
                    supportingContent = { Text("To to save and load projects") },
                    trailingContent = { PermissionAccessIcon(state.value.hasStorageAccess) },
                    leadingContent = {
                        Icon(
                            imageVector = Icons.Default.Storage,
                            contentDescription = null
                        )
                    },
                    colors = ListItemDefaults.colors(
                        containerColor = ADAPTIVE_SURFACE_CONTAINER,
                        leadingIconColor = MaterialTheme.colorScheme.surfaceTint,
                        supportingColor = ON_SURFACE_VARIANT_ALPHA
                    )
                )
            }

            item {
                Spacer(Modifier.height(4.dp))
            }

            item {
                ListItem(
                    headlineContent = { Text("Manage files access") },
                    supportingContent = { Text("To avoid taking ages to build projects") },
                    trailingContent = { PermissionAccessIcon(state.value.hasFilesAccess) },
                    leadingContent = {
                        Icon(
                            imageVector = Icons.Default.Construction,
                            contentDescription = null,
                        )
                    },
                    colors = ListItemDefaults.colors(
                        containerColor = ADAPTIVE_SURFACE_CONTAINER,
                        leadingIconColor = MaterialTheme.colorScheme.surfaceTint,
                        supportingColor = ON_SURFACE_VARIANT_ALPHA
                    )
                )
            }
        }

        Spacer(Modifier.weight(1f))

        HorizontalDivider(
            modifier = Modifier.padding(vertical = 16.dp),
            color = MaterialTheme.colorScheme.surfaceContainerHighest
        )

        @Composable
        fun requestAllFilesButton() {
            Button(
                modifier = Modifier.fillMaxWidth(),
                onClick = { openSettings() }
            ) {
                Text("Request manage files permission")
            }
        }

        if (state.value.hasStorageAccess) {
            if (Build.VERSION.SDK_INT >= 30 && !state.value.hasFilesAccess) {
                requestAllFilesButton()
            } else {
                Button(
                    modifier = Modifier.fillMaxWidth(),
                    onClick = onPermissionGiven
                ) {
                    Text("Get started")
                }
            }
        } else {
            if (!state.value.hasFilesAccess && (hasRequestedPermissions && state.value.hasStorageAccess)) {
                requestAllFilesButton()
            } else {
                Button(
                    modifier = Modifier.fillMaxWidth(),
                    onClick = { requestPermissionsLauncher.launch(REQUIRED_PERMISSIONS) }
                ) {
                    Text("Request permissions")
                }
            }
        }
    }
}

@Composable
fun PermissionAccessIcon(hasAccess: Boolean) {
    if (hasAccess) {
        Icon(
            Icons.Filled.Check,
            contentDescription = null
        )
    } else {
        Icon(
            Icons.Filled.Close,
            contentDescription = null
        )
    }
}

@Preview
@Composable
fun PermissionScreenPreview() {
    NextTheme {
        PermissionScreen(
            onPermissionGiven = {}
        )
    }
}