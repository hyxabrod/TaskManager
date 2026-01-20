package com.mako.taskmngr.domain.usecase

import com.mako.taskmngr.core.UseCaseWithArgs
import com.mako.taskmngr.domain.repository.TasksRepository
import com.mako.taskmngr.presentation.entities.TaskPresentationEntity
import javax.inject.Inject

class GetTaskByIdUseCase @Inject constructor(private val repository: TasksRepository) :
    UseCaseWithArgs<GetTaskByIdUseCaseArgs> {

    override suspend fun invoke(args: GetTaskByIdUseCaseArgs): TaskPresentationEntity {
        val taskEntity = repository.getTaskById(args.id)

        return taskEntity.toPresentation()
    }
}

data class GetTaskByIdUseCaseArgs(val id: Long) : UseCaseWithArgs.Args()
