/*******************************************************************************
 * Copyright (c) 2025. Aikrq
 * All rights reserved.
 ******************************************************************************/

package ru.aikrq.next.presentation.screen.design

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import ru.aikrq.next.model.projects.ProjectConfigModel
import ru.aikrq.next.model.projects.SketchwareProject
import ru.aikrq.next.presentation.component.TopAppBar

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DesignScreen(
    viewModel: DesignViewModel,
    onBack: () -> Unit,
) {
    val uiState by viewModel.uiState.collectAsState()
    var project by remember { mutableStateOf<SketchwareProject?>(null) }
    var config by remember { mutableStateOf<ProjectConfigModel?>(null) }

    LaunchedEffect(uiState) {
        project = uiState.project
    }

    LaunchedEffect(project) {
        config = project?.getConfig()
    }

    if (config != null) {
        Column(
            modifier = Modifier.fillMaxSize()
        ) {
            TopAppBar(
                title = config!!.projectName,
                subtitle = config!!.projectId,
                navigationIcon = Icons.AutoMirrored.Filled.ArrowBack,
                navigationIconOnClick = onBack
            )
        }
    }
}