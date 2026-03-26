package com.example.tudee.data.repository

import com.example.tudee.database.dao.TasksDao
import com.example.tudee.database.mapper.toDomain
import com.example.tudee.database.mapper.toTaskEntity
import com.example.tudee.domain.model.Task
import com.example.tudee.domain.repository.TaskRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class TaskRepositoryImpl(val tasksDao: TasksDao) : TaskRepository {

    override fun getAllTasks(): Flow<List<Task>> {
        return tasksDao.getAllTasks().map { tasksEntity -> tasksEntity.map { it.toDomain() } }
    }

    override suspend fun insertTask(task: Task) {
        tasksDao.insertTask(task.toTaskEntity())
    }

    override suspend fun updateTask(task: Task) {
        tasksDao.updateTask(task.toTaskEntity())
    }

    override suspend fun deleteTask(task: Task) {
        tasksDao.deleteTask(task.toTaskEntity())
    }

}