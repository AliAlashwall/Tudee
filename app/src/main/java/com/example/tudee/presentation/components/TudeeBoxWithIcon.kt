package com.example.tudee.presentation.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.example.tudee.R
import com.example.tudee.presentation.designSystem.theme.Theme

@Composable
fun TudeeBoxWithIcon(
    modifier: Modifier = Modifier,
    startIcon: Painter? = null,
    iconColor: Color = Theme.colors.hint,
    onClick: () -> Unit = {},
    content: @Composable () -> Unit
) {
    Box(
        modifier = modifier
            .clickable { onClick() }
            .border(
                width = 1.dp,
                color = Theme.colors.stroke,
                shape = RoundedCornerShape(16.dp)
            )
            .padding(horizontal = 12.dp, vertical = if (startIcon != null) 16.dp else 12.dp)

    ) {
        Row(
            modifier = Modifier.matchParentSize()
        ) {
            if (startIcon != null) {
                Image(
                    painter = startIcon,
                    colorFilter = ColorFilter.tint(iconColor),
                    contentDescription = stringResource(R.string.text_field_icon),
                    modifier = Modifier.size(24.dp)
                )

                Box(
                    modifier = Modifier
                        .padding(horizontal = 12.dp)
                        .width(1.dp)
                        .height(30.dp)
                        .background(Theme.colors.stroke)
                )
            }
            content()
        }
    }
}