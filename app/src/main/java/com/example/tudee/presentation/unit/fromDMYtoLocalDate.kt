package com.example.tudee.presentation.unit

import android.os.Build
import androidx.annotation.RequiresApi
import java.time.LocalDate
import java.time.format.DateTimeFormatter

@RequiresApi(Build.VERSION_CODES.O)
fun String.fromDMYtoLocalDate(): LocalDate {
    val formatter = DateTimeFormatter.ofPattern("dd MMM yyyy")
    return LocalDate.parse(this, formatter)
}