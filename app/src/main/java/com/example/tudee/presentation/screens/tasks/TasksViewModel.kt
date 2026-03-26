package com.example.tudee.presentation.screens.tasks

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.tudee.database.dao.TasksDao
import com.example.tudee.database.mapper.toDomain
import com.example.tudee.database.mapper.toTaskEntity
import com.example.tudee.domain.model.Task
import com.example.tudee.presentation.screens.home.TaskStatus
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

@RequiresApi(Build.VERSION_CODES.O)
class TasksViewModel(val tasksDao: TasksDao) : ViewModel() {

    private val _tasksUiState = MutableStateFlow(TasksUiState())

    val allTasks: StateFlow<List<Task>> =
        tasksDao.getAllTasks().map { tasksEntity -> tasksEntity.map { it.toDomain() } }
            .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    val tasksUiState: StateFlow<TasksUiState> = combine(
        _tasksUiState,
        allTasks
    ) { state, tasks ->
        state.copy(
            allTasks = tasks
        )
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), TasksUiState())

    fun onDateSelected(date: String) {
        _tasksUiState.update {
            it.copy(selectedDate = date)
        }
    }

    fun deleteTask(task: Task) {
        viewModelScope.launch {
            tasksDao.deleteTask(task.toTaskEntity())
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

class TaskViewModelFactoryForTask(private val dao: TasksDao) : ViewModelProvider.Factory {

    @RequiresApi(Build.VERSION_CODES.O)
    override fun <T : ViewModel> create(modelClass: Class<T>): T =
        TasksViewModel(dao) as T

}