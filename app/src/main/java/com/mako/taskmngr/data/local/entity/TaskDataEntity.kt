package com.mako.taskmngr.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.mako.taskmngr.core.DataEntity
import com.mako.taskmngr.domain.entity.TaskDomainEntity
import com.mako.taskmngr.domain.entity.TaskDomainStatus

@Entity(tableName = "tasks")
data class TaskDataEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val title: String,
    val description: String,
    val status: TaskDataStatus,
) : DataEntity {

    override fun toDomain(): TaskDomainEntity {
        return TaskDomainEntity(
            id = id,
            title = title,
            description = description,
            status = status.toDomain(),
        )
    }
}

enum class TaskDataStatus : DataEntity {
    TODO,
    IN_PROGRESS,
    DONE;

    override fun toDomain(): TaskDomainStatus {
        return TaskDomainStatus.valueOf(this.name);
    }

    companion object {
        fun fromDomain(status: TaskDomainStatus): TaskDataStatus {
            return TaskDataStatus.valueOf(status.name);
        }
    }
}
