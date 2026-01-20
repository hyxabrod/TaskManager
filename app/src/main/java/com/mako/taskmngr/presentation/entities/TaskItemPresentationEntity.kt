package com.mako.taskmngr.presentation.entities

import com.mako.taskmngr.core.PresentationEntity

data class TaskItemPresentationEntity(
    val id: Long,
    val title: String,
    val status: TaskPresentationStatus,
) : PresentationEntity
