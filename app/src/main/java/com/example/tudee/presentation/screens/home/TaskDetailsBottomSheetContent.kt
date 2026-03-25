package com.example.tudee.presentation.screens.home

import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.tudee.R
import com.example.tudee.database.entity.TasksEntity
import com.example.tudee.presentation.components.CategoryCard
import com.example.tudee.presentation.components.PriorityButton
import com.example.tudee.presentation.components.SecondaryButton
import com.example.tudee.presentation.designSystem.theme.Theme
import com.example.tudee.presentation.screens.home.components.StatusCard

@Composable
fun TaskDetailsBottomSheetContent(
    modifier: Modifier = Modifier,
    task: TasksEntity,
    onMoveButtonClicked: () -> Unit,
    onEditButtonClicked: () -> Unit
) {
    val buttonText = when (task.status) {
        TaskStatus.TO_DO.label -> stringResource(R.string.move_to_in_progress)
        TaskStatus.IN_PROGRESS.label -> stringResource(R.string.move_to_done)
        else -> ""
    }
    Column(
        modifier = modifier
            .padding(horizontal = 16.dp),
    ) {
        Text(
            text = stringResource(R.string.task_details),
            color = Theme.colors.title,
            style = Theme.textStyle.title.large,
            modifier = Modifier.fillMaxWidth(),
            textAlign = TextAlign.Start
        )
        Spacer(Modifier.height(12.dp))

        CategoryCard(
            icon = task.categoryIcon,
            label = "",
            count = null,
            showCount = false,
            isPredefined = false,

        )

        Spacer(Modifier.height(8.dp))
        Text(
            text = task.title,
            color = Theme.colors.title,
            style = Theme.textStyle.title.medium,
            modifier = Modifier.fillMaxWidth(),
            textAlign = TextAlign.Start
        )
        Spacer(Modifier.height(8.dp))
        Text(
            text = task.description,
            color = Theme.colors.body,
            style = Theme.textStyle.body.small,
            modifier = Modifier.fillMaxWidth(),
            textAlign = TextAlign.Start
        )

        HorizontalDivider(
            thickness = 1.dp,
            color = Theme.colors.stroke,
            modifier = Modifier.padding(vertical = 12.dp)
        )

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            StatusCard(task = task)

            PriorityButton(
                priorityLevel = task.priority,
                selected = true,
            )
        }

        if (task.status != TaskStatus.DONE.label) {
            Row(
                Modifier
                    .fillMaxWidth()
                    .padding(vertical = 24.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(4.dp)
            ) {
                Box(
                    modifier = Modifier
                        .clickable { onEditButtonClicked() }
                        .border(
                            width = 1.dp, color = Theme.colors.stroke,
                            shape = RoundedCornerShape(25.dp)
                        )
                ) {
                    Icon(
                        painterResource(R.drawable.ic_pencil_edit_filled),
                        contentDescription = stringResource(R.string.edit_task),
                        modifier = Modifier
                            .padding(horizontal = 24.dp, vertical = 16.dp)
                            .size(24.dp),
                        tint = Theme.colors.primary
                    )


                }
                SecondaryButton(
                    text = buttonText,
                    onClick = { onMoveButtonClicked() },
                    isEnabled = true,
                    modifier = Modifier.fillMaxWidth()
                )
            }
        }
    }
}

@Preview(showBackground = true, showSystemUi = false, backgroundColor = 0xFFF9F9F9)
@Composable
private fun TaskDetailsBottomSheetContentPreview() {
    TaskDetailsBottomSheetContent(
        task = TasksEntity(
            title = "Organize Study Desk",
            categoryIcon = (R.drawable.ic_book_open).toString(),
            description = "Solve all exercises from page 45 to 50 in the textbook, Solve all exercises from page 45 to 50 in the textbook.",
            priority = 0,
            status = TaskStatus.TO_DO.label,
            date = "22-6-2025"
        ),
        onMoveButtonClicked = {},
        onEditButtonClicked = {},
    )
}