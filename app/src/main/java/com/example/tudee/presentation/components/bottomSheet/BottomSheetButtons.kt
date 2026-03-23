package com.example.tudee.presentation.components.bottomSheet

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.example.tudee.R
import com.example.tudee.presentation.components.TudeeTextButton
import com.example.tudee.presentation.designSystem.theme.Theme


@Composable
fun BottomSheetButtons(
    modifier: Modifier = Modifier,
    onPrimaryButtonClicked: () -> Unit,
    onCancelBottomSheetClicked: (Boolean) -> Unit,
    primaryButtonText: String = stringResource(R.string.add_bottom_sheet),
    secondaryButtonText: String = stringResource(R.string.cancel_bottom_sheet),
    primaryButtonColor: Color,
    onPrimaryButtonColor: Color,
    secondaryButtonBorderColor: Color = Theme.colors.stroke,
    onSecondaryButtonColor: Color
) {
    Column(
        modifier = modifier
            .background(Theme.colors.surfaceHigh)
            .padding(vertical = 12.dp, horizontal = 16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        TudeeTextButton(
            text = primaryButtonText,
            onClick = { onPrimaryButtonClicked() },
            modifier = Modifier
                .height(56.dp)
                .background(
                    color = primaryButtonColor,
                    shape = RoundedCornerShape(50.dp)
                )
                .fillMaxWidth(),
            style = Theme.textStyle.label.large,
            colors = onPrimaryButtonColor
        )

        TudeeTextButton(
            text = secondaryButtonText,
            onClick = { onCancelBottomSheetClicked(false) },
            modifier = Modifier
                .height(56.dp)
                .border(
                    width = 1.dp,
                    color = secondaryButtonBorderColor,
                    shape = RoundedCornerShape(50.dp)
                )
                .fillMaxWidth(),
            style = Theme.textStyle.label.large,
            colors = onSecondaryButtonColor
        )
    }
}