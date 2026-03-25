package com.example.tudee.presentation.screens.home

import android.annotation.SuppressLint
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import com.example.tudee.R
import com.example.tudee.database.entity.TasksEntity
import com.example.tudee.presentation.components.EmptyTasks
import com.example.tudee.presentation.components.HomeTopBar
import com.example.tudee.presentation.components.OverviewCard
import com.example.tudee.presentation.components.TaskCard
import com.example.tudee.presentation.components.bottomSheet.TudeeBottomSheet
import com.example.tudee.presentation.designSystem.theme.Theme
import com.example.tudee.presentation.designSystem.theme.TudeeTheme

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun HomeScreen(
    modifier: Modifier = Modifier, navController: NavController,
    homeViewModel: HomeViewModel
) {
    LaunchedEffect(Unit) {
        homeViewModel.getCurrentDate()
    }

    val homeUiState = homeViewModel.homeUiState.collectAsStateWithLifecycle().value

    val allDayTasks =
        remember(homeUiState.allTasks) { homeUiState.allTasks.filter { it.date == homeViewModel.homeUiState.value.currentDate } }
    Scaffold(
        containerColor = Theme.colors.surface,
        topBar = {
            HomeTopBar()
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = { homeViewModel.onFABClicked() },
                containerColor = Theme.colors.primary,
                shape = RoundedCornerShape(50.dp),
                modifier = Modifier.size(64.dp)
            ) {
                Icon(
                    painterResource(R.drawable.ic_add_task),
                    contentDescription = "Add Task",
                    tint = Theme.colors.onPrimary,
                    modifier = Modifier.size(24.dp)
                )
            }
        })
    { paddingValues ->
        Column(
            modifier = modifier
                .fillMaxSize()
                .padding(paddingValues),
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            HomeScreenContent(
                currentDate = homeUiState.currentDate,
                allTasks = allDayTasks,
                statusIconId = homeUiState.notificationIcon,
                tudeeStatusImgId = R.drawable.im_robot_neutral,
                notificationTitle = homeUiState.notificationTitle,
                notificationDescription = homeUiState.notificationDescription,
                onTaskClicked = { homeViewModel.onTaskClicked(it) },
                todoTasks = homeUiState.todoTasks,
                inProgressTasks = homeUiState.inProgressTasks,
                doneTasks = homeUiState.doneTasks,
            )

            if (homeUiState.showAddBottomSheet) {
                TudeeBottomSheet(
                    modifier = Modifier.heightIn(max = 700.dp),
                    onDismissRequest = { homeViewModel.onDismissBottomSheet() })
                {
                    val enableAddTaskButton =
                        remember(homeViewModel.enableAddTaskButton()) { homeViewModel.enableAddTaskButton() }
//                    Add Task Bottom Sheet
                    TasksBottomSheetContent(
                        modifier = Modifier.background(Theme.colors.surface),
                        newTaskTitle = homeUiState.newTaskTitle,
                        newDescription = homeUiState.newDescription,
                        currentPriority = homeUiState.currentPriority,
                        selectedDate = homeUiState.selectedDate,
                        selectedCategoryIcon = homeUiState.selectedCategoryIcon,
                        onNewTaskTitleChange = { homeViewModel.onNewTaskTitleChange(it) },
                        onNewDescriptionChange = { homeViewModel.onNewTaskDescriptionChange(it) },
                        updateCurrentPriority = { homeViewModel.updateCurrentPriority(it) },
                        updateSelectedDate = { homeViewModel.updateSelectedDate(it) },
                        onClickCategory = { homeViewModel.updateSelectedCategory(it) },
                        onCancelBottomSheetClicked = { homeViewModel.onDismissBottomSheet() },
                        onPrimaryButtonClicked = { homeViewModel.onAddClicked() },
                        primaryButtonColor = if (enableAddTaskButton) Theme.colors.primary else Theme.colors.disable,
                        onPrimaryButtonColor = if (enableAddTaskButton) Theme.colors.onPrimary else Theme.colors.stroke,
                        secondaryButtonColor = Theme.colors.stroke,
                        onSecondaryButtonColor = Theme.colors.primary,
                        categories = homeUiState.allCategories
                    )
                }
            }

            if (homeUiState.showDetailsBottomSheet && homeUiState.selectedTask != null) {
                TudeeBottomSheet(
                    modifier = Modifier.heightIn(max = 700.dp),
                    expanded = false,
                    onDismissRequest = { homeViewModel.onDismissBottomSheet() }) {
                    TaskDetailsBottomSheetContent(
                        task = homeUiState.selectedTask!!,
                        onMoveButtonClicked = { homeViewModel.onMoveTaskStatusClicked(homeUiState.selectedTask!!) },
                        onEditButtonClicked = { homeViewModel.onEditTaskClicked() },
                        modifier = Modifier.heightIn(max = 373.dp)
                    )
                }
            }

            if (homeUiState.showEditTaskBottomSheet && homeUiState.selectedTask != null) {
                TudeeBottomSheet(
                    modifier = Modifier.heightIn(max = 700.dp),
                    onDismissRequest = { homeViewModel.onDismissBottomSheet() })
                {
                    // Edit task bottom sheet
                    TasksBottomSheetContent(
                        modifier = Modifier.background(Theme.colors.surface),
                        sheetTitle = stringResource(R.string.edit_task),
                        newTaskTitle = homeUiState.newTaskTitle,
                        newDescription = homeUiState.newDescription,
                        currentPriority = homeUiState.currentPriority,
                        selectedDate = homeUiState.selectedDate,
                        selectedCategoryIcon = homeUiState.selectedCategoryIcon,
                        onNewTaskTitleChange = { homeViewModel.onNewTaskTitleChange(it) },
                        onNewDescriptionChange = { homeViewModel.onNewTaskDescriptionChange(it) },
                        updateCurrentPriority = { homeViewModel.updateCurrentPriority(it) },
                        updateSelectedDate = { homeViewModel.updateSelectedDate(it) },
                        onClickCategory = { homeViewModel.updateSelectedCategory(it) },
                        onCancelBottomSheetClicked = { homeViewModel.onDismissBottomSheet() },
                        onPrimaryButtonClicked = { homeViewModel.onSaveTaskEditClicked() },
                        primaryButtonText = stringResource(R.string.save),
                        primaryButtonColor = Theme.colors.primary,
                        onPrimaryButtonColor = Theme.colors.onPrimary,
                        secondaryButtonColor = Theme.colors.stroke,
                        onSecondaryButtonColor = Theme.colors.primary,
                        categories = homeUiState.allCategories
                    )
                }
            }
        }
    }
}

