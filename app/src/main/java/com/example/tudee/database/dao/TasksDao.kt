package com.example.tudee.database.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.example.tudee.database.entity.TasksEntity
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





}