package com.example.tudee.presentation.screens.tasks

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.tudee.domain.model.Category
import com.example.tudee.domain.model.Task
import com.example.tudee.domain.repository.CategoryRepository
import com.example.tudee.domain.repository.TaskRepository
import com.example.tudee.presentation.screens.home.TaskStatus
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@RequiresApi(Build.VERSION_CODES.O)
@HiltViewModel
class TasksViewModel @Inject constructor(
    private val taskRepository: TaskRepository,
    private val categoryRepository: CategoryRepository
) : ViewModel() {

    private val _tasksUiState = MutableStateFlow(TasksUiState())

    val allTasks: StateFlow<List<Task>> =
        taskRepository.getAllTasks()
            .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    val allCategories: StateFlow<List<Category>> =
        categoryRepository.getAllCategories()
            .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    val tasksUiState: StateFlow<TasksUiState> = combine(
        _tasksUiState,
        allTasks,
        allCategories
    ) { state, tasks, categories ->
        state.copy(
            allTasks = tasks,
            allCategories = categories.associateBy { it.id }
        )
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), TasksUiState())

    fun onDateSelected(date: String) {
        _tasksUiState.update {
            it.copy(selectedDate = date)
        }
    }

    fun deleteTask(task: Task) {
        viewModelScope.launch {
            taskRepository.deleteTask(task)
        }
        _tasksUiState.update {
            it.copy(
                showDeleteBottomSheet = false,
                swapedTask = null
            )
        }
    }

    fun onSwapTaskCard(task: Task) {
        _tasksUiState.update {
            it.copy(
                showDeleteBottomSheet = true,
                swapedTask = task
            )
        }

    }

    fun onDismissDatePicker() {
        _tasksUiState.update {
            it.copy(showDatePicker = false)
        }
    }

    fun onDismissBottomSheet() {
        _tasksUiState.update {
            it.copy(showDeleteBottomSheet = false)
        }
    }

    fun onShowDatePicker() {
        _tasksUiState.update {
            it.copy(showDatePicker = true)
        }
    }

    fun onTabClicked(selectedTab: TaskStatus) {
        _tasksUiState.update {
            it.copy(selectedTab = selectedTab)
        }
    }

    fun getTasksByDateAndState(
        allTasksList: List<Task>,
        date: String,
        state: String
    ): List<Task> {
        return allTasksList.filter {
            it.date == date && it.status == state
        }
    }
}