package com.mako.taskmngr.presentation.task_list.entities

import com.mako.taskmngr.presentation.entities.TaskPresentationStatus

data class TaskManagerListState(
    val searchQuery: String = "",
    val selectedStatuses: Set<TaskPresentationStatus> = emptySet()
)
