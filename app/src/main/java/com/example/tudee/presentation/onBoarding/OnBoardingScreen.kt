package com.example.tudee.presentation.onBoarding

import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.tudee.R
import com.example.tudee.presentation.design.theme.Theme
import com.example.tudee.presentation.design.theme.TudeeTheme
import kotlinx.coroutines.launch


enum class OnBoardingItems(
    val imgId: Int,
    val mainText: String,
    val subText: String
) {
    Page1(
        imgId = R.drawable.image_container_1,
        mainText = "Overwhelmed with tasks?",
        subText = "Let’s bring some order to the chaos. \nTudee is here to help you sort, plan, and breathe easier."
    ),
    Page2(
        imgId = R.drawable.image_container_3,
        mainText = "Uh-oh! Procrastinating again",
        subText = "Tudee not mad… just a little \ndisappointed, Let’s get back on track \ntogether."
    ),
    Page3(
        imgId = R.drawable.image_container_2,
        mainText = "Let’s complete task and celebrate\n" +
                "together.",
        subText = "Progress is progress. Tudee will\n celebrate you on for every win, big or \nsmall."
    )
}

@Composable
fun OnBoardingScreen(
    modifier: Modifier = Modifier,
    onCompleteScroll: () -> Unit,
) {

    Box(
        modifier = modifier
            .fillMaxSize()
            .background(Theme.colors.surface)
            .background(Theme.colors.overlay),
        contentAlignment = Alignment.TopCenter
    ) {
        val pagerState = rememberPagerState(initialPage = 0) { OnBoardingItems.entries.size }

        Image(
            painterResource(R.drawable.background_splash_light),
            contentDescription = "Splash Background",
            modifier = Modifier.fillMaxWidth(),
            contentScale = ContentScale.FillWidth,
        )
        Box(contentAlignment = Alignment.BottomCenter) {

            Column{
                Button(
                    onClick = { onCompleteScroll() },
                    colors = ButtonDefaults.buttonColors(Color.Transparent),
                    contentPadding = PaddingValues(0.dp)
                ) {
                    Text(
                        "Skip",
                        color = Theme.colors.primary,
                        style = Theme.textStyle.label.large,
                        textAlign = TextAlign.Start
                    )
                }
                HorizontalPager(state = pagerState) { pageIndex ->

                    val page = OnBoardingItems.entries[pageIndex]
                    OnBoardingPage(
                        imgId = page.imgId,
                        mainText = page.mainText,
                        subText = page.subText,
                    )
                }
                Spacer(Modifier.height(27.dp))
            }
            val scope = rememberCoroutineScope()

            IconButton(
                onClick = {

                    if (pagerState.currentPage < OnBoardingItems.entries.lastIndex) {
                        scope.launch {
                            pagerState.animateScrollToPage(
                                page = pagerState.currentPage + 1,
                                animationSpec = tween(
                                    durationMillis = 800,
                                    easing = FastOutSlowInEasing
                                )
                            )
                        }
                    } else {
                        onCompleteScroll()
                    }

                },
                colors = IconButtonDefaults.iconButtonColors(containerColor = Theme.colors.primary),
                modifier = Modifier.size(64.dp)
            ) {
                Icon(
                    painterResource(R.drawable.arrow_right_double),
                    contentDescription = "Next",
                    tint = Theme.colors.onPrimary,
                    modifier = Modifier
                        .padding(18.dp)
                        .size(28.dp),
                )
            }
        }
    }
}


@Preview(showSystemUi = true)
@Composable
private fun OnBoardingScreenPreview() {
    TudeeTheme { OnBoardingScreen(onCompleteScroll = {}) }
}