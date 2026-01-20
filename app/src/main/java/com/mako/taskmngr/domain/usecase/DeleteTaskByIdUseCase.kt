package com.mako.taskmngr.domain.usecase

import com.mako.taskmngr.core.VoidUseCaseWithArgs
import com.mako.taskmngr.domain.repository.TasksRepository
import javax.inject.Inject

class DeleteTaskByIdUseCase @Inject constructor(private val repository: TasksRepository) :
    VoidUseCaseWithArgs<DeleteTaskByIdUseCaseArgs> {

    override suspend fun invoke(args: DeleteTaskByIdUseCaseArgs) {
        repository.deleteTask(args.id)
    }
}

data class DeleteTaskByIdUseCaseArgs(val id: Long) : VoidUseCaseWithArgs.Args()
