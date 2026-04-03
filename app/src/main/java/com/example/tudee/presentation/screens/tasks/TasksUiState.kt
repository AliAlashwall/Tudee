package com.example.tudee.presentation.screens.tasks

import android.os.Build
import androidx.annotation.RequiresApi
import com.example.tudee.domain.model.Category
import com.example.tudee.domain.model.Task
import com.example.tudee.presentation.screens.home.components.TaskStatus
import com.example.tudee.presentation.unit.toDMYFormat
import java.time.LocalDate

@RequiresApi(Build.VERSION_CODES.O)
data class TasksUiState(
    val selectedDate: String = LocalDate.now().toDMYFormat(),
    val showDatePicker: Boolean = false,
    val showDeleteBottomSheet: Boolean = false,
    val selectedTab: TaskStatus = TaskStatus.IN_PROGRESS,
    val swapedTask: Task? = null,
    val allTasks : List<Task> = emptyList(),
    val allCategories: Map<Int, Category> = emptyMap(),
)
