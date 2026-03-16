package com.example.tudee.presentation.screens.category

import android.net.Uri

data class CategoryUiState(
    var showBottomSheet: Boolean = false,
    var categoryTitle: String = "",
    var selectedCategoryImage: Uri? = null,
)
