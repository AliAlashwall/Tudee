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
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.example.tudee.R
import com.example.tudee.presentation.components.TudeeTextButton
import com.example.tudee.presentation.designSystem.theme.Theme


@Composable
fun BottomSheetButtons(
    modifier: Modifier = Modifier,
    enableAddTaskButton: Boolean,
    onAddClicked: () -> Unit,
    onCancelBottomSheetClicked: (Boolean) -> Unit
) {
    Column(
        modifier = modifier
            .background(Theme.colors.surfaceHigh)
            .padding(vertical = 12.dp, horizontal = 16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        TudeeTextButton(
            text = stringResource(R.string.add_bottom_sheet),
            onClick = { onAddClicked() },
            modifier = Modifier
                .height(56.dp)
                .background(
                    color = if (enableAddTaskButton) Theme.colors.primary else Theme.colors.disable,
                    shape = RoundedCornerShape(50.dp)
                )
                .fillMaxWidth(),
            style = Theme.textStyle.label.large,
            colors = if (enableAddTaskButton) Theme.colors.onPrimary else Theme.colors.stroke
        )

        TudeeTextButton(
            text = stringResource(R.string.cancel_bottom_sheet),
            onClick = { onCancelBottomSheetClicked(false) },
            modifier = Modifier
                .height(56.dp)
                .border(
                    width = 1.dp,
                    color = Theme.colors.stroke,
                    shape = RoundedCornerShape(50.dp)
                )
                .fillMaxWidth(),
            style = Theme.textStyle.label.large,
            colors = Theme.colors.primary
        )
    }
}