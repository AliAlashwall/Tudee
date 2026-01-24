package com.example.tudee.presentation.components

import androidx.annotation.DrawableRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
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
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.tudee.R
import com.example.tudee.presentation.design.theme.Theme
import com.example.tudee.presentation.design.theme.TudeeTheme

@Composable
fun OverviewCard(
    currentDate: String = "today, 22 Jun 2025",
    @DrawableRes statusIconId: Int = R.drawable.ic_status_neutral,
    @DrawableRes tudeeStatusImgId: Int = R.drawable.im_robot_neutral,
    notificationTitle: String = "Stay working!",
    notificationDescription: String = "You've completed 3 out of 10 tasks Keep going!"
) {
    Card(
        colors = CardDefaults.cardColors(Theme.colors.surfaceHigh),
        shape = RoundedCornerShape(16.dp),
        elevation = CardDefaults.cardElevation(10.dp)
    ) {

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 8.dp),
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
    }
}


@Preview
@Composable
private fun OverviewCardPreview() {
    TudeeTheme {
        OverviewCard()
    }
}