package com.example.tudee.presentation.screens.tasks

import android.os.Build
import androidx.annotation.RequiresApi
import com.example.tudee.presentation.screens.home.TaskStatus
import com.example.tudee.presentation.unit.toDMYFormat
import java.time.LocalDate

@RequiresApi(Build.VERSION_CODES.O)
data class TasksUiState(
    val selectedDate: String = LocalDate.now().toDMYFormat(),
    var showDatePicker: Boolean = false,
    var selectedTab: TaskStatus = TaskStatus.IN_PROGRESS,
)
