package com.example.tudee.presentation.components

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.tooling.preview.Preview
import com.example.tudee.presentation.unit.toDMYFormat
import com.example.tudee.presentation.unit.toLocalDate

@RequiresApi(Build.VERSION_CODES.O)
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DateRangePicker(
    onDismissRequest: () -> Unit,
    onConfirm: (String) -> Unit
) {
    val datePickerState = rememberDatePickerState()
    var selectedDate by remember { mutableStateOf("No date selected") }

    DatePickerDialog(
        onDismissRequest = { onDismissRequest() },
        confirmButton = {
            TextButton(
                onClick = {
                    val millis = datePickerState.selectedDateMillis
                    selectedDate = millis?.toLocalDate()?.toDMYFormat().toString()
                    onConfirm(selectedDate)
                    onDismissRequest()
                }) { Text("OK") }
        },
        dismissButton = {
            TextButton(onClick = { onDismissRequest() }) { Text("Cancel") }
        }
    ) {
        DatePicker(state = datePickerState)
    }
}


@RequiresApi(Build.VERSION_CODES.O)
@Preview
@Composable
private fun DateRangePickerPreview() {
    DateRangePicker({}, {})
}