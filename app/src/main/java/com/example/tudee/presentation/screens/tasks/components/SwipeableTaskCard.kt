package com.example.tudee.presentation.screens.tasks.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.SwipeToDismissBox
import androidx.compose.material3.SwipeToDismissBoxValue
import androidx.compose.material3.rememberSwipeToDismissBoxState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.tudee.R
import com.example.tudee.domain.model.Task
import com.example.tudee.presentation.components.TaskCard
import com.example.tudee.presentation.designSystem.theme.Theme

@Composable
fun SwipeableTaskCard(
    modifier: Modifier = Modifier,
    task: Task,
    categoryIcon: String,
    erasable: Boolean = true,
    onSwap: (Task) -> Unit = {}
) {

    if (!erasable) {
        TaskCard(
            categoryIconOfTask = categoryIcon,
            priorityLevel = task.priority,
            title = task.title,
            description = task.description,
            modifier = modifier.fillMaxWidth()
        )
        return
    }

    val dismissState = rememberSwipeToDismissBoxState(
        confirmValueChange = { value ->
            if (value == SwipeToDismissBoxValue.EndToStart) {
                onSwap(task)
                false
            } else {
                false
            }
        }
    )

    SwipeToDismissBox(
        state = dismissState,
        enableDismissFromStartToEnd = false, // Only swipe left to delete
        backgroundContent = {
            val color = if (dismissState.dismissDirection == SwipeToDismissBoxValue.EndToStart) {
                Theme.colors.errorVariant
            } else {
                Color.Transparent
            }
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .clip(RoundedCornerShape(16.dp))
                    .background(color)
                    .padding(horizontal = 20.dp),
                contentAlignment = Alignment.CenterEnd
            ) {
                Icon(
                    painter = painterResource(R.drawable.ic_delete),
                    contentDescription = "Delete",
                    tint = Theme.colors.error,
                    modifier = Modifier.size(32.dp)
                )
            }
        },
        modifier = modifier
    ) {
        // Foreground card
        TaskCard(
            categoryIconOfTask = categoryIcon,
            priorityLevel = task.priority,
            title = task.title,
            description = task.description,
            modifier = Modifier.fillMaxWidth()
        )
    }

}


@Preview
@Composable
private fun SwipeableTaskCardPreview() {
    SwipeableTaskCard(
        modifier = Modifier,
        categoryIcon = "",
        task = Task(
            id = 1,
            title = "First",
            description = "First description",
            categoryId = 0,
            priority = 0,
            status = "To Do",
            date = "22-6-2025"
        )
    )
}