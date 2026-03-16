package com.example.tudee.presentation.screens.tasks

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
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
import com.example.tudee.presentation.components.CustomDateRangePicker
import com.example.tudee.presentation.designSystem.theme.Theme
import com.example.tudee.presentation.designSystem.theme.TudeeTheme

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun TasksScreen(
    modifier: Modifier = Modifier,
    navController: NavController,
    tasksViewModel: TasksViewModel
) {
    val tasksUiState = tasksViewModel.tasksUiState.collectAsStateWithLifecycle().value

    Column(
        modifier = modifier.fillMaxSize(), verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        TasksScreenContent(
            onDateSelected = { tasksViewModel.onDateSelected(it) },
            showDialog = tasksUiState.showDatePicker,
            onDismissDatePicker = { tasksViewModel.onDismissDatePicker() },
            onShowDatePicker = { tasksViewModel.onShowDatePicker() }
        )
    }
}

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun TasksScreenContent(
    modifier: Modifier = Modifier,
    onDateSelected: (date: String) -> Unit,
    showDialog: Boolean = false,
    onDismissDatePicker: () -> Unit,
    onShowDatePicker: () -> Unit
) {
    Column(
        modifier
            .fillMaxSize()
            .padding(top = 20.dp, start = 20.dp, end = 20.dp)
    ) {
        Text(
            text = stringResource(R.string.tasks),
            modifier = Modifier.fillMaxWidth(),
            color = Theme.colors.title,
            style = Theme.textStyle.title.large,
        )

        HorizontalDayPicker(
            onDateSelected = { onDateSelected(it) },
            openDatePicker = {onShowDatePicker()}
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
@Preview
@Composable
private fun TasksScreenPreview() {
    TudeeTheme {
        TasksScreenContent(
            onDateSelected = {}, onDismissDatePicker = {},onShowDatePicker = {}
        )
    }
}