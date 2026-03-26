package com.example.tudee.data.local.database.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.example.tudee.data.local.database.entity.TasksEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface TasksDao {
    @Query("SELECT * FROM tasks_table")
    fun getAllTasks(): Flow<List<TasksEntity>>


    @Insert
    suspend fun insertTask(task: TasksEntity)

    @Update
    suspend fun updateTask(task: TasksEntity)


    @Delete
    suspend fun deleteTask(task: TasksEntity)

    @Query("Select * from tasks_table Where categoryId = :categoryId")
    suspend fun getTasksByCategoryId(categoryId: Int): List<TasksEntity>
}