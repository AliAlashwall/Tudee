package com.example.tudee.presentation.screens.tasks

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.material3.PrimaryTabRow
import androidx.compose.material3.Tab
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.tudee.presentation.designSystem.theme.Theme

@Composable
fun TaskTabs(
    selectedTabIndex: Int = 0,
    onTabClicked:(Int) -> Unit
) {
    val tabs = remember { listOf("In progress" to 14, "To Do" to null, "Done" to null) }

    PrimaryTabRow(selectedTabIndex = selectedTabIndex) {

        tabs.forEachIndexed { index, (title, count) ->
            val textStyle =
                if (selectedTabIndex == index) Theme.textStyle.label.medium else Theme.textStyle.label.small
            Tab(
                selected = selectedTabIndex == index,
                onClick = {  onTabClicked(index) },
                text = {
                    Row(horizontalArrangement = Arrangement.spacedBy(6.dp)) {
                        Text(
                            text = title,
                            color = if (selectedTabIndex == index) Theme.colors.title else Theme.colors.hint,
                            style = textStyle
                        )
                        if (count != null) {
                            Text(
                                text = count.toString(),
                                color = if (selectedTabIndex == index) Theme.colors.body else Theme.colors.hint,
                                style = textStyle

                            )
                        }
                    }
                }
            )
        }
    }
}


@Preview
@Composable
private fun TaskTabsPreview() {
    TaskTabs(
        selectedTabIndex = 0,
        onTabClicked = {}
    )
}