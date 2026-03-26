package com.example.tudee.presentation.screens.home.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.example.tudee.R
import com.example.tudee.domain.model.Task
import com.example.tudee.presentation.designSystem.theme.Theme
import com.example.tudee.presentation.screens.home.TaskStatus

@Composable
fun StatusCard(task: Task) {

    val textColor = when (task.status) {
        TaskStatus.TO_DO.label -> Theme.colors.yellowAccent
        TaskStatus.IN_PROGRESS.label -> Theme.colors.purpleAccent
        TaskStatus.DONE.label -> Theme.colors.greenAccent
        else -> Theme.colors.yellowVariant
    }

    val cardColor = when (task.status) {
        TaskStatus.TO_DO.label -> Theme.colors.yellowVariant
        TaskStatus.IN_PROGRESS.label -> Theme.colors.purpleVariant
        TaskStatus.DONE.label -> Theme.colors.greenVariant
        else -> Theme.colors.yellowVariant
    }
    Card(
        colors = CardDefaults.cardColors(cardColor)
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(4.dp),
            modifier = Modifier
                .padding(horizontal = 12.dp, vertical = 6.dp)
        ) {
            Icon(
                painterResource(R.drawable.dot),
                contentDescription = stringResource(R.string.task_details),
                modifier = Modifier.size(5.dp),
                tint = textColor
            )

            Spacer(Modifier.width(4.dp))

            Text(
                text = task.status,
                color = textColor,
                style = Theme.textStyle.label.small
            )
        }
    }
}

