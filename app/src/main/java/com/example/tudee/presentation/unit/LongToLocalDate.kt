package com.example.tudee.presentation.unit

import android.os.Build
import androidx.annotation.RequiresApi
import java.time.Instant
import java.time.LocalDate
import java.time.ZoneId

@RequiresApi(Build.VERSION_CODES.O)
fun Long.toLocalDate(): LocalDate? {
    // Convert the Long (milliseconds since epoch) to LocalDate
    val instant = Instant.ofEpochMilli(this)
    val date = instant.atZone(ZoneId.systemDefault()).toLocalDate()
    return date  // e.g., "2026-03-12"

}
