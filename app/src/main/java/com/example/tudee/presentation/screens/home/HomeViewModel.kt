package com.example.tudee.presentation.screens.home

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.update

class HomeViewModel : ViewModel() {

    val homeUiState = MutableStateFlow(HomeUiState())

    fun onNewTaskTitleChange(newTitle: String) {
        homeUiState.update {
            it.copy(newTaskTitle = newTitle)
        }
    }

    fun onNewTaskDescriptionChange(newDescription: String) {
        homeUiState.update {
            it.copy(newDescription = newDescription)
        }
    }

    fun updateCurrentPriority(newPriority: String) {
        homeUiState.update {
            it.copy(currentPriority = newPriority)
        }
    }

    fun onFABClicked() {
     controlBottomSheetVisibility(true)
    }

    // Update the selectedDate field (used by the BottomSheet) instead of currentDate.
    fun updateSelectedDate(selectedDate: String) {
        homeUiState.update {
            it.copy(selectedDate = selectedDate)
        }
    }

    // Helper to control the bottom sheet visibility
    fun controlBottomSheetVisibility(visibility: Boolean) {
        homeUiState.update {
            it.copy(showBottomSheet = visibility)
        }
    }

}