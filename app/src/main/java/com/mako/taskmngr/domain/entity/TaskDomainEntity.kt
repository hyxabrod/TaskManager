package com.mako.taskmngr.domain.entity

import com.mako.taskmngr.core.DomainEntity
import com.mako.taskmngr.core.IncomingDomainEntity
import com.mako.taskmngr.presentation.entities.TaskPresentationEntity
import com.mako.taskmngr.presentation.entities.TaskPresentationStatus

data class NewTaskDomainEntity(
    val title: String,
    val description: String,
    val status: TaskDomainStatus,
) : DomainEntity

data class TaskDomainEntity(
    val id: Long,
    val title: String,
    val description: String,
    val status: TaskDomainStatus,
) : IncomingDomainEntity {

    override fun toPresentation(): TaskPresentationEntity {
        return TaskPresentationEntity(
            id = id,
            title = title,
            description = description,
            status = status.toPresentation(),
        )
    }
}

enum class TaskDomainStatus : IncomingDomainEntity {
    TODO,
    IN_PROGRESS,
    DONE;

    override fun toPresentation(): TaskPresentationStatus {
        return TaskPresentationStatus.valueOf(this.name);
    }

    companion object Companion {
        fun fromPresentation(status: TaskPresentationStatus): TaskDomainStatus {
            return TaskDomainStatus.valueOf(status.name);
        }
    }
}

