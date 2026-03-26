package com.example.tudee.presentation.screens.home

import com.example.tudee.R
import com.example.tudee.domain.model.Category
import com.example.tudee.domain.model.Task

data class HomeUiState(
    var newTaskTitle: String = "",
    var newDescription: String = "",
    var selectedDate: String = "21 Mar 2026",
    var currentDate: String = "21 Mar 2026",
    var currentPriority: Int? = null,
    var selectedCategoryIcon: String? = null,
    var selectedTask: Task? = null,
    var showAddBottomSheet: Boolean = false,
    var showEditTaskBottomSheet: Boolean = false,
    var showDetailsBottomSheet: Boolean = false,
    val notificationTitle: String = "title empty",
    val notificationDescription: String = "desc empty",
    val notificationIcon: Int = R.drawable.ic_status_neutral,
    val allTasks: List<Task> = emptyList(),
    val todoTasks: List<Task>? = emptyList(),
    val inProgressTasks: List<Task>? = emptyList(),
    val doneTasks: List<Task>? = emptyList(),
    val allCategories: List<Category> = emptyList()
)


