package com.example.tudee.presentation.screens.categories

import com.example.tudee.database.entity.CategoryEntity

data class CategoryUiState(
    val categoryTitle: String = "",
    val selectedCategoryImage: String? = null,
    val categories: List<CategoryEntity> = emptyList(),
    val showBottomSheet: Boolean = false
)