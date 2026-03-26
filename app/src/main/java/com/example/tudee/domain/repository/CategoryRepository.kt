package com.example.tudee.domain.repository

import com.example.tudee.domain.model.Category
import kotlinx.coroutines.flow.Flow

interface CategoryRepository {

    fun getAllCategories(): Flow<List<Category>>

    suspend fun insertCategory(category: Category)

    suspend fun updateCategory(category: Category)

    suspend fun deleteCategory(categoryId: Int)

    suspend fun getCategoryById(categoryId: Int): Category

}