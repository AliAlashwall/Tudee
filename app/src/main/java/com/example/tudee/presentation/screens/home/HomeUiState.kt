package com.example.tudee.presentation.screens.home

import com.example.tudee.R
import com.example.tudee.domain.model.Category
import com.example.tudee.domain.model.Task

data class HomeUiState(
    val newTaskTitle: String = "",
    val newDescription: String = "",
    val selectedDate: String = "",      //21 Mar 2026
    val currentDate: String = "",       //21 Mar 2026
    val currentPriority: Int? = null,
    val selectedCategoryId: Int? = null,
    val selectedTask: Task? = null,
    val showAddBottomSheet: Boolean = false,
    val showEditTaskBottomSheet: Boolean = false,
    val showDetailsBottomSheet: Boolean = false,
    val notificationTitle: String = "",
    val notificationDescription: String = "",
    val notificationIcon: Int = R.drawable.ic_status_neutral,
    val allTasks: List<Task> = emptyList(),
    val todoTasks: List<Task>? = emptyList(),
    val inProgressTasks: List<Task>? = emptyList(),
    val doneTasks: List<Task>? = emptyList(),
    val allCategories: List<Category> = emptyList()
)


