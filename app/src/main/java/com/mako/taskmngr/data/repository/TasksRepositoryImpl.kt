package com.mako.taskmngr.data.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.PagingSource
import androidx.paging.map
import com.mako.taskmngr.data.local.dao.TasksDao
import com.mako.taskmngr.data.local.entity.TaskDataEntity
import com.mako.taskmngr.data.local.entity.TaskDataStatus
import com.mako.taskmngr.domain.entity.NewTaskDomainEntity
import com.mako.taskmngr.domain.entity.TaskDomainEntity
import com.mako.taskmngr.domain.entity.TaskFilterDomainEntity
import com.mako.taskmngr.domain.entity.TaskItemIncomingDomainEntity
import com.mako.taskmngr.domain.repository.TasksRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class TasksRepositoryImpl
@Inject
constructor(private val dao: TasksDao) : TasksRepository {
    override fun getAllTasks(
        filter: TaskFilterDomainEntity
    ): Flow<PagingData<TaskItemIncomingDomainEntity>> {
        return Pager(
            config = PagingConfig(
                pageSize = TasksRepository.PAGE_SIZE,
                prefetchDistance = 5,
                enablePlaceholders = false,
                initialLoadSize = TasksRepository.PAGE_SIZE * 2,
            ),
            pagingSourceFactory = { selectPagingSource(filter) }
        ).flow
            .map { pagingData ->
                pagingData.map { entity ->
                    TaskItemIncomingDomainEntity(
                        id = entity.id,
                        title = entity.title,
                        status = entity.status.toDomain()
                    )
                }
            }
    }

    private fun selectPagingSource(
        filter: TaskFilterDomainEntity
    ): PagingSource<Int, TaskDataEntity> {
        val result = dao.getFilteredTasks(
            filter.searchQuery,
            filter.selectedStatuses.map { TaskDataStatus.fromDomain(it) });

        return result;
    }

    override suspend fun saveTask(task: NewTaskDomainEntity) {
        dao.saveTask(
            TaskDataEntity(
                title = task.title,
                description = task.description,
                status = TaskDataStatus.fromDomain(task.status)
            )
        )
    }

    override suspend fun saveTasks(tasks: List<NewTaskDomainEntity>) {
        dao.saveTasks(
            tasks.map { task ->
                TaskDataEntity(
                    title = task.title,
                    description = task.description,
                    status = TaskDataStatus.fromDomain(task.status)
                )
            }
        )
    }

    override suspend fun updateTask(task: TaskDomainEntity) {
        dao.updateTask(
            TaskDataEntity(
                id = task.id,
                title = task.title,
                description = task.description,
                status = TaskDataStatus.fromDomain(task.status)
            )
        )
    }

    override suspend fun deleteTask(id: Long) {
        dao.deleteById(id)
    }

    override suspend fun getTaskById(id: Long): TaskDomainEntity {
        return dao.getTaskById(id).toDomain()
    }
}
