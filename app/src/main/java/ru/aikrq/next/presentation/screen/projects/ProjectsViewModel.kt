package ru.aikrq.next.presentation.screen.projects

import android.os.Environment
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import ru.aikrq.next.core.manager.SketchwareManager
import ru.aikrq.next.model.projects.SketchwareProject
import java.io.File

sealed class ProjectsUiState {
    data class Success(val projects: List<SketchwareProject>) : ProjectsUiState()
    object Loading : ProjectsUiState()
}

class ProjectsViewModel : ViewModel() {
    private val _uiState = MutableStateFlow<ProjectsUiState>(ProjectsUiState.Loading)
    val uiState: StateFlow<ProjectsUiState> get() = _uiState
    val manager = SketchwareManager(
        sketchwareFolder = File(Environment.getExternalStorageDirectory(), ".sketchware")
    )

    fun loadProjects() {
        viewModelScope.launch {
            _uiState.value = ProjectsUiState.Success(
                projects = manager.projectsManager.getProjects()
            )
        }
    }
}