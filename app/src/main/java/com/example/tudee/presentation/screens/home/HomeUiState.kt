package com.example.tudee.presentation.screens.home

import com.example.tudee.R
import com.example.tudee.database.entity.TasksEntity

data class HomeUiState(
    var newTaskTitle: String = "",
    var newDescription: String = "",
    var showAddBottomSheet: Boolean = false,
    var showEditTaskBottomSheet: Boolean = false,
    var showDetailsBottomSheet: Boolean = false,
    var selectedDate: String = "22-6-2025",
    var currentDate: String = "22-6-2025",
    var currentPriority: Int? = null,
    var selectedCategoryIcon: Int? = null,
    var selectedTask: TasksEntity? = null,
    val todoTasksCount: Int = 0,
    val inProgressTasksCount: Int = 0,
    val doneTasksCount: Int = 0,
    val notificationTitle: String = "title empty",
    val notificationDescription: String = "desc empty",
    val notificationIcon: Int = R.drawable.ic_status_neutral,
)


