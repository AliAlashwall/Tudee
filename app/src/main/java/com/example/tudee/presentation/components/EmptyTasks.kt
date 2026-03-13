package com.example.tudee.presentation.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.tudee.R
import com.example.tudee.presentation.designSystem.theme.Theme
import com.example.tudee.presentation.designSystem.theme.TudeeTheme

@Composable
fun EmptyTasks(modifier: Modifier = Modifier) {
    Box(modifier = modifier) {
        Image(
            painterResource(R.drawable.empty_task_img),
            contentDescription = "Empty Tasks Image",
            modifier = Modifier
                .padding(start = 181.dp, end = 5.dp, top = 12.dp)
                .size(168.dp)
        )

        Card(
            colors = CardDefaults.cardColors(Theme.colors.surfaceHigh),
            shape = RoundedCornerShape(
                topStart = 16.dp,
                topEnd = 16.dp,
                bottomStart = 16.dp,
                bottomEnd = 2.dp
            )
        ) {
            Column(
                Modifier.padding(vertical = 8.dp, horizontal = 12.dp),
                verticalArrangement = Arrangement.spacedBy(4.dp)
            ) {
                Text(
                    text = "No tasks for today!",
                    color = Theme.colors.body,
                    style = Theme.textStyle.title.small
                )

                Text(
                    text = "Tap the + button to add your \n" + "first one.",
                    style = Theme.textStyle.body.small,
                    color = Theme.colors.hint
                )
            }


        }
    }
}

@Preview
@Composable
private fun EmptyTasksPreview() {
    TudeeTheme {
        EmptyTasks()
    }
}