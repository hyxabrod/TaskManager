package com.mako.taskmngr.domain.entity

import com.mako.taskmngr.core.IncomingDomainEntity
import com.mako.taskmngr.presentation.entities.TaskItemPresentationEntity

data class TaskItemIncomingDomainEntity(
    val id: Long,
    val title: String,
    val status: TaskDomainStatus,
) : IncomingDomainEntity {

    override fun toPresentation(): TaskItemPresentationEntity {
        return TaskItemPresentationEntity(
            id = id,
            title = title,
            status = status.toPresentation(),
        )
    }
}
