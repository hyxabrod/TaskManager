package com.mako.taskmngr.domain.repository

import androidx.paging.PagingData
import com.mako.taskmngr.domain.entity.NewTaskDomainEntity
import com.mako.taskmngr.domain.entity.TaskDomainEntity
import com.mako.taskmngr.domain.entity.TaskFilterDomainEntity
import com.mako.taskmngr.domain.entity.TaskItemIncomingDomainEntity
import kotlinx.coroutines.flow.Flow

interface TasksRepository {
    fun getAllTasks(
        filter: TaskFilterDomainEntity = TaskFilterDomainEntity()
    ): Flow<PagingData<TaskItemIncomingDomainEntity>>

    suspend fun saveTask(task: NewTaskDomainEntity)
    suspend fun saveTasks(tasks: List<NewTaskDomainEntity>)
    suspend fun updateTask(task: TaskDomainEntity)
    suspend fun deleteTask(id: Long)
    suspend fun getTaskById(id: Long): TaskDomainEntity

    companion object Companion {
        const val PAGE_SIZE: Int = 20
    }
}

class UpgradeRequiredException : Exception()
