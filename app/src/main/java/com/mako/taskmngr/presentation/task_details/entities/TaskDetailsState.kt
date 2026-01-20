package com.mako.taskmngr.presentation.task_details.entities

import com.mako.taskmngr.presentation.entities.TaskPresentationEntity
import com.mako.taskmngr.presentation.entities.TaskPresentationStatus

data class TaskDetailsState(
    val task: TaskPresentationEntity? = null,
    val title: String = "",
    val description: String = "",
    val status: TaskPresentationStatus = TaskPresentationStatus.TODO,
    val canDelete: Boolean = false,
)
