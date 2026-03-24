package com.example.tudee.presentation.screens.home

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.tudee.R
import com.example.tudee.database.dao.TasksDao
import com.example.tudee.database.entity.TasksEntity
import com.example.tudee.presentation.unit.toDMYFormat
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.time.LocalDate

@RequiresApi(Build.VERSION_CODES.O)
class HomeViewModel(val tasksDao: TasksDao) : ViewModel() {

    val allTasks: StateFlow<List<TasksEntity>> = tasksDao.getAllTasks()
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())


    private val _homeUiState = MutableStateFlow(HomeUiState())
    val homeUiState: StateFlow<HomeUiState> = _homeUiState.asStateFlow()


    fun onNewTaskTitleChange(newTitle: String) {
        _homeUiState.update {
            it.copy(newTaskTitle = newTitle)
        }
    }

    fun updateOverviewSection(allTasks: List<TasksEntity>) {
        updateOverviewCounter(allTasks)
        updateOverviewNotification()
    }

    private fun updateOverviewCounter(allTasks: List<TasksEntity>) {
        val counts = allTasks.groupBy { it.status }
        val todoTasks = counts[TaskStatus.TO_DO.label]
        val inProgressTasks = counts[TaskStatus.IN_PROGRESS.label]
        val doneTasks = counts[TaskStatus.DONE.label]

        _homeUiState.update {
            it.copy(
                todoTasksCount = todoTasks,
                inProgressTasksCount = inProgressTasks,
                doneTasksCount = doneTasks
            )
        }
    }

    private fun updateOverviewNotification() {
        val state = _homeUiState.value
        val todo = state.todoTasksCount?.size ?: 0
        val inProgress = state.inProgressTasksCount?.size ?: 0
        val done = state.doneTasksCount?.size ?: 0

        val (title, description, icon) = when {
            todo == 0 && inProgress == 0 && done == 0 -> Triple(
                "Nothing on your list...",
                "Fill your day with something awesome.",
                R.drawable.ic_status_sad
            )

            todo == 0 && inProgress == 0 && done != 0 -> Triple(
                "Tadaa!",
                "You're doing amazing!!!\nTudee is proud of you.",
                R.drawable.ic_status_happy
            )

            todo != 0 && inProgress == 0 && done == 0 -> Triple(
                "Zero progress?!",
                "You just scrolling, not working. Tudee is watching. back to work!!!",
                R.drawable.ic_status_angry
            )

            else -> Triple(
                "Stay working",
                "You've completed $done out of ${inProgress + todo + done} tasks. Keep going!",
                R.drawable.ic_status_neutral
            )
        }

        _homeUiState.update {
            it.copy(
                notificationTitle = title,
                notificationDescription = description,
                notificationIcon = icon
            )
        }
    }


    fun onNewTaskDescriptionChange(newDescription: String) {
        _homeUiState.update {
            it.copy(newDescription = newDescription)
        }
    }

    fun updateCurrentPriority(newPriority: Int) {
        _homeUiState.update {
            it.copy(currentPriority = newPriority)
        }
    }


    fun getCurrentDate() {
        val currDate = LocalDate.now().toDMYFormat()
        _homeUiState.update {
            it.copy(currentDate = currDate, selectedDate = currDate)
        }
    }

    fun onFABClicked() {
        showAddBottomSheet(true)
    }

    // Update the selectedDate field (used by the BottomSheet) instead of currentDate.
    fun updateSelectedDate(selectedDate: String) {
        _homeUiState.update {
            it.copy(selectedDate = selectedDate)
        }
    }

    fun enableAddTaskButton(): Boolean {
        return _homeUiState.value.newTaskTitle.isNotBlank() &&
                _homeUiState.value.selectedCategoryIcon.let { it != null } &&
                _homeUiState.value.currentPriority.let { it != null }
    }

    fun updateSelectedCategory(categoryIcon: Int) {
        _homeUiState.update {
            it.copy(selectedCategoryIcon = categoryIcon)
        }
    }

    fun onTaskClicked(task: TasksEntity) {
        _homeUiState.update {
            it.copy(
                selectedTask = task,
                showDetailsBottomSheet = true
            )
        }
    }

    fun onEditTaskClicked() {
        _homeUiState.update {
            it.copy(
                showDetailsBottomSheet = false,
                showEditTaskBottomSheet = true,
                newTaskTitle = _homeUiState.value.selectedTask!!.title,
                newDescription = _homeUiState.value.selectedTask!!.description,
                currentPriority = _homeUiState.value.selectedTask!!.priority,
                selectedDate = _homeUiState.value.selectedTask!!.date,
                selectedCategoryIcon = _homeUiState.value.selectedTask!!.categoryIcon,
            )
        }
    }

    fun onMoveTaskStatusClicked(task: TasksEntity) {
        if (task.status == TaskStatus.TO_DO.label) {
            updateTask(task.copy(status = TaskStatus.IN_PROGRESS.label))

        }
        if (task.status == TaskStatus.IN_PROGRESS.label) {
            updateTask(task.copy(status = TaskStatus.DONE.label))
        }
        _homeUiState.update {
            it.copy(showDetailsBottomSheet = false)
        }
    }

    fun onAddClicked() {
        // All values are exists, because the button is enabled only if they are.
        val task = TasksEntity(
            title = _homeUiState.value.newTaskTitle,
            description = _homeUiState.value.newDescription,
            date = _homeUiState.value.selectedDate,
            categoryIcon = _homeUiState.value.selectedCategoryIcon!!,
            priority = _homeUiState.value.currentPriority!!,
            status = TaskStatus.TO_DO.label,
        )

        viewModelScope.launch {
            tasksDao.insertTask(task)
        }
        onDismissBottomSheet()
    }

    fun onSaveTaskEditClicked() {
        val updatedTask = _homeUiState.value.selectedTask!!
            .copy(
                title = _homeUiState.value.newTaskTitle,
                description = _homeUiState.value.newDescription,
                date = _homeUiState.value.selectedDate,
                categoryIcon = _homeUiState.value.selectedCategoryIcon!!,
                priority = _homeUiState.value.currentPriority!!,
                status = _homeUiState.value.selectedTask!!.status,
            )
        viewModelScope.launch {
            tasksDao.updateTask(updatedTask)
        }
        onDismissBottomSheet()
    }

    fun updateTask(
        task: TasksEntity
    ) {
        viewModelScope.launch {
            tasksDao.updateTask(task)

        }
    }

    fun onDismissBottomSheet() {
        resetBottomSheetState()
    }

    private fun resetBottomSheetState() {
        _homeUiState.update {
            it.copy(
                newTaskTitle = "",
                newDescription = "",
                showAddBottomSheet = false,
                showEditTaskBottomSheet = false,
                showDetailsBottomSheet = false,
                selectedDate = it.currentDate,
                currentPriority = null,
                selectedCategoryIcon = null
            )
        }
    }

    // Helper to control the bottom sheet visibility
    fun showAddBottomSheet(visibility: Boolean) {
        _homeUiState.update {
            it.copy(showAddBottomSheet = visibility)
        }
    }
}

class TaskViewModelFactoryForHome(private val dao: TasksDao) : ViewModelProvider.Factory {
// A Factory class — needed because NoteViewModel has a custom constructor parameter (dao)
// By default, ViewModelProvider can only create ViewModels with empty constructors

    @RequiresApi(Build.VERSION_CODES.O)
    override fun <T : ViewModel> create(modelClass: Class<T>): T =
    // Called by the framework when a ViewModel is first requested
        // modelClass — the class of ViewModel being requested

        HomeViewModel(dao) as T
    // Creates a new NoteViewModel, passing the dao
    // as T — unchecked cast to the expected ViewModel type
}
