package com.example.tudee.presentation.components

import androidx.annotation.DrawableRes
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.tudee.R
import com.example.tudee.presentation.designSystem.theme.Theme
import com.example.tudee.presentation.designSystem.theme.TudeeTheme

@Composable
fun TaskCard(
    @DrawableRes taskIcon: Int,
    priorityLevel: Int,
    title: String,
    body: String
) {
    Card(
        modifier = Modifier
            .height(111.dp)
            .fillMaxWidth(),
        colors = CardDefaults.cardColors(Theme.colors.surfaceHigh),
    ) {
        Column(
            Modifier
                .fillMaxWidth()
                .padding(start = 12.dp),
            verticalArrangement = Arrangement.spacedBy(2.dp)
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.padding(start = 4.dp, end = 12.dp, top = 14.dp)
            ) {
                Icon(
                    painter = painterResource(taskIcon),
                    contentDescription = stringResource(R.string.task_card_icon),
                    modifier = Modifier.size(32.dp),
                    tint = Color.Unspecified
                )

                Spacer(Modifier.weight(1f))

                PriorityButton(
                    priorityLevel = priorityLevel,
                    selected = true,
                    onClicked = {}
                )
            }
            Text(
                text = title,
                color = Theme.colors.body,
                style = Theme.textStyle.label.large
            )

            Text(
                text = body,
                color = Theme.colors.hint,
                style = Theme.textStyle.label.small,
                maxLines = 1
            )
        }
    }
}

@Preview
@Composable
private fun TaskCardPreview() {
    TudeeTheme {
        TaskCard(
            taskIcon = R.drawable.ic_quran,
            title = "Organize Study Desk",
            body = "Review cell structure and functions for tomorrow...",
            priorityLevel = 0
        )
    }
}