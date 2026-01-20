package com.mako.taskmngr.presentation.task_list.entities

import com.mako.taskmngr.presentation.entities.TaskPresentationStatus

sealed interface TaskManagerListIntent {
    object AddTask : TaskManagerListIntent
    data class Navigation(val id: Long) : TaskManagerListIntent
    data class DeleteTask(val id: Long) : TaskManagerListIntent
    object GenerateTasks : TaskManagerListIntent
    data class UpdateSearchQuery(val query: String) : TaskManagerListIntent
    data class ToggleStatus(val status: TaskPresentationStatus) : TaskManagerListIntent
    object ClearFilters : TaskManagerListIntent
}
