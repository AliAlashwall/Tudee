package com.example.tudee.presentation.screens.categories

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
import com.example.tudee.R
import com.example.tudee.database.entity.TasksEntity
import com.example.tudee.presentation.designSystem.theme.Theme
import com.example.tudee.presentation.designSystem.theme.TudeeTheme
import com.example.tudee.presentation.screens.home.TaskStatus
import com.example.tudee.presentation.screens.tasks.TaskTabs


@Composable
fun CategoryTasksScreen(modifier: Modifier = Modifier) {
//    CategoryTasksScreenContent()
}

@Composable
fun CategoryTasksScreenContent(
    modifier: Modifier = Modifier,
    categoryTitle: String,
    selectedTab: TaskStatus,
    onBackClicked: () -> Unit,
    onEditClicked: () -> Unit,
    tasksList: List<TasksEntity>,
    onTaBClicked: () -> Unit,
    onSwapTaskCard: () -> Unit
) {
    Column(
        modifier = modifier.fillMaxSize(),
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
                text = categoryTitle,
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
            onTabClicked = { onTaBClicked() },
            onSwapTaskCard = { onSwapTaskCard() }
        )


    }

}

@Preview(showBackground = true)
@Composable
private fun CategoryTasksScreenPreview() {
    TudeeTheme {
        CategoryTasksScreenContent(
            categoryTitle = "Reading novel",
            onBackClicked = {},
            onEditClicked = {},
            selectedTab = TaskStatus.IN_PROGRESS,
            tasksList = listOf(
                TasksEntity(
                    id = 1,
                    title = "First",
                    description = "First description",
                    categoryIcon = (R.drawable.ic_quran).toString(),
                    priority = 0,
                    status = TaskStatus.TO_DO.label,
                    date = "22-6-2025"
                )
            ),
            onTaBClicked = { },
            onSwapTaskCard = {},
        )
    }
}