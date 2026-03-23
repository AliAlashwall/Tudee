package com.example.tudee.presentation.screens.tasks

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectHorizontalDragGestures
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import com.example.tudee.R
import com.example.tudee.database.entity.TasksEntity
import com.example.tudee.presentation.components.TaskCard
import com.example.tudee.presentation.designSystem.theme.Theme
import kotlin.math.roundToInt

@Composable
fun SwipeableTaskCard(
    modifier: Modifier = Modifier,
    task: TasksEntity,
    onSwap: (TasksEntity) -> Unit = {}
) {
    val swipeThreshold = remember { -600f }
    var offsetX by remember { mutableFloatStateOf(0f) }
    val animatedOffset by animateFloatAsState(targetValue = offsetX, label = "swipe")

    if (offsetX == swipeThreshold) {
        onSwap(task)
        offsetX = 0f
    }


    Box(
        modifier = modifier.size(width = 320.dp, height = 111.dp)
    ) {
        // Background delete button (revealed on swipe)
        Box(
            modifier = Modifier
                .fillMaxSize()
                .clip(RoundedCornerShape(16.dp))
                .background(Theme.colors.errorVariant),
            contentAlignment = Alignment.CenterEnd
        ) {
            IconButton(
                onClick = { /* handle delete */ },
                modifier = Modifier.padding(end = 12.dp)
            ) {
                Icon(
                    painterResource(R.drawable.ic_delete),
                    contentDescription = "Delete",
                    tint = Theme.colors.error,
                    modifier = Modifier.size(32.dp)
                )
            }
        }

        // Foreground card
        TaskCard(
            taskIcon = task.categoryIcon,
            priorityLevel = task.priority,
            title = task.title,
            description = task.description,
            onClick = { },
            modifier = Modifier
                .fillMaxWidth()
                .offset { IntOffset(animatedOffset.roundToInt(), 0) }
                .pointerInput(Unit) {
                    detectHorizontalDragGestures(
                        onDragEnd = {
                            offsetX = if (offsetX < swipeThreshold / 2) swipeThreshold else 0f
                        },
                        onHorizontalDrag = { _, dragAmount ->
                            offsetX = (offsetX + dragAmount).coerceIn(swipeThreshold, 0f)
                        }
                    )
                }
        )

    }
}


@Preview
@Composable
private fun SwipeableTaskCardPreview() {
    SwipeableTaskCard(
        modifier = Modifier,
        task = TasksEntity(
            id = 1,
            title = "First",
            description = "First description",
            categoryIcon = (R.drawable.ic_quran),
            priority = 0,
            status = "To Do",
            date = "22-6-2025"
        )
    )
}