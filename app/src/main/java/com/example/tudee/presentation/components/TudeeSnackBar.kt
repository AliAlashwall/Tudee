package com.example.tudee.presentation.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.tudee.R
import com.example.tudee.presentation.designSystem.theme.Theme
import com.example.tudee.presentation.designSystem.theme.TudeeTheme

@Composable
fun TudeeSnackBar(message: String) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(12.dp),
        modifier = Modifier
            .background(
                color = Theme.colors.surfaceHigh,
                shape = RoundedCornerShape(16.dp)
            )
            .fillMaxWidth()
            .height(56.dp)
            .padding(horizontal = 8.dp)
    ) {
        Box(
            modifier = Modifier
                .background(color = Theme.colors.greenVariant, shape = RoundedCornerShape(12.dp))
                .size(40.dp),
            contentAlignment = Alignment.Center
        ) {
            Icon(
                painterResource(R.drawable.ic_checkmark_badge),
                contentDescription = "checkmark badge icon",
                modifier = Modifier.size(22.dp),
                tint = Theme.colors.greenAccent
            )
        }

        Text(
            text = message,
            color = Theme.colors.body,
            style = Theme.textStyle.body.medium
        )
    }
}

@Preview
@Composable
private fun SnackBarPreview() {
    TudeeTheme { TudeeSnackBar("Added task successfully") }
}