package com.example.tudee.domain.repository

import com.example.tudee.domain.model.Task
import kotlinx.coroutines.flow.Flow

interface TaskRepository {

    fun getAllTasks(): Flow<List<Task>>

    suspend fun insertTask(task: Task)

    suspend fun updateTask(task: Task)


    suspend fun deleteTask(task: Task)

    suspend fun getTasksByCategoryId(categoryId: Int): List<Task>
}