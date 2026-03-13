package com.example.tudee.presentation.screens.home

import com.example.tudee.R

data class HomeUiState(
    var newTaskTitle: String = "",
    var newDescription: String = "",
    var showBottomSheet: Boolean = false,
    var selectedDate: String = "22-6-2025",
    var currentDate: String = "22-6-2025",
    var currentPriority: String = "",
)

val categoryList = listOf(
    listOf("Education", R.drawable.ic_book_open),
    listOf("Shopping", R.drawable.ic_shopping_cart),
    listOf("Medical", R.drawable.ic_hospital_location),
    listOf("Jym", R.drawable.ic_body_part_muscle),
    listOf("Entertainment", R.drawable.ic_baseball_bat),
    listOf("Cooking", R.drawable.ic_chef),
    listOf("family & friend", R.drawable.ic_user_multiple),
    listOf("traveling", R.drawable.ic_airplane),
    listOf("agriculture", R.drawable.ic_plant),
    listOf("coding", R.drawable.ic_developer),
    listOf("adoration", R.drawable.ic_quran),
    listOf("fixing bugs", R.drawable.ic_bug),
    listOf("cleaning", R.drawable.ic_blush_brush),
    listOf("work", R.drawable.ic_money_bag),
    listOf("budgeting", R.drawable.ic_money_bag),
    listOf("self - care", R.drawable.ic_in_love),
    listOf("event", R.drawable.ic_birthday_cake),
    listOf("Reading novels", R.drawable.ic_book_open),
)
