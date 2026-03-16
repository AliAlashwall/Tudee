package com.example.tudee.presentation.screens.home

data class HomeUiState(
    var newTaskTitle: String = "",
    var newDescription: String = "",
    var showBottomSheet: Boolean = false,
    var selectedDate: String = "22-6-2025",
    var currentDate: String = "22-6-2025",
    var currentPriority: Int? = null,
    var selectedCategory: Int? = null,

)


