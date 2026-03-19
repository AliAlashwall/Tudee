package com.example.tudee.presentation.screens.home

import android.annotation.SuppressLint
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
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
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun HomeScreen(
    modifier: Modifier = Modifier, navController: NavController,
    homeViewModel: HomeViewModel
) {
    LaunchedEffect(Unit) {
        homeViewModel.getCurrentDate()
    }

    val allTasks = homeViewModel.allTasks.collectAsStateWithLifecycle().value
    val homeUiState = homeViewModel.homeUiState.collectAsStateWithLifecycle().value
    Scaffold(
        containerColor = Theme.colors.surface,
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
    {
        Column(
            modifier = modifier
                .fillMaxSize(),
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            HomeScreenContent(
                currentDate = homeUiState.currentDate,
                allTasks = allTasks,
                onTaskClicked = { homeViewModel.onTaskClicked(it) },
            )

            if (homeUiState.showAddBottomSheet) {
                TudeeBottomSheet(
                    modifier = Modifier.heightIn(max = 700.dp),
                    onDismissRequest = { homeViewModel.onDismissBottomSheet() })
                {
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
                        enablePrimaryTaskButton = homeViewModel.enableAddTaskButton(),
                        onCancelBottomSheetClicked = { homeViewModel.onDismissBottomSheet() },
                        onPrimaryButtonClicked = { homeViewModel.onAddClicked() }
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
                        onEditButtonClicked = { homeViewModel.onEditTaskClicked() }
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
                        enablePrimaryTaskButton = true,
                        onCancelBottomSheetClicked = { homeViewModel.onDismissBottomSheet() },
                        onPrimaryButtonClicked = { homeViewModel.onSaveTaskEditClicked() },
                        primaryButtonText = stringResource(R.string.save)
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
    allTasks: List<TasksEntity>,
    onTaskClicked: (TasksEntity) -> Unit,
) {
    Box {
        Box(
            modifier = modifier
                .fillMaxWidth()
                .height(167.dp)
                .background(Theme.colors.primary)
        )

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp)
        ) {
            HomeTopBar(modifier = Modifier.padding(vertical = 12.dp))

            OverviewCard(
                currentDate = "today, $currentDate",
                statusIconId = R.drawable.ic_status_neutral,
                tudeeStatusImgId = R.drawable.im_robot_neutral,
                notificationTitle = stringResource(R.string.stay_working),
                notificationDescription = stringResource(R.string.stay_working_description)
            )

            Spacer(Modifier.height(48.dp))

            if (allTasks.isNotEmpty()) {
                LazyColumn(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                    items(allTasks) { task ->
                        TaskCard(
                            taskIcon = task.categoryIcon,
                            priorityLevel = task.priority,
                            title = task.title,
                            description = task.description,
                            onClick = { onTaskClicked(task) }
                        )
                    }
                }
            } else {
                EmptyTasks(modifier = Modifier.fillMaxWidth())
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
            currentDate = "",
            allTasks = listOf(
                TasksEntity(
                    id = 1,
                    title = "po",
                    description = "sdfsafgsagfdg",
                    categoryIcon = (R.drawable.ic_quran),
                    priority = 0,
                    status = TaskStatus.TO_DO.label,
                    date = "22-6-2025"
                )
            ),
            onTaskClicked = {},
        )
    }
}