package com.example.tudee.presentation.screens.categories

import com.example.tudee.database.entity.CategoryEntity
import com.example.tudee.presentation.screens.home.TaskStatus

data class CategoryUiState(
    val categoryTitle: String = "",
    val selectedCategoryImage: String? = null,
    val categories: List<CategoryEntity> = emptyList(),
    val showBottomSheet: Boolean = false,
    var selectedTab: TaskStatus = TaskStatus.IN_PROGRESS,
)