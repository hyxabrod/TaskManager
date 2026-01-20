package com.mako.taskmngr.domain.usecase

import com.mako.taskmngr.core.VoidUseCaseWithArgs
import com.mako.taskmngr.domain.entity.TaskDomainEntity
import com.mako.taskmngr.domain.entity.TaskDomainStatus
import com.mako.taskmngr.domain.repository.TasksRepository
import com.mako.taskmngr.presentation.entities.TaskPresentationStatus
import javax.inject.Inject

class UpdateTaskByIdUseCase @Inject constructor(private val repository: TasksRepository) :
    VoidUseCaseWithArgs<UpdateTaskByIdUseCaseArgs> {

    override suspend fun invoke(args: UpdateTaskByIdUseCaseArgs) {
        repository.updateTask(
            TaskDomainEntity(
                id = args.id,
                title = args.title,
                description = args.description,
                status = TaskDomainStatus.fromPresentation(args.status)
            )
        )
    }
}

data class UpdateTaskByIdUseCaseArgs(
    val id: Long,
    val title: String,
    val description: String,
    val status: TaskPresentationStatus
) : VoidUseCaseWithArgs.Args()
