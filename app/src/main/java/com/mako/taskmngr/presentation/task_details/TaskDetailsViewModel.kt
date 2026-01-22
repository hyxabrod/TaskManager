package com.mako.taskmngr.presentation.task_details

import android.util.Log
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.toRoute
import com.mako.taskmngr.domain.usecase.DeleteTaskByIdUseCase
import com.mako.taskmngr.domain.usecase.DeleteTaskByIdUseCaseArgs
import com.mako.taskmngr.domain.usecase.GetTaskByIdUseCase
import com.mako.taskmngr.domain.usecase.GetTaskByIdUseCaseArgs
import com.mako.taskmngr.domain.usecase.SaveNewTaskByIdUseCase
import com.mako.taskmngr.domain.usecase.SaveNewTaskByIdUseCaseArgs
import com.mako.taskmngr.domain.usecase.UpdateTaskByIdUseCase
import com.mako.taskmngr.domain.usecase.UpdateTaskByIdUseCaseArgs
import com.mako.taskmngr.navigation.Details
import com.mako.taskmngr.presentation.entities.TaskPresentationEntity
import com.mako.taskmngr.presentation.task_details.entities.TaskDetailsEffect
import com.mako.taskmngr.presentation.task_details.entities.TaskDetailsIntent
import com.mako.taskmngr.presentation.task_details.entities.TaskDetailsState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TaskDetailsViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val getTaskByIdUseCase: GetTaskByIdUseCase,
    private val updateTaskUseCase: UpdateTaskByIdUseCase,
    private val saveTaskUseCase: SaveNewTaskByIdUseCase,
    private val deleteTaskUseCase: DeleteTaskByIdUseCase,
) : ViewModel() {

    private val details: Details = savedStateHandle.toRoute()
    private val taskId = details.taskId

    private val _state = MutableStateFlow(TaskDetailsState())
    val state: StateFlow<TaskDetailsState> = _state.asStateFlow()

    private val _effect = Channel<TaskDetailsEffect>(Channel.BUFFERED)
    val effect: Flow<TaskDetailsEffect> = _effect.receiveAsFlow()

    init {
        loadTask()
    }

    private fun loadTask() {
        if (taskId == TaskPresentationEntity.NO_ID) {
            return
        }

        viewModelScope.launch {
            try {
                val task = getTaskByIdUseCase(GetTaskByIdUseCaseArgs(taskId))
                _state.update {
                    it.copy(
                        task = task,
                        title = task.title,
                        description = task.description,
                        status = task.status,
                        canDelete = true,
                    )
                }
            } catch (e: Exception) {
                Log.e("TaskDetailsViewModel", "Failed to load task: ${e.message}")
                _effect.send(TaskDetailsEffect.ShowSnackbar("Failed to load task"))
            }
        }
    }

    fun handleIntent(intent: TaskDetailsIntent) {
        when (intent) {
            is TaskDetailsIntent.Title -> onTitleIntent(intent.title)
            is TaskDetailsIntent.Description -> onDescriptionIntent(intent)
            is TaskDetailsIntent.Status -> onStatusIntent(intent)
            is TaskDetailsIntent.SaveTask -> onSaveTaskIntent()
            TaskDetailsIntent.DeleteTask -> onDeleteTaskIntent()
            TaskDetailsIntent.BackNavigation -> onBackNavigationIntent()
        }
    }

    private fun onStatusIntent(intent: TaskDetailsIntent.Status) {
        _state.update { it.copy(status = intent.status) }
    }

    private fun onDescriptionIntent(intent: TaskDetailsIntent.Description) {
        _state.update { it.copy(description = intent.description) }
    }

    private fun onTitleIntent(title: String) {
        _state.update { it.copy(title = title) }
    }

    private fun onBackNavigationIntent() {
        navigateBack()
    }

    private fun navigateBack() {
        viewModelScope.launch {
            _effect.send(TaskDetailsEffect.BackNavigation)
        }
    }

    private fun onDeleteTaskIntent() {
        viewModelScope.launch {
            try {
                deleteTaskUseCase(
                    DeleteTaskByIdUseCaseArgs(id = taskId)
                )
                Log.d("TaskDetailsViewModel", "Delete")
                navigateBack()
            } catch (e: Exception) {
                Log.e("TaskDetailsViewModel", "Failed to delete: ${e.message}")
                _effect.send(TaskDetailsEffect.ShowSnackbar("Failed to delete"))
            }
        }
    }

    private fun onSaveTaskIntent() {
        viewModelScope.launch {
            try {
                if (taskId == TaskPresentationEntity.NO_ID) {
                    saveTask()
                } else {
                    updateTask()
                }
                _state.update { it.copy(canDelete = true) }
                Log.d("TaskDetailsViewModel", "Saved")
                _effect.send(TaskDetailsEffect.ShowSnackbar("Saved!"))
                navigateBack()
            } catch (e: Exception) {
                Log.e("TaskDetailsViewModel", "Failed to save: ${e.message}")
                _effect.send(TaskDetailsEffect.ShowSnackbar("Failed to save"))
            }
        }
    }

    private suspend fun updateTask() {
        updateTaskUseCase(
            UpdateTaskByIdUseCaseArgs(
                id = taskId,
                title = _state.value.title,
                description = _state.value.description,
                status = _state.value.status
            )
        )
    }

    private suspend fun saveTask() {
        saveTaskUseCase(
            SaveNewTaskByIdUseCaseArgs(
                title = _state.value.title,
                description = _state.value.description,
                status = _state.value.status
            )
        )
    }
}
