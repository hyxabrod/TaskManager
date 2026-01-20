package com.mako.taskmngr.di

import android.app.Application
import androidx.room.Room
import com.mako.taskmngr.data.local.TaskDatabase
import com.mako.taskmngr.data.local.dao.TasksDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Provides
    @Singleton
    fun provideTasksDatabase(app: Application): TaskDatabase {
        return Room.databaseBuilder(app, TaskDatabase::class.java, "tasks_db")
            .build()
    }

    @Provides
    @Singleton
    fun provideTasksDao(db: TaskDatabase): TasksDao {
        return db.tasksDao()
    }
}
