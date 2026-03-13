package com.example.tudee.presentation.screens.home

import android.annotation.SuppressLint
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.tudee.R
import com.example.tudee.presentation.components.CategoryCard
import com.example.tudee.presentation.components.DateRangePicker
import com.example.tudee.presentation.components.EmptyTasks
import com.example.tudee.presentation.components.HomeTopBar
import com.example.tudee.presentation.components.OverviewCard
import com.example.tudee.presentation.components.PriorityButton
import com.example.tudee.presentation.components.TudeeBoxWithIcon
import com.example.tudee.presentation.components.TudeeTextButton
import com.example.tudee.presentation.components.TudeeTextField
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
    val homeUiState = homeViewModel.homeUiState.collectAsState().value
    Scaffold(
        floatingActionButton = {
            FloatingActionButton(
                onClick = { homeViewModel.onFABClicked() },
                containerColor = Theme.colors.primary
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
                .fillMaxSize()
                .background(Theme.colors.surface),
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Box {
                Box(
                    modifier = Modifier
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
                        currentDate = "today, 22 Jun 2025",
                        statusIconId = R.drawable.ic_status_neutral,
                        tudeeStatusImgId = R.drawable.im_robot_neutral,
                        notificationTitle = stringResource(R.string.stay_working),
                        notificationDescription = stringResource(R.string.stay_working_description)
                    )

                    Spacer(Modifier.height(48.dp))

                    EmptyTasks(modifier = Modifier.fillMaxWidth())

                    if (homeUiState.showBottomSheet) {
                        TudeeBottomSheet(onDismissRequest = {
                            homeViewModel.controlBottomSheetVisibility(false)
                        }) {
                            BottomSheetContent(
                                modifier = Modifier.background(Theme.colors.surface),
                                newTaskTitle = homeUiState.newTaskTitle,
                                newDescription = homeUiState.newDescription,
                                currentPriority = homeUiState.currentPriority,
                                selectedDate = homeUiState.selectedDate,
                                onNewTaskTitleChange = { homeViewModel.onNewTaskTitleChange(it) },
                                onNewDescriptionChange = {
                                    homeViewModel.onNewTaskDescriptionChange(
                                        it
                                    )
                                },
                                updateCurrentPriority = { homeViewModel.updateCurrentPriority(it) },
                                updateSelectedDate = { homeViewModel.updateSelectedDate(it) }
                            )
                        }
                    }
                }
            }
        }
    }
}

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun BottomSheetContent(
    modifier: Modifier = Modifier,
    newTaskTitle: String,
    newDescription: String,
    currentPriority: String,
    selectedDate: String,
    maxHeight: Dp = 600.dp,
    onNewTaskTitleChange: (String) -> Unit,
    onNewDescriptionChange: (String) -> Unit,
    updateCurrentPriority: (String) -> Unit,
    updateSelectedDate: (String) -> Unit
) {
    var showDialog by remember { mutableStateOf(false) }
    val priority = mapOf<Int, String>(
        0 to stringResource(R.string.priority_high),
        1 to stringResource(R.string.priority_medium),
        2 to stringResource(R.string.priority_low)
    )
    LazyColumn(
        horizontalAlignment = Alignment.Start,
        verticalArrangement = Arrangement.spacedBy(16.dp),
        modifier = modifier
            .heightIn(max = maxHeight)
            .padding(horizontal = 16.dp)
    ) {
        item {
            Text(
                text = stringResource(R.string.add_new_task),
                modifier = Modifier.fillMaxWidth(),
                textAlign = TextAlign.Start,
                style = Theme.textStyle.title.large,
                lineHeight = 24.sp
            )
        }

        item {
            TudeeTextField(
                value = newTaskTitle,
                onValueChange = { onNewTaskTitleChange(it) },
                hint = stringResource(R.string.task_title),
                startIcon = painterResource(R.drawable.ic_document_outlined),
                singleLine = true,
                keyboardOptions = KeyboardOptions.Default,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(56.dp)
            )
        }

        item {
            TudeeTextField(
                value = newDescription,
                onValueChange = { onNewDescriptionChange(it) },
                hint = stringResource(R.string.description),
                singleLine = true,
                keyboardOptions = KeyboardOptions.Default,
                placeholderAlignment = Alignment.TopStart,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(168.dp)
            )
        }

        item {
            TudeeBoxWithIcon(
                startIcon = painterResource(R.drawable.ic_calendar_add),
                onClick = {
                    updateSelectedDate(selectedDate)
                    showDialog = true
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(56.dp)
            ) {
                Text(
                    text = selectedDate,
                    style = Theme.textStyle.body.medium,
                    color = Theme.colors.body
                )
            }
        }

        item {
            if (showDialog) {
                DateRangePicker(
                    onDismissRequest = { showDialog = false },
                    onConfirm = { updateSelectedDate(it) })
            }
        }

        item {
            Text(
                text = stringResource(R.string.priority),
                modifier = Modifier.fillMaxWidth(),
                textAlign = TextAlign.Start,
                style = Theme.textStyle.title.medium,
                lineHeight = 22.sp
            )
        }

        item {
            Row(
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.fillMaxWidth()
            ) {
                PriorityButton(
                    textContent = stringResource(R.string.priority_high),
                    startIcon = R.drawable.ic_flag,
                    buttonColor = if (currentPriority == priority[0]) Theme.colors.error else Theme.colors.surfaceLow,
                    textColor = if (currentPriority == priority[0]) Theme.colors.onPrimary else Theme.colors.hint,
                    onClicked = { updateCurrentPriority(priority[0]!!) }
                )

                PriorityButton(
                    textContent = stringResource(R.string.priority_medium),
                    startIcon = R.drawable.ic_alert,
                    buttonColor = if (currentPriority == priority[1]) Theme.colors.yellowAccent else Theme.colors.surfaceLow,
                    textColor = if (currentPriority == priority[1]) Theme.colors.onPrimary else Theme.colors.hint,
                    onClicked = { updateCurrentPriority(priority[1]!!) }
                )

                PriorityButton(
                    textContent = stringResource(R.string.priority_low),
                    startIcon = R.drawable.ic_trade_down,
                    buttonColor = if (currentPriority == priority[2]) Theme.colors.greenAccent else Theme.colors.surfaceLow,
                    textColor = if (currentPriority == priority[2]) Theme.colors.onPrimary else Theme.colors.hint,
                    onClicked = { updateCurrentPriority(priority[2]!!) }
                )
            }
        }

        item {
            Text(
                text = stringResource(R.string.category),
                modifier = Modifier.fillMaxWidth(),
                textAlign = TextAlign.Start,
                style = Theme.textStyle.title.medium,
                lineHeight = 22.sp
            )
        }

        item {
            FlowRow(
                maxItemsInEachRow = 3,
                horizontalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 13.dp)
            ) {
                categoryList.forEach { category ->

                    CategoryCard(
                        icon = painterResource(id = category[1] as Int),
                        label = category[0] as String,
                        selected = false,
                        showCount = false,
                        iconTint = Color.Unspecified,
                        isPredefined = true,
                        modifier = Modifier
                            .padding(bottom = if (categoryList.indexOf(category) <= categoryList.size - 3) 24.dp else 0.dp)
                    )

                }
            }
        }

    }


    Column(
        Modifier
            .background(Color.White)
            .padding(vertical = 12.dp, horizontal = 16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        TudeeTextButton(
            text = stringResource(R.string.add_bottom_sheet),
            onClick = { /*TODO*/ },
            modifier = Modifier
                .height(56.dp)
                .background(
                    color = Theme.colors.disable,
                    shape = RoundedCornerShape(50.dp)
                )
                .fillMaxWidth(),
            style = Theme.textStyle.label.large,
            colors = Theme.colors.stroke
        )

        TudeeTextButton(
            text = stringResource(R.string.cancel_bottom_sheet),
            onClick = { /*TODO*/ },
            modifier = Modifier
                .height(56.dp)
                .border(
                    width = 1.dp,
                    color = Theme.colors.stroke,
                    shape = RoundedCornerShape(50.dp)
                )
                .fillMaxWidth(),
            style = Theme.textStyle.label.large,
            colors = Theme.colors.primary
        )
    }


}

@RequiresApi(Build.VERSION_CODES.O)
@Preview
@Composable
private fun BottomSheetContentPreview() {
    TudeeTheme {
        BottomSheetContent(
            newTaskTitle = "",
            newDescription = "",
            currentPriority = "",
            selectedDate = "",
            onNewTaskTitleChange = {},
            onNewDescriptionChange = {},
            updateCurrentPriority = {},
            updateSelectedDate = {}
        )
    }
}

@RequiresApi(Build.VERSION_CODES.O)
@SuppressLint("ViewModelConstructorInComposable")
@Preview
@Composable
private fun HomeScreenPreview() {
    TudeeTheme {
        val navController = rememberNavController()
        val vm = HomeViewModel()
        HomeScreen(
            navController = navController,
            homeViewModel = vm
        )
    }
}