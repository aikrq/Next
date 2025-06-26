/*******************************************************************************
 * Copyright (c) 2025. Aikrq
 * All rights reserved.
 ******************************************************************************/

package ru.aikrq.next.presentation.screen.design

import androidx.compose.runtime.Stable
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import ru.aikrq.next.core.manager.SketchwareManager
import ru.aikrq.next.model.projects.SketchwareProject
import ru.aikrq.next.presentation.screen.Design

@Stable
data class DesignUiState(val project: SketchwareProject? = null)

@HiltViewModel(assistedFactory = DesignViewModel.Factory::class)
class DesignViewModel @AssistedInject constructor(
    private val manager: SketchwareManager,
    @Assisted val navKey: Design,
) : ViewModel() {
    private val _uiState = MutableStateFlow<DesignUiState>(DesignUiState())
    val uiState: StateFlow<DesignUiState> get() = _uiState

    init {
        viewModelScope.launch {
            val projects = manager.projectsManager.getProjects()
            _uiState.value =
                DesignUiState(
                    project = projects.find { it.getConfig().projectId == navKey.scId })
        }
    }

    @AssistedFactory
    interface Factory {
        fun create(navKey: Design): DesignViewModel
    }
}