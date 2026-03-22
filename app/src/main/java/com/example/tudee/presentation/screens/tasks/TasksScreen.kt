package com.example.tudee.presentation.screens.tasks

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import com.example.tudee.R
import com.example.tudee.database.entity.TasksEntity
import com.example.tudee.presentation.components.CustomDateRangePicker
import com.example.tudee.presentation.designSystem.theme.Theme
import com.example.tudee.presentation.designSystem.theme.TudeeTheme
import com.example.tudee.presentation.screens.home.TaskStatus
import com.example.tudee.presentation.unit.fromDMYtoLocalDate

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun TasksScreen(
    modifier: Modifier = Modifier,
    navController: NavController,
    tasksViewModel: TasksViewModel
) {
    val tasksUiState = tasksViewModel.tasksUiState.collectAsStateWithLifecycle().value
    val allTasks = tasksViewModel.allTasks.collectAsStateWithLifecycle().value

    Column(
        modifier = modifier
            .background(color = Theme.colors.surfaceHigh)
            .fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        TasksScreenContent(
            selectedDate = tasksUiState.selectedDate,
            onDateSelected = { tasksViewModel.onDateSelected(it) },
            showDialog = tasksUiState.showDatePicker,
            onDismissDatePicker = { tasksViewModel.onDismissDatePicker() },
            onShowDatePicker = { tasksViewModel.onShowDatePicker() },
            selectedTab = tasksUiState.selectedTab,
            onTabClicked = {
                tasksViewModel.onTabClicked(it)
            },
            statusTasksList = tasksViewModel.getTasksByDateAndState(
                allTasks,
                tasksUiState.selectedDate,
                tasksUiState.selectedTab.label
            ),
        )
    }
}

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun TasksScreenContent(
    modifier: Modifier = Modifier,
    selectedDate: String,
    onDateSelected: (date: String) -> Unit,
    showDialog: Boolean = false,
    onDismissDatePicker: () -> Unit,
    onShowDatePicker: () -> Unit,
    selectedTab: TaskStatus,
    onTabClicked: (TaskStatus) -> Unit,
    statusTasksList: List<TasksEntity>,
) {
    Column(
        modifier
            .fillMaxSize()
            .padding(top = 20.dp)
    ) {
        Text(
            text = stringResource(R.string.tasks),
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp),
            color = Theme.colors.title,
            style = Theme.textStyle.title.large,
        )

        HorizontalDayPicker(
            selectedDate = selectedDate.fromDMYtoLocalDate(),
            onDateSelected = { onDateSelected(it) },
            openDatePicker = { onShowDatePicker() }
        )
        Spacer(Modifier.height(8.dp))

        TaskTabs(
            selectedTab = selectedTab,
            onTabClicked = { onTabClicked(it) },
            statusTasksList = statusTasksList,
        )
    }

    if (showDialog) {
        CustomDateRangePicker(
            onDismissRequest = { onDismissDatePicker() },
            onConfirm = { onDateSelected(it) }
        )
    }
}

@RequiresApi(Build.VERSION_CODES.O)
@Preview(showBackground = true)
@Composable
private fun TasksScreenPreview() {
    TudeeTheme {
        TasksScreenContent(
            selectedDate = "22 Mar 2025",
            onDateSelected = {},
            onDismissDatePicker = {},
            onShowDatePicker = {},
            onTabClicked = { },
            selectedTab = TaskStatus.IN_PROGRESS,
            statusTasksList = emptyList(),
        )
    }
}