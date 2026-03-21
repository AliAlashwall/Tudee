package com.example.tudee.presentation.unit

import android.os.Build
import androidx.annotation.RequiresApi
import java.time.LocalDate
import java.time.format.DateTimeFormatter

// Extension function for formatting
@RequiresApi(Build.VERSION_CODES.O)
fun LocalDate.toDMYFormat(): String {
    val formatter = DateTimeFormatter.ofPattern("dd MMM yyyy")
    return this.format(formatter)
}