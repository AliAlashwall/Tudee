package com.example.tudee.presentation.components

import androidx.annotation.DrawableRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.tudee.R
import com.example.tudee.presentation.designSystem.theme.Theme
import com.example.tudee.presentation.designSystem.theme.TudeeTheme
import com.example.tudee.presentation.screens.home.TaskStatus

@Composable
fun OverviewCard(
    modifier: Modifier = Modifier,
    currentDate: String,
    @DrawableRes statusIconId: Int,
    @DrawableRes tudeeStatusImgId: Int,
    notificationTitle: String,
    notificationDescription: String,
    todoTasksCount: Int = 0,
    inProgressTasksCount: Int = 0,
    doneTasksCount: Int = 0,
) {
    Box {
        Box(
            modifier = modifier
                .fillMaxWidth()
                .height(60.dp)
                .background(Theme.colors.primary)
        )

        Card(
            colors = CardDefaults.cardColors(Theme.colors.surfaceHigh),
            shape = RoundedCornerShape(16.dp),
            elevation = CardDefaults.cardElevation(10.dp),
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp)
        ) {
            OverviewDateRow(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 8.dp),
                currentDate = currentDate
            )

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 6.dp),
                verticalAlignment = Alignment.Top,
                horizontalArrangement = Arrangement.Start,
            ) {
                Column(
                    horizontalAlignment = Alignment.Start,
                    verticalArrangement = Arrangement.Center,
                    modifier = Modifier
                        .padding(top = 16.dp)
                        .weight(1f)
                ) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Text(
                            text = notificationTitle,
                            style = Theme.textStyle.title.medium,
                            color = Theme.colors.title,
                            textAlign = TextAlign.Start
                        )
                        Spacer(Modifier.width(8.dp))

                        Image(
                            painterResource(statusIconId),
                            contentDescription = "status icon",
                            modifier = Modifier.size(20.dp)
                        )
                    }

                    Text(
                        text = notificationDescription,
                        style = Theme.textStyle.body.medium,
                        color = Theme.colors.body,
                        textAlign = TextAlign.Start
                    )
                }
//            Spacer(Modifier.weight(1f))

                Box(contentAlignment = Alignment.Center) {
                    Image(
                        painterResource(R.drawable.circle_background),
                        contentDescription = "circle bg",
                        modifier = Modifier.size(76.dp)
                    )
                    Image(
                        modifier = Modifier.size(61.dp, 92.dp),
                        painter = painterResource(tudeeStatusImgId),
                        contentDescription = "tudee overview img"
                    )
                }
            }

            Spacer(Modifier.height(8.dp))

            Text(
                text = "Overview",
                style = Theme.textStyle.title.large,
                color = Theme.colors.title,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 6.dp, vertical = 8.dp),
                textAlign = TextAlign.Start
            )

            Row(
                Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 6.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                ProgressOverviewCard(
                    state = TaskStatus.DONE.label,
                    cardColor = Theme.colors.greenAccent,
                    icon = R.drawable.ic_done_tasks,
                    progressCount = doneTasksCount
                )

                ProgressOverviewCard(
                    state = TaskStatus.IN_PROGRESS.label,
                    cardColor = Theme.colors.yellowAccent,
                    icon = R.drawable.ic_in_progress_tasks,
                    progressCount = inProgressTasksCount
                )

                ProgressOverviewCard(
                    state = TaskStatus.TO_DO.label,
                    cardColor = Theme.colors.purpleAccent,
                    icon = R.drawable.ic_to_do_tasks,
                    progressCount = todoTasksCount
                )

            }
            Spacer(Modifier.height(12.dp))
        }
    }

}


@Composable
fun ProgressOverviewCard(
    modifier: Modifier = Modifier,
    cardColor: Color = Theme.colors.greenAccent,
    @DrawableRes icon: Int = R.drawable.ic_done_tasks,
    progressCount: Int = 0,
    state: String
) {
    Box(modifier = modifier.size(96.dp, 110.dp)) {

        Card(
            colors = CardDefaults.cardColors(cardColor),
            shape = RoundedCornerShape(16.dp),
        ) {


            Column(
                Modifier
                    .fillMaxSize()
                    .padding(start = 12.dp, end = 12.dp, bottom = 10.dp, top = 12.dp),
//            verticalArrangement = Arrangement.spacedBy(4.dp)
            ) {
                Box(
                    modifier = Modifier
                        .size(40.dp)
                        .background(
                            color = Color(0x3DFFFFFF),
                            shape = RoundedCornerShape(10.dp)
                        )
                ) {
                    Icon(
                        painter = painterResource(icon),
                        contentDescription = "done tasks icon",
                        tint = Theme.colors.onPrimary,
                        modifier = Modifier
                            .size(20.dp, 22.dp)
                            .align(Alignment.Center)
                    )
                }

                Text(
                    text = progressCount.toString(),
                    style = Theme.textStyle.headline.medium,
                    color = Theme.colors.onPrimary
                )
                Text(
                    text = state,
                    style = Theme.textStyle.label.small,
                    color = Theme.colors.onPrimaryCaption
                )
            }
        }
    }
}

@Preview
@Composable
private fun ProgressOverviewCardPreview() {
    TudeeTheme {
        ProgressOverviewCard(state = "Done")
    }
}

@Composable
fun OverviewDateRow(
    modifier: Modifier = Modifier,
    currentDate: String
) {
    Row(
        modifier = modifier,
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center
    ) {
        Icon(
            painter = painterResource(R.drawable.ic_calendar_favorite),
            contentDescription = "calendar icon",
            tint = Theme.colors.body, modifier = Modifier.size(14.dp)
        )
        Spacer(modifier = Modifier.width(8.dp))

        Text(
            text = currentDate,
            color = Theme.colors.body,
            style = Theme.textStyle.label.medium
        )
    }
}


@Preview
@Composable
private fun OverviewCardPreview() {
    TudeeTheme {
        OverviewCard(
            currentDate = "22-6-2025",
            statusIconId = R.drawable.ic_status_neutral,
            tudeeStatusImgId = R.drawable.im_robot_neutral,
            notificationTitle = "Stay working",
            notificationDescription = "You have 3 tasks to do today, let's get it done!"
        )
    }
}