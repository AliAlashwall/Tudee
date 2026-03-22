package com.example.tudee.presentation.screens.tasks

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.PrimaryTabRow
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRowDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.tudee.database.entity.TasksEntity
import com.example.tudee.presentation.components.TaskCard
import com.example.tudee.presentation.designSystem.theme.Theme
import com.example.tudee.presentation.screens.home.TaskStatus

@Composable
fun TaskTabs(
    selectedTab: TaskStatus,
    statusTasksList: List<TasksEntity>,
    onTabClicked: (TaskStatus) -> Unit,
    modifier: Modifier = Modifier,
) {
    val tabs = TaskStatus.entries

    // Calculate index once
    val selectedTabIndex = remember(selectedTab) {
        tabs.indexOf(selectedTab)
    }

    Column(modifier = modifier) {
        PrimaryTabRow(
            selectedTabIndex = selectedTabIndex,
            indicator = {
                TabRowDefaults.PrimaryIndicator(
                    modifier = Modifier.tabIndicatorOffset(tabs.indexOf(selectedTab)),
                    color = Theme.colors.secondary,
                    width = 65.dp
                )
            }) {
            tabs.forEachIndexed { index, taskStatus ->
                val isSelected = index == selectedTabIndex
                val textStyle =
                    if (isSelected) Theme.textStyle.label.medium else Theme.textStyle.label.small
                Tab(
                    selected = isSelected,
                    onClick = { onTabClicked(taskStatus) },
                    text = {
                        Row(horizontalArrangement = Arrangement.spacedBy(6.dp)) {
                            Text(
                                text = taskStatus.label,
                                color = if (isSelected) Theme.colors.title else Theme.colors.hint,
                                style = textStyle
                            )
                            if (isSelected && statusTasksList.isNotEmpty()) {
                                Text(
                                    text = statusTasksList.size.toString(),
                                    color = Theme.colors.body,
                                    style = textStyle
                                )
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
                    TaskCard(
                        taskIcon = task.categoryIcon,
                        priorityLevel = task.priority,
                        title = task.title,
                        description = task.description,
                        onClick = { },
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 16.dp)
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
        statusTasksList = emptyList(),
    )
}