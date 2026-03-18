package com.example.tudee.presentation.screens.home

import android.annotation.SuppressLint
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.background
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
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.tudee.R
import com.example.tudee.presentation.components.CategoryCard
import com.example.tudee.presentation.components.CustomDateRangePicker
import com.example.tudee.presentation.components.EmptyTasks
import com.example.tudee.presentation.components.HomeTopBar
import com.example.tudee.presentation.components.OverviewCard
import com.example.tudee.presentation.components.PriorityButton
import com.example.tudee.presentation.components.TudeeBoxWithIcon
import com.example.tudee.presentation.components.TudeeTextField
import com.example.tudee.presentation.components.bottomSheet.BottomSheetButtons
import com.example.tudee.presentation.components.bottomSheet.TudeeBottomSheet
import com.example.tudee.presentation.designSystem.theme.Theme
import com.example.tudee.presentation.designSystem.theme.TudeeTheme
import com.example.tudee.presentation.screens.category.TudeeCategories.categoriesList

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
                        currentDate = "today, ${homeUiState.currentDate}",
                        statusIconId = R.drawable.ic_status_neutral,
                        tudeeStatusImgId = R.drawable.im_robot_neutral,
                        notificationTitle = stringResource(R.string.stay_working),
                        notificationDescription = stringResource(R.string.stay_working_description)
                    )

                    Spacer(Modifier.height(48.dp))

                    EmptyTasks(modifier = Modifier.fillMaxWidth())

                    if (homeUiState.showBottomSheet) {
                        TudeeBottomSheet(
                            modifier = Modifier.heightIn(max = 700.dp),
                            onDismissRequest = { homeViewModel.onDismissBottomSheet() })
                        {
                            BottomSheetContent(
                                modifier = Modifier.background(Theme.colors.surface),
                                newTaskTitle = homeUiState.newTaskTitle,
                                newDescription = homeUiState.newDescription,
                                currentPriority = homeUiState.currentPriority,
                                selectedDate = homeUiState.selectedDate,
                                selectedCategory = homeUiState.selectedCategory,
                                onNewTaskTitleChange = { homeViewModel.onNewTaskTitleChange(it) },
                                onNewDescriptionChange = {
                                    homeViewModel.onNewTaskDescriptionChange(
                                        it
                                    )
                                },
                                updateCurrentPriority = { homeViewModel.updateCurrentPriority(it) },
                                updateSelectedDate = { homeViewModel.updateSelectedDate(it) },
                                onClickCategory = { homeViewModel.updateSelectedCategory(it) },
                                enableAddTaskButton = homeViewModel.enableAddTaskButton(),
                                onCancelBottomSheetClicked = { homeViewModel.onDismissBottomSheet() },
                                onAddClicked = { /*TODO()*/ }
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
    currentPriority: Int?,
    selectedDate: String,
    selectedCategory: Int?,
    onNewTaskTitleChange: (String) -> Unit,
    onNewDescriptionChange: (String) -> Unit,
    updateCurrentPriority: (Int) -> Unit,
    updateSelectedDate: (String) -> Unit,
    onClickCategory: (Int) -> Unit,
    enableAddTaskButton: Boolean,
    onCancelBottomSheetClicked: (Boolean) -> Unit,
    onAddClicked: () -> Unit

) {
    // Persist dialog state across configuration changes
    var showDialog by rememberSaveable { mutableStateOf(false) }



    Box(modifier = Modifier.fillMaxSize()) {
        LazyColumn(
            horizontalAlignment = Alignment.Start,
            verticalArrangement = Arrangement.spacedBy(16.dp),
            modifier = modifier
                .padding(horizontal = 16.dp)
        ) {
            item {
                Text(
                    text = stringResource(R.string.add_new_task),
                    modifier = Modifier.fillMaxWidth(),
                    textAlign = TextAlign.Start,
                    style = Theme.textStyle.title.large,
                    lineHeight = 24.sp,
                    color = Theme.colors.title
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
                        // Don't update the ViewModel with the same value when opening the picker;
                        // that can trigger a recomposition which may interfere with the dialog state
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
                Text(
                    text = stringResource(R.string.priority),
                    modifier = Modifier.fillMaxWidth(),
                    textAlign = TextAlign.Start,
                    style = Theme.textStyle.title.medium,
                    lineHeight = 22.sp,
                    color = Theme.colors.title
                )
            }

            item {
                Row(
                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    //High priority Button
                    PriorityButton(
                        priorityLevel = 0,
                        selected = currentPriority == 0,
                        onClicked = { updateCurrentPriority(0) }
                    )

                    //Medium priority Button
                    PriorityButton(
                        priorityLevel = 1,
                        selected = currentPriority == 1,
                        onClicked = { updateCurrentPriority(1) }
                    )

                    //Low priority Button
                    PriorityButton(
                        priorityLevel = 2,
                        selected = currentPriority == 2,
                        onClicked = { updateCurrentPriority(2) }
                    )
                }
            }

            item {
                Text(
                    text = stringResource(R.string.category),
                    modifier = Modifier.fillMaxWidth(),
                    textAlign = TextAlign.Start,
                    style = Theme.textStyle.title.medium,
                    lineHeight = 22.sp,
                    color = Theme.colors.title
                )
            }

            item {
                // remember the static categories list so it's not re-evaluated on every recomposition
                val categories = remember { categoriesList }
                FlowRow(
                    maxItemsInEachRow = 3,
                    horizontalArrangement = Arrangement.SpaceBetween,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 13.dp)
                ) {
                    categories.forEachIndexed { categoryIndex, category ->
                        val bottomPadding =
                            if (categoryIndex <= categories.size - 3) 24.dp else 0.dp
                        CategoryCard(
                            icon = painterResource(id = category.icon),
                            label = category.name,
                            selected = (categoryIndex == selectedCategory),
                            showCount = false,
                            iconTint = Color.Unspecified,
                            isPredefined = true,
                            modifier = Modifier
                                .padding(bottom = bottomPadding),
                            onClickCategory = { onClickCategory(categoryIndex) }
                        )

                    }
                }
            }

        }

        if (showDialog) {
            CustomDateRangePicker(
                onDismissRequest = { showDialog = false },
                onConfirm = { updateSelectedDate(it) }
            )
        }

        Box(
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .fillMaxWidth()
        ) {
            BottomSheetButtons(
                enableAddTaskButton = enableAddTaskButton,
                onAddClicked = { onAddClicked() },
                onCancelBottomSheetClicked = { onCancelBottomSheetClicked(false) }
            )
        }
    }

}

@RequiresApi(Build.VERSION_CODES.O)
@Preview(showBackground = true)
@Composable
private fun BottomSheetContentPreview() {
    TudeeTheme {
        BottomSheetContent(
            newTaskTitle = "",
            newDescription = "",
            currentPriority = 0,
            enableAddTaskButton = false,
            selectedDate = "",
            selectedCategory = 0,
            onNewTaskTitleChange = {},
            onNewDescriptionChange = {},
            updateCurrentPriority = {},
            updateSelectedDate = {},
            onClickCategory = {},
            onCancelBottomSheetClicked = {},
            onAddClicked = {}
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