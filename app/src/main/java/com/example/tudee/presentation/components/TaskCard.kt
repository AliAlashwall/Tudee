package com.example.tudee.presentation.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.core.net.toUri
import coil.compose.AsyncImage
import com.example.tudee.R
import com.example.tudee.presentation.designSystem.theme.Theme
import com.example.tudee.presentation.designSystem.theme.TudeeTheme
import com.example.tudee.presentation.unit.isValidUri

@Composable
fun TaskCard(
    modifier: Modifier = Modifier,
    categoryIconOfTask: String,
    priorityLevel: Int,
    title: String,
    description: String,
    onClick: () -> Unit = {},
) {
    val isCustom = remember(categoryIconOfTask) { categoryIconOfTask.isValidUri() }
    Card(
        modifier = modifier
            .clickable { onClick() }
            .height(111.dp)
            .width(320.dp),
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
                if (categoryIconOfTask != "") {
                    if (isCustom) {
                        AsyncImage(
                            model = categoryIconOfTask.toUri(),
                            contentDescription = stringResource(R.string.task_card_icon),
                            modifier = Modifier.size(32.dp),
                        )
                    } else {
                        Image(
                            painter = painterResource(categoryIconOfTask.toInt()),
                            contentDescription = stringResource(R.string.task_card_icon),
                            modifier = Modifier.size(32.dp),
                        )
                    }
                }

                Spacer(Modifier.weight(1f))

                PriorityButton(
                    priorityLevel = priorityLevel,
                    selected = true,
                    onClicked = {}
                )
            }
            Spacer(Modifier.height(16.dp))
            Text(
                text = title,
                color = Theme.colors.body,
                style = Theme.textStyle.label.large
            )

            Text(
                text = description,
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
            categoryIconOfTask = R.drawable.ic_quran.toString(),
            title = "Organize Study Desk",
            description = "Review cell structure and functions for tomorrow...",
            priorityLevel = 0
        )
    }
}