@Composable
fun HomeScreenContent(
    modifier: Modifier = Modifier,
    currentDate: String,
    statusIconId: Int,
    tudeeStatusImgId: Int,
    notificationTitle: String,
    notificationDescription: String,
    allTasks: List<TasksEntity>,
    onTaskClicked: (TasksEntity) -> Unit,
    todoTasks: List<TasksEntity>?,
    inProgressTasks: List<TasksEntity>?,
    doneTasks: List<TasksEntity>?
) {
    LazyColumn(
        modifier = modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        item {
            OverviewCard(
                currentDate = "today, $currentDate",
                statusIconId = statusIconId,
                tudeeStatusImgId = tudeeStatusImgId,
                notificationTitle = notificationTitle,
                notificationDescription = notificationDescription,
                todoTasksCount = todoTasks?.size ?: 0,
                inProgressTasksCount = inProgressTasks?.size ?: 0,
                doneTasksCount = doneTasks?.size ?: 0,
            )
        }

        item { Spacer(Modifier.height(16.dp)) }

        if (allTasks.isNotEmpty()) {

            if (inProgressTasks != null) {
                item {
                    Text(
                        text = stringResource(R.string.in_progress_status),
                        style = Theme.textStyle.title.large,
                        color = Theme.colors.title,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 16.dp)
                    )
                    Spacer(Modifier.height(2.dp))
                }
                item {
                    LazyRow(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                        items(
                            allTasks.filter { it.status == TaskStatus.IN_PROGRESS.label },
                            key = { it.id }) { task ->
                            TaskCard(
                                taskIcon = task.categoryIcon,
                                priorityLevel = task.priority,
                                title = task.title,
                                description = task.description,
                                onClick = { onTaskClicked(task) },
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(horizontal = 16.dp)
                            )
                        }
                    }

                    Spacer(Modifier.height(16.dp))
                }
            }
            if (todoTasks != null) {
                item {
                    Text(
                        text = stringResource(R.string.to_do),
                        style = Theme.textStyle.title.large,
                        color = Theme.colors.title,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 16.dp)
                    )
                }
                item {
                    LazyRow(
                        horizontalArrangement = Arrangement.spacedBy(8.dp),
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 16.dp)
                    ) {
                        items(allTasks.filter { it.status == TaskStatus.TO_DO.label }) { task ->
                            TaskCard(
                                taskIcon = task.categoryIcon,
                                priorityLevel = task.priority,
                                title = task.title,
                                description = task.description,
                                onClick = { onTaskClicked(task) }
                            )
                            Spacer(Modifier.height(16.dp))
                        }
                    }
                }
            }

            if (doneTasks != null) {
                item {
                    Text(
                        text = stringResource(R.string.done),
                        style = Theme.textStyle.title.large,
                        color = Theme.colors.title,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 16.dp)
                    )
                }
                item {
                    LazyRow(
                        horizontalArrangement = Arrangement.spacedBy(8.dp),
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 16.dp)
                    ) {
                        items(allTasks.filter { it.status == TaskStatus.DONE.label }) { task ->
                            TaskCard(
                                taskIcon = task.categoryIcon,
                                priorityLevel = task.priority,
                                title = task.title,
                                description = task.description,
                                onClick = { onTaskClicked(task) }
                            )
                            Spacer(Modifier.height(16.dp))
                        }
                    }
                }
            }
        } else {
            item {
                EmptyTasks(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp)
                )
            }
        }
    }
}

@RequiresApi(Build.VERSION_CODES.O)
@SuppressLint("ViewModelConstructorInComposable")
@Preview(showBackground = true, backgroundColor = 0xFF0D0C14)
@Composable
private fun HomeScreenPreview() {
    TudeeTheme {
        HomeScreenContent(
            currentDate = "22-6-2025",
            allTasks = listOf(
                TasksEntity(
                    id = 1,
                    title = "First",
                    description = "First description",
                    categoryIcon = (R.drawable.ic_quran.toString()),
                    priority = 0,
                    status = TaskStatus.TO_DO.label,
                    date = "22-6-2025"
                )
            ),
            statusIconId = R.drawable.ic_status_neutral,
            tudeeStatusImgId = R.drawable.im_robot_neutral,
            notificationTitle = "Stay working",
            notificationDescription = "You have 3 tasks to do today, let's get it done!",
            onTaskClicked = {},
            todoTasks = emptyList(),
            inProgressTasks = emptyList(),
            doneTasks = emptyList()
        )
    }
}