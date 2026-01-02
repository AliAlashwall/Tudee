package com.example.tudee.presentation.onBoarding

import androidx.annotation.DrawableRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.tudee.R
import com.example.tudee.presentation.design.theme.Theme
import com.example.tudee.presentation.design.theme.TudeeTheme

@Composable
fun OnBoardingPage(
    modifier: Modifier = Modifier,
    @DrawableRes imgId: Int,
    mainText: String,
    subText: String,
) {

    Column(
        modifier = modifier
            .padding(16.dp),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(Modifier.height(153.dp))

        Image(
            painterResource(imgId),
            contentDescription = "Tudee Image",
            modifier = Modifier.size(296.dp, 250.dp),
            contentScale = ContentScale.FillBounds
        )
        Spacer(Modifier.height(32.dp))

        Column(
            modifier = Modifier
                .fillMaxWidth().height(192.dp)
                .clip(shape = RoundedCornerShape(32.dp))
                .border(width = 1.dp, color = Theme.colors.onPrimaryStroke)
                .background(Theme.colors.onPrimaryCard)
                .padding(start = 16.dp, end = 16.dp, top = 16.dp ),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = mainText,
                color = Theme.colors.title,
                style = Theme.textStyle.title.medium,
                textAlign = TextAlign.Center
            )

            Spacer(Modifier.height(24.dp))

            Text(
                text = subText,
                color = Theme.colors.body,
                style = Theme.textStyle.body.medium,
                textAlign = TextAlign.Center
            )
        }
    }
}


@Preview
@Composable
private fun OnBoardingPagePreview() {
    TudeeTheme {
        OnBoardingPage(
            imgId = R.drawable.im_robot_angry,
            mainText = "Organize your tasks?",
            subText = "Manage your tasks efficiently and boost your productivity with Tudee."
        )
    }
}