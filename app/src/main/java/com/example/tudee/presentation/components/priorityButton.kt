package com.example.tudee.presentation.components

import androidx.annotation.DrawableRes
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.tudee.R
import com.example.tudee.presentation.designSystem.theme.Theme

@Composable
fun PriorityButton(
    modifier: Modifier = Modifier,
    textContent: String,
    @DrawableRes startIcon: Int,
    onClicked: () -> Unit = {},
    buttonColor: Color,
    textColor : Color = Theme.colors.hint
) {
    Button(
        shape = RoundedCornerShape(50.dp),
        onClick = {
            onClicked()
        },
        colors = ButtonDefaults.buttonColors(buttonColor),
        modifier = modifier.height(28.dp),
        contentPadding = PaddingValues(vertical = 6.dp, horizontal = 8.dp)
    ) {
        Icon(
            painter = painterResource(startIcon),
            contentDescription = "high Priority",
            tint = Theme.colors.hint,
            modifier = Modifier.size(12.dp)
        )

        Spacer(Modifier.width(2.dp))

        Text(
            text = textContent,
            color = textColor,
            style = Theme.textStyle.label.small
        )
    }
}

@Preview
@Composable
private fun PriorityButtonPreview() {
    PriorityButton(
        textContent = stringResource(R.string.priority_high),
        startIcon = R.drawable.ic_flag,
        buttonColor =   Theme.colors.surfaceLow,
        textColor =  Theme.colors.hint,
        onClicked = { }
    )
}