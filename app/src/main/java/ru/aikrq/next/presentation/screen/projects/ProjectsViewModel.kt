/*******************************************************************************
 * Copyright (c) 2025. Aikrq
 * All rights reserved.
 ******************************************************************************/

package ru.aikrq.next.presentation.screen.projects

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import ru.aikrq.next.core.manager.SketchwareManager
import ru.aikrq.next.model.projects.SketchwareProject
import javax.inject.Inject

sealed class ProjectsUiState {
    data class Success(val projects: List<SketchwareProject>) : ProjectsUiState()
    object Loading : ProjectsUiState()
}

@HiltViewModel
class ProjectsViewModel @Inject constructor(val manager: SketchwareManager) : ViewModel() {
    private val _uiState = MutableStateFlow<ProjectsUiState>(ProjectsUiState.Loading)
    val uiState: StateFlow<ProjectsUiState> get() = _uiState

    init {
        viewModelScope.launch {
            _uiState.value = ProjectsUiState.Success(
                projects = manager.projectsManager.getProjects()
            )
        }
    }
}