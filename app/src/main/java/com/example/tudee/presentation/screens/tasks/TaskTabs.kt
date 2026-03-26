package com.example.tudee.presentation.screens.tasks

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.PrimaryTabRow
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRowDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.tudee.R
import com.example.tudee.domain.model.Category
import com.example.tudee.domain.model.Task
import com.example.tudee.presentation.designSystem.theme.Theme
import com.example.tudee.presentation.screens.home.TaskStatus

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TaskTabs(
    modifier: Modifier = Modifier,
    selectedTab: TaskStatus,
    statusTasksList: List<Task>,
    onTabClicked: (TaskStatus) -> Unit,
    onSwapTaskCard: (Task) -> Unit,
    allCategories: Map<Int, Category>,
) {
    val tabs = remember { TaskStatus.entries }

    // Calculate index once
    val selectedTabIndex = remember(selectedTab) {
        tabs.indexOf(selectedTab)
    }

    Column(modifier = modifier.background(Theme.colors.surface)) {
        PrimaryTabRow(
            selectedTabIndex = selectedTabIndex,
            containerColor = Theme.colors.surfaceHigh,
            indicator = {
                TabRowDefaults.PrimaryIndicator(
                    modifier = Modifier.tabIndicatorOffset(tabs.indexOf(selectedTab)),
                    color = Theme.colors.secondary,
                    width = 65.dp
                )
            }) {
            tabs.forEachIndexed { index, taskStatus ->
                val isSelected = (index == selectedTabIndex)
                val textStyle =
                    if (isSelected) Theme.textStyle.label.medium else Theme.textStyle.label.small
                Tab(
                    selected = isSelected,
                    onClick = { onTabClicked(taskStatus) },
                    text = {
                        Row(
                            horizontalArrangement = Arrangement.spacedBy(6.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(
                                text = taskStatus.label,
                                color = if (isSelected) Theme.colors.title else Theme.colors.hint,
                                style = textStyle
                            )
                            if (isSelected && statusTasksList.isNotEmpty()) {
                                Box(
                                    modifier = Modifier
                                        .size(28.dp)
                                        .background(
                                            color = Theme.colors.surface,
                                            shape = RoundedCornerShape(14.dp),
                                        ),
                                    contentAlignment = Alignment.Center
                                ) {
                                    Text(
                                        text = statusTasksList.size.toString(),
                                        color = Theme.colors.body,
                                        style = textStyle
                                    )
                                }
                            }
                        }
                    }
                )
            }
        }

        Spacer(Modifier.height(12.dp))

        if (statusTasksList.isNotEmpty()) {
            LazyColumn(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f),
                verticalArrangement = Arrangement.spacedBy(8.dp),
            ) {
                items(
                    items = statusTasksList,
                    key = { it.id }
                ) { task ->
                    val category =
                        allCategories[task.categoryId]
                            ?: Category(
                                name = "Unknown",
                                icon = R.drawable.reading_novels, count = 0
                            )
                    val categoryImage =
                        if (category.isCustom) category.uriImage else category.icon.toString()
                    SwipeableTaskCard(
                        task = task,
                        categoryIcon = categoryImage,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 16.dp),
                        onSwap = { onSwapTaskCard(it) },
                    )
                }
            }
        }
    }
}


@Preview
@Composable
private fun TaskTabsPreview() {
    TaskTabs(
        selectedTab = TaskStatus.IN_PROGRESS,
        onTabClicked = {},
        statusTasksList = listOf(
            Task(
                id = 1,
                title = "First",
                description = "First description",
                categoryId = 0,
                priority = 0,
                status = TaskStatus.TO_DO.label,
                date = "22-6-2025"
            )
        ),
        onSwapTaskCard = {},
        allCategories = emptyMap()
    )
}