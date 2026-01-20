package com.mako.taskmngr.di

import com.mako.taskmngr.data.repository.TasksRepositoryImpl
import com.mako.taskmngr.domain.repository.TasksRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {
    @Binds
    @Singleton
    abstract fun bindTasksRepository(tasksRepositoryImpl: TasksRepositoryImpl): TasksRepository
}
