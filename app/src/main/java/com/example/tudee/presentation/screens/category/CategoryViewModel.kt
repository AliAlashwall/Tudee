package com.example.tudee.presentation.screens.category

import android.net.Uri
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.update

class CategoryViewModel : ViewModel() {
    val categoryUiState = MutableStateFlow(CategoryUiState())

    fun onFABClicked() {
        controlBottomSheetVisibility(true)
    }


    fun enableAddTaskButton(): Boolean {
        return categoryUiState.value.categoryTitle.isNotBlank() &&
                categoryUiState.value.selectedCategoryImage.let { it != null }
    }

    fun onCategoryTitleChange(categoryTitle: String) {
        categoryUiState.update {
            it.copy(categoryTitle = categoryTitle)
        }
    }

    fun onDismissBottomSheet() {
        controlBottomSheetVisibility(false)
        resetBottomSheetState()
    }

    fun updateCategoryImage(imageUri: Uri?) {
        categoryUiState.update {
            it.copy(selectedCategoryImage = imageUri)
        }
    }

    fun controlBottomSheetVisibility(visibility: Boolean) {
        categoryUiState.update {
            it.copy(showBottomSheet = visibility)
        }
    }

    private fun resetBottomSheetState() {
        categoryUiState.update {
            it.copy(categoryTitle = "", selectedCategoryImage = null)
        }
    }
}