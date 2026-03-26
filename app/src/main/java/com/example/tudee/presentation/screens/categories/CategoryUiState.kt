package com.example.tudee.presentation.screens.categories

import com.example.tudee.domain.model.Category
import com.example.tudee.domain.model.Task
import com.example.tudee.presentation.screens.home.TaskStatus

data class CategoryUiState(
    val categoryTitle: String = "",
    val selectedCategoryImage: String? = null,
    val categories: List<Category> = emptyList(),
    val showBottomSheet: Boolean = false,
    var selectedTab: TaskStatus = TaskStatus.IN_PROGRESS,
    var clickedCategory: Category? = null,
    val tasksPerCategory: List<Task> = emptyList(),
    val todoTasks : List<Task>? = emptyList(),
    val inProgressTasks : List<Task>? = emptyList(),
    val doneTasks : List<Task>? = emptyList(),
)