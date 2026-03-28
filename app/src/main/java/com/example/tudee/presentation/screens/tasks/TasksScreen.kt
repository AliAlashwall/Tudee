package com.example.tudee.presentation.screens.tasks

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.tudee.R
import com.example.tudee.domain.model.Category
import com.example.tudee.domain.model.Task
import com.example.tudee.presentation.components.CustomDateRangePicker
import com.example.tudee.presentation.components.bottomSheet.BottomSheetButtons
import com.example.tudee.presentation.components.bottomSheet.TudeeBottomSheet
import com.example.tudee.presentation.designSystem.theme.Theme
import com.example.tudee.presentation.designSystem.theme.TudeeTheme
import com.example.tudee.presentation.screens.home.components.TaskStatus
import com.example.tudee.presentation.screens.tasks.components.HorizontalDayPicker
import com.example.tudee.presentation.components.TaskTabs
import com.example.tudee.presentation.unit.fromDMYtoLocalDate

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun TasksScreen(
    modifier: Modifier = Modifier,
    tasksViewModel: TasksViewModel
) {
    val tasksUiState = tasksViewModel.tasksUiState.collectAsStateWithLifecycle().value

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
                allTasksList = tasksUiState.allTasks,
                date = tasksUiState.selectedDate,
                state = tasksUiState.selectedTab.label
            ),
            onSwapTaskCard = { tasksViewModel.onSwapTaskCard(it) },
            allCategories = tasksUiState.allCategories
        )
    }
    if (tasksUiState.showDeleteBottomSheet) {
        TudeeBottomSheet(
            onDismissRequest = { tasksViewModel.onDismissBottomSheet() },
            expanded = false
        ) {
            BottomSheetDeleteContent(
                onDeleteClicked = {
                    if (tasksUiState.swapedTask != null) {
                        tasksViewModel.deleteTask(tasksUiState.swapedTask!!)
                    }
                },
                onCancelClicked = { tasksViewModel.onDismissBottomSheet() }
            )
        }
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
    statusTasksList: List<Task>,
    onSwapTaskCard: (Task) -> Unit,
    allCategories: Map<Int, Category>
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
            onSwapTaskCard = { onSwapTaskCard(it) },
            allCategories = allCategories
        )
    }

    if (showDialog) {
        CustomDateRangePicker(
            onDismissRequest = { onDismissDatePicker() },
            onConfirm = { onDateSelected(it) }
        )
    }
}

@Composable
fun BottomSheetDeleteContent(
    modifier: Modifier = Modifier,
    onDeleteClicked: () -> Unit = {},
    onCancelClicked: () -> Unit = {},
) {

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier.background(Theme.colors.surface)
    ) {
        Text(
            stringResource(R.string.delete_task),
            style = Theme.textStyle.title.large,
            color = Theme.colors.title,
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp),
            textAlign = TextAlign.Start
        )
        Spacer(Modifier.height(12.dp))

        Text(
            stringResource(R.string.are_you_sure_to_continue),
            style = Theme.textStyle.body.large,
            color = Theme.colors.body,
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp),
            textAlign = TextAlign.Start
        )
        Spacer(Modifier.height(12.dp))

        Image(
            painterResource(R.drawable.im_robot_normal),
            contentDescription = stringResource(R.string.tudee_robot),
            Modifier.size(107.dp, 100.dp)
        )

        Spacer(Modifier.height(24.dp))

        BottomSheetButtons(
            onPrimaryButtonClicked = { onDeleteClicked() },
            onCancelBottomSheetClicked = { onCancelClicked() },
            primaryButtonText = stringResource(R.string.delete),
            primaryButtonColor = Theme.colors.errorVariant,
            onPrimaryButtonColor = Theme.colors.error,
            onSecondaryButtonColor = Theme.colors.primary
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
            onSwapTaskCard = {},
            allCategories = emptyMap()
        )
    }
}

@RequiresApi(Build.VERSION_CODES.O)
@Preview(showBackground = true)
@Composable
private fun BottomSheetDeleteContentPreview() {
    TudeeTheme { BottomSheetDeleteContent() }
}