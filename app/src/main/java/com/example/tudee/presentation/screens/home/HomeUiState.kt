package com.example.tudee.presentation.screens.home

import com.example.tudee.R
import com.example.tudee.database.entity.TasksEntity

data class HomeUiState(
    var newTaskTitle: String = "",
    var newDescription: String = "",
    var selectedDate: String = "21 Mar 2026",
    var currentDate: String = "21 Mar 2026",
    var currentPriority: Int? = null,
    var selectedCategoryIcon: Int? = null,
    var selectedTask: TasksEntity? = null,
    var showAddBottomSheet: Boolean = false,
    var showEditTaskBottomSheet: Boolean = false,
    var showDetailsBottomSheet: Boolean = false,
    val todoTasksCount:  List<TasksEntity>? = emptyList(),
    val inProgressTasksCount:  List<TasksEntity>? = emptyList(),
    val doneTasksCount:  List<TasksEntity>? = emptyList(),
    val notificationTitle: String = "title empty",
    val notificationDescription: String = "desc empty",
    val notificationIcon: Int = R.drawable.ic_status_neutral,
)


