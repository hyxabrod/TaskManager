package com.mako.taskmngr.domain.usecase

import com.mako.taskmngr.core.VoidUseCaseWithArgs
import com.mako.taskmngr.domain.entity.NewTaskDomainEntity
import com.mako.taskmngr.domain.entity.TaskDomainStatus
import com.mako.taskmngr.domain.repository.TasksRepository
import com.mako.taskmngr.presentation.entities.TaskPresentationStatus
import javax.inject.Inject

class SaveNewTaskByIdUseCase @Inject constructor(private val repository: TasksRepository) :
    VoidUseCaseWithArgs<SaveNewTaskByIdUseCaseArgs> {

    override suspend fun invoke(args: SaveNewTaskByIdUseCaseArgs) {
        repository.saveTask(
            NewTaskDomainEntity(
                title = args.title,
                description = args.description,
                status = TaskDomainStatus.fromPresentation(args.status)
            )
        )
    }
}

data class SaveNewTaskByIdUseCaseArgs(
    val title: String,
    val description: String,
    val status: TaskPresentationStatus
) : VoidUseCaseWithArgs.Args()

