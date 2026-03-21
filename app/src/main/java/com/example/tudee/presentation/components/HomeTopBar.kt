package com.example.tudee.presentation.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.tudee.R
import com.example.tudee.presentation.components.tudeeSwitch.TudeeSwitch
import com.example.tudee.presentation.designSystem.theme.Theme
import com.example.tudee.presentation.designSystem.theme.TudeeTheme
import com.example.tudee.presentation.designSystem.typography.CherryBomb
import com.example.tudee.presentation.designSystem.typography.DefaultTextStyle

@Composable
fun HomeTopBar(modifier: Modifier = Modifier) {
    Row(
        modifier = modifier
            .background(Theme.colors.primary)
            .padding(horizontal = 16.dp)
            .fillMaxWidth()
            .height(90.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        Image(
            painter = painterResource(R.drawable.im_robot_smile),
            contentDescription = "App Logo",
            modifier = Modifier.size(48.dp)
        )

        Column {
            Text(
                text = "Tudee",
                fontSize = 18.sp,
                fontFamily = CherryBomb,
                color = Color.White
            )
            Text(
                text = "Your cute Helper for Every Task",
                style = DefaultTextStyle.label.small,
                color = Color.White
            )
        }
        Spacer(Modifier.weight(1f))

        TudeeSwitch()
    }

}

@Preview
@Composable
private fun HomeTopBarPreview() {
    TudeeTheme {
        HomeTopBar()
    }
}