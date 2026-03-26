package com.example.tudee.data.repository

import com.example.tudee.database.dao.CategoryDao
import com.example.tudee.database.mapper.toCategoryEntity
import com.example.tudee.database.mapper.toDomain
import com.example.tudee.domain.model.Category
import com.example.tudee.domain.repository.CategoryRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class CategoryRepositoryImpl(val categoryDao: CategoryDao) : CategoryRepository {
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
}