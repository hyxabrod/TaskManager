package com.mako.taskmngr.domain.usecase

import androidx.paging.PagingData
import androidx.paging.map
import com.mako.taskmngr.core.PagedFlowUseCaseWithArgs
import com.mako.taskmngr.domain.entity.TaskDomainStatus
import com.mako.taskmngr.domain.entity.TaskFilterDomainEntity
import com.mako.taskmngr.domain.repository.TasksRepository
import com.mako.taskmngr.presentation.entities.TaskItemPresentationEntity
import com.mako.taskmngr.presentation.entities.TaskPresentationStatus
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class FetchFilteredTasksUseCase @Inject constructor(private val repository: TasksRepository) :
    PagedFlowUseCaseWithArgs<FetchFilteredTasksUseCaseArgs> {
    override fun invoke(
        args: FetchFilteredTasksUseCaseArgs
    ): Flow<PagingData<TaskItemPresentationEntity>> {
        val allStatuses = TaskPresentationStatus.entries.toSet();
        val filteredStatuses = args.selectedStatuses
        val domainFilter = TaskFilterDomainEntity(
            searchQuery = args.searchQuery,
            selectedStatuses = (filteredStatuses.ifEmpty { allStatuses }).map {
                TaskDomainStatus.fromPresentation(it)
            }.toSet()
        )

        return repository.getAllTasks(domainFilter).map { pagingData ->
            pagingData.map { entity ->
                TaskItemPresentationEntity(
                    id = entity.id,
                    title = entity.title,
                    status = entity.status.toPresentation()
                )
            }
        }
    }
}

data class FetchFilteredTasksUseCaseArgs(
    val searchQuery: String = "", val selectedStatuses: Set<TaskPresentationStatus> = emptySet()
) : PagedFlowUseCaseWithArgs.Args()
