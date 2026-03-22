package com.example.tudee.presentation.screens.tasks

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.tudee.database.dao.TasksDao
import com.example.tudee.database.entity.TasksEntity
import com.example.tudee.presentation.screens.home.TaskStatus
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update

@RequiresApi(Build.VERSION_CODES.O)
class TasksViewModel(tasksDao: TasksDao) : ViewModel() {

    val tasksUiState = MutableStateFlow(TasksUiState())

    val allTasks: StateFlow<List<TasksEntity>> = tasksDao.getAllTasks()
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

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

    fun onTabClicked(selectedTab: TaskStatus) {
        tasksUiState.update {
            it.copy(selectedTab = selectedTab)
        }
    }

    fun getTasksByDateAndState(
        allTasksList: List<TasksEntity>,
        date: String,
        state: String
    ): List<TasksEntity> {
        return allTasksList.filter {
            it.date == date && it.status == state
        }
    }
}

class TaskViewModelFactoryForTask(private val dao: TasksDao) : ViewModelProvider.Factory {
// A Factory class — needed because NoteViewModel has a custom constructor parameter (dao)
// By default, ViewModelProvider can only create ViewModels with empty constructors

    @RequiresApi(Build.VERSION_CODES.O)
    override fun <T : ViewModel> create(modelClass: Class<T>): T =
    // Called by the framework when a ViewModel is first requested
        // modelClass — the class of ViewModel being requested

        TasksViewModel(dao) as T
    // Creates a new NoteViewModel, passing the dao
    // as T — unchecked cast to the expected ViewModel type
}