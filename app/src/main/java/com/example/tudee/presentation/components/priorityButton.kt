package com.example.tudee.presentation.components

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
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.tudee.R
import com.example.tudee.presentation.designSystem.theme.Theme
import com.example.tudee.presentation.screens.home.prioritiesList

@Composable
fun PriorityButton(
    modifier: Modifier = Modifier,
    priorityLevel: Int,
    selected: Boolean ,
    onClicked: () -> Unit,
) {
    val textContent = when (priorityLevel) {
        0 -> stringResource(prioritiesList[0].name)
        1 -> stringResource(prioritiesList[1].name)
        2 -> stringResource(prioritiesList[2].name)
        else -> stringResource(prioritiesList[0].name)
    }

    val startIcon = when (priorityLevel) {
        0 -> prioritiesList[0].icon
        1 -> prioritiesList[1].icon
        2 -> prioritiesList[2].icon
        else -> prioritiesList[0].icon
    }

    val textColor = if (selected) Theme.colors.onPrimary else Theme.colors.hint

    val buttonColor = if (selected) {
        when (priorityLevel) {
            0 -> Theme.colors.error
            1-> Theme.colors.yellowAccent
            2->Theme.colors.greenAccent
            else -> Theme.colors.surfaceLow
        }
    } else Theme.colors.surfaceLow


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
            contentDescription = stringResource(R.string.priority_high),
            tint = textColor,
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
        priorityLevel = 0,
        selected = true,
        onClicked = {  }
    )
}