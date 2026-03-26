package com.example.tudee.data.repository

import com.example.tudee.data.local.database.dao.CategoryDao
import com.example.tudee.data.local.database.mapper.toCategoryEntity
import com.example.tudee.data.local.database.mapper.toDomain
import com.example.tudee.domain.model.Category
import com.example.tudee.domain.repository.CategoryRepository
import jakarta.inject.Inject
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class CategoryRepositoryImpl @Inject constructor(private val categoryDao: CategoryDao) :
    CategoryRepository {
    override fun getAllCategories(): Flow<List<Category>> {
        return categoryDao.getAllCategories().map { categoriesEntities ->
            categoriesEntities.map { it.toDomain() }
        }
    }

    override suspend fun insertCategory(category: Category) {
        categoryDao.insertCategory(category.toCategoryEntity())
    }

    override suspend fun updateCategory(category: Category) {
        categoryDao.updateCategory(category.toCategoryEntity())
    }

    override suspend fun deleteCategory(categoryId: Int) {
        categoryDao.deleteCategory(categoryId)
    }

    override suspend fun getCategoryById(categoryId: Int): Category {
        return categoryDao.getCategoryById(categoryId).toDomain()
    }
}