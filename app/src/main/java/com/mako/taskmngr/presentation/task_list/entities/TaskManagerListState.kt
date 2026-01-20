package com.mako.taskmngr.presentation.task_list.entities

import com.mako.taskmngr.presentation.entities.TaskPresentationStatus

data class TaskManagerListState(
    val isLoading: Boolean = false,
    val searchQuery: String = "",
    val selectedStatuses: Set<TaskPresentationStatus> = emptySet()
)
