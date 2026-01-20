package com.mako.taskmngr.data.local.dao

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.mako.taskmngr.data.local.entity.TaskDataEntity
import com.mako.taskmngr.data.local.entity.TaskDataStatus

@Dao
interface TasksDao {
    @Query("SELECT * FROM tasks")
    fun getAllTasks(): PagingSource<Int, TaskDataEntity>

    @Query(
        """
        SELECT * FROM tasks 
        WHERE 
            (title LIKE '%' || :searchQuery || '%') 
            AND 
            (status IN (:statuses))
        ORDER BY id DESC
    """
    )
    fun getFilteredTasks(
        searchQuery: String,
        statuses: List<TaskDataStatus>
    ): PagingSource<Int, TaskDataEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun saveTask(task: TaskDataEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun saveTasks(task: List<TaskDataEntity>)

    @Update
    suspend fun updateTask(task: TaskDataEntity)

    @Query("DELETE FROM tasks WHERE id = :id")
    suspend fun deleteById(id: Long)

    @Query("SELECT * FROM tasks WHERE id = :id")
    suspend fun getTaskById(id: Long): TaskDataEntity
}
