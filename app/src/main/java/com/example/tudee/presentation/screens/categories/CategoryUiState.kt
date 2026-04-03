package com.example.tudee.presentation.screens.categories

import com.example.tudee.domain.model.Category
import com.example.tudee.domain.model.Task
import com.example.tudee.presentation.screens.home.components.TaskStatus

data class CategoryUiState(
    val categoryTitle: String = "",
    val selectedCategoryImage: String? = null,
    val categories: List<Category> = emptyList(),
    val showBottomSheet: Boolean = false,
    val selectedTab: TaskStatus = TaskStatus.IN_PROGRESS,
    val clickedCategory: Category? = null,
    val tasksPerCategory: List<Task> = emptyList(),
    val todoTasks : List<Task>? = emptyList(),
    val inProgressTasks : List<Task>? = emptyList(),
    val doneTasks : List<Task>? = emptyList(),
)