package com.mako.taskmngr.presentation.task_list

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.mako.taskmngr.domain.usecase.DeleteTaskByIdUseCase
import com.mako.taskmngr.domain.usecase.DeleteTaskByIdUseCaseArgs
import com.mako.taskmngr.domain.usecase.FetchFilteredTasksUseCase
import com.mako.taskmngr.domain.usecase.FetchFilteredTasksUseCaseArgs
import com.mako.taskmngr.domain.usecase.GenerateTasksUseCase
import com.mako.taskmngr.presentation.entities.TaskItemPresentationEntity
import com.mako.taskmngr.presentation.entities.TaskPresentationEntity
import com.mako.taskmngr.presentation.entities.TaskPresentationStatus
import com.mako.taskmngr.presentation.task_list.entities.TaskManagerListEffect
import com.mako.taskmngr.presentation.task_list.entities.TaskManagerListIntent
import com.mako.taskmngr.presentation.task_list.entities.TaskManagerListState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@OptIn(ExperimentalCoroutinesApi::class, FlowPreview::class)
@HiltViewModel
class TaskListViewModel @Inject constructor(
    private val fetchFilteredTasksUseCase: FetchFilteredTasksUseCase,
    private val deleteTaskByIdUseCase: DeleteTaskByIdUseCase,
    private val generateTasksUseCase: GenerateTasksUseCase,
) : ViewModel() {

    private val _state = MutableStateFlow(TaskManagerListState())
    val state: StateFlow<TaskManagerListState> = _state.asStateFlow()

    private val _effect = MutableSharedFlow<TaskManagerListEffect>()
    val effect: SharedFlow<TaskManagerListEffect> = _effect.asSharedFlow()

    private val filterArgs = state
        .map { state ->
            FetchFilteredTasksUseCaseArgs(
                searchQuery = state.searchQuery,
                selectedStatuses = state.selectedStatuses
            )
        }
        .debounce(300)
        .distinctUntilChanged()

    val tasks: Flow<PagingData<TaskItemPresentationEntity>> = filterArgs
        .flatMapLatest { args ->
            fetchFilteredTasksUseCase(args)
        }
        .cachedIn(viewModelScope)

    fun handleIntent(intent: TaskManagerListIntent) {
        when (intent) {
            is TaskManagerListIntent.Navigation -> onNavigation(intent.id)
            TaskManagerListIntent.AddTask -> onNavigation(TaskPresentationEntity.NO_ID)
            is TaskManagerListIntent.DeleteTask -> deleteTask(intent.id)
            TaskManagerListIntent.GenerateTasks -> generateTasks()
            is TaskManagerListIntent.UpdateSearchQuery -> updateSearchQuery(intent.query)
            is TaskManagerListIntent.ToggleStatus -> toggleStatus(intent.status)
            TaskManagerListIntent.ClearFilters -> clearFilters()
        }
    }

    private fun updateSearchQuery(query: String) {
        _state.update { it.copy(searchQuery = query) }
    }

    private fun toggleStatus(status: TaskPresentationStatus) {
        _state.update { currentState ->
            val newStatuses = if (status in currentState.selectedStatuses) {
                currentState.selectedStatuses - status
            } else {
                currentState.selectedStatuses + status
            }
            currentState.copy(selectedStatuses = newStatuses)
        }
    }

    private fun clearFilters() {
        _state.update { it.copy(searchQuery = "", selectedStatuses = emptySet()) }
    }

    private fun onNavigation(id: Long) {
        Log.d("TaskListViewModel", "onNavigation: $id")
        viewModelScope.launch { _effect.emit(TaskManagerListEffect.Navigation(id)) }
    }

    private fun deleteTask(id: Long) {
        Log.d("TaskListViewModel", "deleteTask: $id")
        viewModelScope.launch {
            try {
                deleteTaskByIdUseCase(DeleteTaskByIdUseCaseArgs(id))
            } catch (e: Exception) {
                Log.e("TaskListViewModel", "Error deleting task: ${e.message}")
            }
        }
    }

    private fun generateTasks() {
        viewModelScope.launch {
            try {
                generateTasksUseCase()
            } catch (e: Exception) {
                Log.e("TaskListViewModel", "Error generating tasks: ${e.message}")
            }
        }
    }
}
