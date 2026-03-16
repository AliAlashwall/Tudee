package com.example.tudee.presentation.screens.tasks

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.update

class TasksViewModel : ViewModel() {

    val tasksUiState = MutableStateFlow(TasksUiState())

    fun onDateSelected(date: String) {
        tasksUiState.update {
            it.copy(selectedDate = date)
        }
    }

    fun onDismissDatePicker() {
        tasksUiState.update {
            it.copy(showDatePicker = false)
        }
    }

    fun onShowDatePicker() {
        tasksUiState.update {
            it.copy(showDatePicker = true)
        }
    }
}