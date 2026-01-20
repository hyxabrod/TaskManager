package com.mako.taskmngr.di

import com.mako.taskmngr.domain.repository.TasksRepository
import com.mako.taskmngr.domain.usecase.DeleteTaskByIdUseCase
import com.mako.taskmngr.domain.usecase.FetchFilteredTasksUseCase
import com.mako.taskmngr.domain.usecase.GetTaskByIdUseCase
import com.mako.taskmngr.domain.usecase.SaveNewTaskByIdUseCase
import com.mako.taskmngr.domain.usecase.UpdateTaskByIdUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent

@Module
@InstallIn(ViewModelComponent::class)
object UseCaseModule {
    @Provides
    fun provideFetchFilteredTasksUseCase(
        repository: TasksRepository
    ): FetchFilteredTasksUseCase {
        return FetchFilteredTasksUseCase(repository)
    }

    @Provides
    fun provideDeleteTaskByIdUseCase(
        repository: TasksRepository
    ): DeleteTaskByIdUseCase {
        return DeleteTaskByIdUseCase(repository)
    }

    @Provides
    fun provideUpdateTaskByIdUseCase(
        repository: TasksRepository
    ): UpdateTaskByIdUseCase {
        return UpdateTaskByIdUseCase(repository)
    }

    @Provides
    fun provideSaveNewTaskByIdUseCase(
        repository: TasksRepository
    ): SaveNewTaskByIdUseCase {
        return SaveNewTaskByIdUseCase(repository)
    }

    @Provides
    fun provideGetTaskByIdUseCase(
        repository: TasksRepository
    ): GetTaskByIdUseCase {
        return GetTaskByIdUseCase(repository)
    }
}
