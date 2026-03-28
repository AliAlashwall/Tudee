package com.example.tudee.presentation.screens.categories

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import com.example.tudee.R
import com.example.tudee.domain.model.Category
import com.example.tudee.domain.model.Task
import com.example.tudee.presentation.designSystem.theme.Theme
import com.example.tudee.presentation.designSystem.theme.TudeeTheme
import com.example.tudee.presentation.screens.home.components.TaskStatus
import com.example.tudee.presentation.components.TaskTabs


@Composable
fun CategoryTasksScreen(categoryViewModel: CategoryViewModel, navController: NavController) {
    val categoryUiState = categoryViewModel.categoryUiState.collectAsStateWithLifecycle().value

    CategoryTasksScreenContent(
        category = categoryUiState.clickedCategory ?: Category(
            name = "Error",
            icon = R.drawable.ic_alert,
            count = 0
        ),
        selectedTab = categoryUiState.selectedTab,
        onBackClicked = { navController.popBackStack() },
        onEditClicked = {},
        tasksList = when (categoryUiState.selectedTab) {
            TaskStatus.IN_PROGRESS -> categoryUiState.inProgressTasks ?: emptyList()
            TaskStatus.TO_DO -> categoryUiState.todoTasks ?: emptyList()
            TaskStatus.DONE -> categoryUiState.doneTasks ?: emptyList()
        },
        onTabClicked = { categoryViewModel.onTabClicked(it) },
        onSwapTaskCard = {}
    )
}

@Composable
fun CategoryTasksScreenContent(
    modifier: Modifier = Modifier,
    category: Category,
    selectedTab: TaskStatus,
    onBackClicked: () -> Unit,
    onEditClicked: () -> Unit,
    tasksList: List<Task>,
    onTabClicked: (TaskStatus) -> Unit,
    onSwapTaskCard: () -> Unit
) {
    Column(
        modifier = modifier
            .background(color = Theme.colors.surfaceHigh)
            .fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Row(
            modifier = Modifier.padding(horizontal = 16.dp, vertical = 12.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            IconButton(
                onClick = { onBackClicked() },
                modifier = Modifier
                    .size(40.dp)
                    .border(
                        width = 1.dp,
                        color = Theme.colors.stroke,
                        shape = RoundedCornerShape(100.dp)
                    )
            ) {
                Icon(
                    painter = painterResource(R.drawable.ic_arrow_head_back),
                    contentDescription = stringResource(R.string.back_button),
                    modifier = Modifier.size(20.dp),
                    tint = Theme.colors.body
                )
            }

            Spacer(Modifier.width(12.dp))

            Text(
                text = category.name,
                color = Theme.colors.title,
                style = Theme.textStyle.title.large
            )

            Spacer(Modifier.weight(1f))

            IconButton(
                onClick = { onEditClicked() },
                modifier = Modifier
                    .size(40.dp)
                    .border(
                        width = 1.dp,
                        color = Theme.colors.stroke,
                        shape = RoundedCornerShape(100.dp)
                    )
            ) {
                Icon(
                    painter = painterResource(R.drawable.ic_pencil_edit),
                    contentDescription = stringResource(R.string.back_button),
                    modifier = Modifier.size(20.dp),
                    tint = Theme.colors.body
                )
            }
        }

        TaskTabs(
            selectedTab = selectedTab,
            statusTasksList = tasksList,
            onTabClicked = { onTabClicked(it) },
            onSwapTaskCard = { onSwapTaskCard() },
            allCategories = mapOf(          // as it has only the selected category
                Pair(
                    category.id,
                    category
                )
            )
        )
    }

}

@Preview(showBackground = true)
@Composable
private fun CategoryTasksScreenPreview() {
    TudeeTheme {
        CategoryTasksScreenContent(
            category = Category(
                name = "reading novels",
                icon = R.drawable.reading_novels,
                count = 0
            ),
            onBackClicked = {},
            onEditClicked = {},
            selectedTab = TaskStatus.IN_PROGRESS,
            tasksList = listOf(
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
            onTabClicked = { },
            onSwapTaskCard = {},
        )
    }
}