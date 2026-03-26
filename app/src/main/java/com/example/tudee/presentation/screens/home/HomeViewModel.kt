package com.example.tudee.presentation.screens.home

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.tudee.R
import com.example.tudee.domain.model.Category
import com.example.tudee.domain.model.Task
import com.example.tudee.domain.repository.CategoryRepository
import com.example.tudee.domain.repository.TaskRepository
import com.example.tudee.presentation.unit.toDMYFormat
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.time.LocalDate
import javax.inject.Inject

@RequiresApi(Build.VERSION_CODES.O)
@HiltViewModel
class HomeViewModel @Inject constructor(
    private val taskRepository: TaskRepository,
    private val categoryRepository: CategoryRepository
) : ViewModel() {

    val allTasks: StateFlow<List<Task>> =
        taskRepository.getAllTasks()
            .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    val allCategories: StateFlow<List<Category>> = categoryRepository.getAllCategories()
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    private val _homeUiState = MutableStateFlow(HomeUiState())
    val homeUiState: StateFlow<HomeUiState> = combine(
        _homeUiState,
        allTasks,
        allCategories
    ) { state, tasks, categories ->

        val stateWithOverview = updateOverviewSection(state = state, allTasks = tasks)

        // 2. Return the final copy including tasks and categories
        stateWithOverview.copy(
            allTasks = tasks,
            allCategories = categories
        )
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000),
        initialValue = HomeUiState()
    )


    fun onNewTaskTitleChange(newTitle: String) {
        _homeUiState.update {
            it.copy(newTaskTitle = newTitle)
        }
    }

    private fun updateOverviewSection(
        state: HomeUiState,
        allTasks: List<Task>
    ): HomeUiState {
        val stateWithCounters = updateOverviewCounter(state, allTasks)
        return updateOverviewNotification(stateWithCounters)
    }

    private fun updateOverviewCounter(
        state: HomeUiState,
        allTasks: List<Task>
    ): HomeUiState {
        val groupedTasksWithStatus = allTasks.filter { it.date == state.currentDate }.groupBy { it.status }
        val todoTasks = groupedTasksWithStatus[TaskStatus.TO_DO.label]
        val inProgressTasks = groupedTasksWithStatus[TaskStatus.IN_PROGRESS.label]
        val doneTasks = groupedTasksWithStatus[TaskStatus.DONE.label]

        return state.copy(
            todoTasks = todoTasks,
            inProgressTasks = inProgressTasks,
            doneTasks = doneTasks
        )
    }

    private fun updateOverviewNotification(state: HomeUiState): HomeUiState {
        val todo = state.todoTasks?.size ?: 0
        val inProgress = state.inProgressTasks?.size ?: 0
        val done = state.doneTasks?.size ?: 0

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

        return state.copy(
            notificationTitle = title,
            notificationDescription = description,
            notificationIcon = icon
        )
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

    fun updateSelectedCategory(categoryIcon: String) {
        _homeUiState.update {
            it.copy(selectedCategoryIcon = categoryIcon)
        }
    }

    fun onTaskClicked(task: Task) {
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

    fun onMoveTaskStatusClicked(task: Task) {
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
        val task = Task(
            title = _homeUiState.value.newTaskTitle,
            description = _homeUiState.value.newDescription,
            date = _homeUiState.value.selectedDate,
            categoryIcon = _homeUiState.value.selectedCategoryIcon!!,
            priority = _homeUiState.value.currentPriority!!,
            status = TaskStatus.TO_DO.label,
        )

        viewModelScope.launch {
            taskRepository.insertTask(task)
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
            taskRepository.updateTask(updatedTask)
        }
        onDismissBottomSheet()
    }

    fun updateTask(
        task: Task
    ) {
        viewModelScope.launch {
            taskRepository.updateTask(task)

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
