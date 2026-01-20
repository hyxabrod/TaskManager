package com.mako.taskmngr.presentation.task_details.entities

import com.mako.taskmngr.presentation.entities.TaskPresentationStatus

sealed interface TaskDetailsIntent {
    data class Title(val title: String) : TaskDetailsIntent
    data class Description(val description: String) : TaskDetailsIntent
    data class Status(val status: TaskPresentationStatus) : TaskDetailsIntent
    object SaveTask : TaskDetailsIntent
    object DeleteTask : TaskDetailsIntent
    object BackNavigation : TaskDetailsIntent
}