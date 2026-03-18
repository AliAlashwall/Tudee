package com.example.tudee.presentation.screens.home

import com.example.tudee.R


data class Priority(
    val name: Int, val icon: Int,
)

val prioritiesList = listOf(
    Priority(
        R.string.priority_high,
        R.drawable.ic_flag,
    ),
    Priority(
        R.string.priority_medium,
        R.drawable.ic_alert,
    ),
    Priority(
        R.string.priority_low,
        R.drawable.ic_trade_down,
    )
)