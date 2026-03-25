package com.example.tudee.presentation.screens.home

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
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
import androidx.core.net.toUri
import coil.compose.rememberAsyncImagePainter
import com.example.tudee.R
import com.example.tudee.database.entity.CategoryEntity
import com.example.tudee.presentation.components.CategoryCard
import com.example.tudee.presentation.components.CustomDateRangePicker
import com.example.tudee.presentation.components.PriorityButton
import com.example.tudee.presentation.components.TudeeBoxWithIcon
import com.example.tudee.presentation.components.TudeeTextField
import com.example.tudee.presentation.components.bottomSheet.BottomSheetButtons
import com.example.tudee.presentation.designSystem.theme.Theme
import com.example.tudee.presentation.designSystem.theme.TudeeTheme

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun TasksBottomSheetContent(
    modifier: Modifier = Modifier,
    sheetTitle: String = stringResource(R.string.add_new_task),
    primaryButtonText: String = stringResource(R.string.add_bottom_sheet),
    secondaryButtonText: String = stringResource(R.string.cancel_bottom_sheet),
    primaryButtonColor: Color,
    onPrimaryButtonColor: Color,
    secondaryButtonColor: Color,
    onSecondaryButtonColor: Color,
    newTaskTitle: String,
    newDescription: String,
    currentPriority: Int?,
    selectedDate: String,
    selectedCategoryIcon: String?,
    onNewTaskTitleChange: (String) -> Unit,
    onNewDescriptionChange: (String) -> Unit,
    updateCurrentPriority: (Int) -> Unit,
    updateSelectedDate: (String) -> Unit,
    onClickCategory: (String) -> Unit,
    onCancelBottomSheetClicked: (Boolean) -> Unit,
    onPrimaryButtonClicked: () -> Unit,
    categories: List<CategoryEntity>
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
                    text = sheetTitle,
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
                FlowRow(
                    maxItemsInEachRow = 3,
                    horizontalArrangement = Arrangement.SpaceBetween,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 13.dp)
                ) {


                    categories.forEachIndexed { categoryIndex, category ->

                        val categoryImage = remember {
                            (if (category.isCustom) {
                                category.uriImage
                            } else {
                                category.icon.toString()
                            })
                        }

                        val painter = if (category.isCustom) {
                            rememberAsyncImagePainter(model = categoryImage.toUri())
                        } else {
                            painterResource(id = categoryImage.toInt())
                        }
                        val bottomPadding =
                            if (categoryIndex <= categories.size - 3) 24.dp else 0.dp

                        CategoryCard(
                            icon = painter,
                            label = category.name,
                            selected = categoryImage == selectedCategoryIcon.toString(),
                            showCount = false,
                            isPredefined = true,
                            modifier = Modifier
                                .padding(bottom = bottomPadding),
                            onClickCategory = { onClickCategory(categoryImage) }
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
                onPrimaryButtonClicked = { onPrimaryButtonClicked() },
                onCancelBottomSheetClicked = { onCancelBottomSheetClicked(false) },
                primaryButtonText = primaryButtonText,
                secondaryButtonText = secondaryButtonText,
                primaryButtonColor = primaryButtonColor,
                onPrimaryButtonColor = onPrimaryButtonColor,
                secondaryButtonBorderColor = secondaryButtonColor,
                onSecondaryButtonColor = onSecondaryButtonColor
            )
        }
    }

}

@RequiresApi(Build.VERSION_CODES.O)
@Preview(showBackground = true)
@Composable
private fun TasksBottomSheetContentPreview() {
    TudeeTheme {
        TasksBottomSheetContent(
            newTaskTitle = "",
            newDescription = "",
            currentPriority = 0,
            selectedDate = "",
            selectedCategoryIcon = null,
            onNewTaskTitleChange = {},
            onNewDescriptionChange = {},
            updateCurrentPriority = {},
            updateSelectedDate = {},
            onClickCategory = {},
            onCancelBottomSheetClicked = {},
            onPrimaryButtonClicked = {},
            primaryButtonColor = Theme.colors.primary,
            onPrimaryButtonColor = Theme.colors.onPrimary,
            secondaryButtonColor = Theme.colors.secondary,
            onSecondaryButtonColor = Theme.colors.onPrimary,
            categories = emptyList()
        )
    }
}