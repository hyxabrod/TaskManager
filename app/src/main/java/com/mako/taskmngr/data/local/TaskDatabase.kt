package com.mako.taskmngr.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.mako.taskmngr.data.local.dao.TasksDao
import com.mako.taskmngr.data.local.entity.TaskDataEntity

@Database(entities = [TaskDataEntity::class], version = 1, exportSchema = false)
abstract class TaskDatabase : RoomDatabase() {
    abstract fun tasksDao(): TasksDao
}
