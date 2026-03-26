package com.example.tudee.data.repository

import com.example.tudee.data.local.database.dao.CategoryDao
import com.example.tudee.data.local.database.dao.TasksDao
import com.example.tudee.data.local.database.mapper.toDomain
import com.example.tudee.data.local.database.mapper.toTaskEntity
import com.example.tudee.domain.model.Task
import com.example.tudee.domain.repository.TaskRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class TaskRepositoryImpl @Inject constructor(
    private val tasksDao: TasksDao,
    private val categoryDao: CategoryDao
) : TaskRepository {

    override fun getAllTasks(): Flow<List<Task>> {
        return tasksDao.getAllTasks().map { tasksEntity -> tasksEntity.map { it.toDomain() } }
    }

    override suspend fun insertTask(task: Task) {
        val category = categoryDao.getCategoryById(task.categoryId).toDomain()
        tasksDao.insertTask(task.toTaskEntity(category))
    }

    override suspend fun updateTask(task: Task) {
        val category = categoryDao.getCategoryById(task.categoryId).toDomain()
        tasksDao.updateTask(task.toTaskEntity(category))
    }

    override suspend fun deleteTask(task: Task) {
        val category = categoryDao.getCategoryById(task.categoryId).toDomain()
        tasksDao.deleteTask(task.toTaskEntity(category))
    }

    override suspend fun getTasksByCategoryId(categoryId: Int): List<Task> {
        return tasksDao.getTasksByCategoryId(categoryId).map { entities -> entities.toDomain() }
    }
}
