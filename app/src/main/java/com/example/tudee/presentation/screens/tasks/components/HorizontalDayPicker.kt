package com.example.tudee.presentation.screens.tasks.components

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.tudee.R
import com.example.tudee.presentation.designSystem.theme.Theme
import com.example.tudee.presentation.designSystem.theme.TudeeTheme
import com.example.tudee.presentation.unit.toDMYFormat
import java.time.LocalDate
import java.time.YearMonth
import java.time.format.TextStyle
import java.util.Locale


@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun HorizontalDayPicker(
    selectedDate: LocalDate,
    onDateSelected: (String) -> Unit,
    openDatePicker: () -> Unit
) {

    var currentMonth by remember { mutableStateOf(YearMonth.from(selectedDate)) }

    // Build list of all days in the current month
    val days: List<DayItem> = remember(currentMonth) {
        (1..currentMonth.lengthOfMonth()).map { day ->
            DayItem(currentMonth.atDay(day))
        }
    }

    LaunchedEffect(Unit) {
        onDateSelected(selectedDate.toDMYFormat())
    }
    // Auto-scroll so the selected date is roughly centred
    val listState = rememberLazyListState()
    val selectedIndex = days.indexOfFirst { it.date == selectedDate }.coerceAtLeast(0)
    LaunchedEffect(selectedDate, currentMonth) {
        val offset = maxOf(0, selectedIndex - 2)
        listState.animateScrollToItem(offset)
    }

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 12.dp)
    ) {

        // ── Month header ──────────────────────────────────────────────────
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            // Left arrow
            Box(
                modifier = Modifier
                    .size(32.dp)
                    .clip(CircleShape)
                    .border(width = 1.dp, color = Theme.colors.stroke)
                    .clickable {
                        currentMonth = currentMonth.minusMonths(1)
                        onDateSelected(currentMonth.atDay(1).toDMYFormat())
                    },
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    painter = painterResource(R.drawable.ic_arrow_head_back),
                    contentDescription = "Previous month",
                    tint = Theme.colors.body,
                    modifier = Modifier.size(20.dp)
                )
            }

            // Month + year label with dropdown caret
            Row(
                modifier = Modifier
                    .padding(horizontal = 20.dp)
                    .clickable { openDatePicker() },
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "${
                        currentMonth.month.getDisplayName(
                            TextStyle.SHORT,
                            Locale.ENGLISH
                        )
                    }, ${currentMonth.year}",
                    style = Theme.textStyle.label.medium,
                    color = Theme.colors.body
                )
                Spacer(Modifier.width(4.dp))

                Icon(
                    painter = painterResource(R.drawable.ic_down_arrow),
                    contentDescription = "Select month and year",
                    tint = Theme.colors.body,
                    modifier = Modifier.size(16.dp)
                )
            }

            // Right arrow
            Box(
                modifier = Modifier
                    .size(32.dp)
                    .clip(CircleShape)
                    .border(width = 1.dp, color = Theme.colors.stroke)
                    .clickable {
                        currentMonth = currentMonth.plusMonths(1)
                        onDateSelected(currentMonth.atDay(1).toDMYFormat())
                    },
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    painter = painterResource(R.drawable.ic_arrow_head_next),
                    contentDescription = "Next month",
                    tint = Theme.colors.body,
                    modifier = Modifier.size(20.dp)
                )
            }
        }

        Spacer(Modifier.height(8.dp))

        // ── Scrollable day strip ──────────────────────────────────────────
        LazyRow(
            state = listState,
            contentPadding = PaddingValues(start = 16.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            itemsIndexed(days) { _, item ->
                DayCell(
                    day = item,
                    isSelected = item.date == selectedDate,
                    onClick = {
                        onDateSelected(item.date.toDMYFormat())
                    }
                )
            }
        }
    }
}

// ── Individual day cell ───────────────────────────────────────────────────────
@RequiresApi(Build.VERSION_CODES.O)
@Composable
private fun DayCell(
    day: DayItem,
    isSelected: Boolean,
    onClick: () -> Unit
) {
    val bgColor = if (isSelected) Theme.colors.primary else Theme.colors.surface
    val textColor = if (isSelected) Theme.colors.onPrimary else Theme.colors.body
    val subTextColor = if (isSelected) Theme.colors.onPrimaryCaption else Theme.colors.hint

    val abbrev = day.date.dayOfWeek
        .getDisplayName(TextStyle.SHORT, Locale.ENGLISH)
        .take(3)                          // "Mon", "Tue", …

    Box(
        modifier = Modifier
            .size(56.dp, 65.dp)
            .clip(RoundedCornerShape(14.dp))
            .background(bgColor)
            .clickable(onClick = onClick)
            .padding(vertical = 12.dp),
        contentAlignment = Alignment.Center
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(2.dp)
        ) {
            Text(
                text = day.date.dayOfMonth.toString(),
                style = Theme.textStyle.title.medium,
                color = textColor
            )
            Text(
                text = abbrev,
                style = Theme.textStyle.body.small,
                color = subTextColor
            )
        }
    }
}

@RequiresApi(Build.VERSION_CODES.O)
@Preview(showBackground = true, apiLevel = 34)
@Composable
fun HorizontalDayPickerPreview() {
    TudeeTheme {
        // Use a fixed initial date for a stable preview
        HorizontalDayPicker(
            selectedDate = LocalDate.now(), openDatePicker = {}, onDateSelected = {})
    }
}