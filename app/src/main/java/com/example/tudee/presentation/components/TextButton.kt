package com.example.tudee.presentation.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.tudee.R
import com.example.tudee.presentation.designSystem.theme.Theme

@Composable
fun TudeeTextButton(
    text: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    style: TextStyle = Theme.textStyle.label.medium,
    colors: Color = Color.Blue,
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center,
        modifier = modifier .clickable(onClick = onClick)
    ) {
        TudeeText(
            text = text,
            style = style,
            color = colors,
            modifier = Modifier
                .padding(8.dp),
            textAlign = TextAlign.Center
        )
    }
}


@Preview
@Composable
private fun TudeeTextButtonPreview() {
    TudeeTextButton(
        text = stringResource(R.string.add_bottom_sheet),
        onClick = {  },
        modifier = Modifier
            .height(56.dp)
            .background(
                color = Theme.colors.disable,
                shape = RoundedCornerShape(50.dp)
            )
            .fillMaxWidth(),
        style = Theme.textStyle.title.large,
        colors = Theme.colors.error
    )
}

