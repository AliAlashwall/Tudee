package com.example.tudee.presentation.screens.home

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.update
import java.time.LocalDate
import java.time.format.DateTimeFormatter

@RequiresApi(Build.VERSION_CODES.O)
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

    fun updateCurrentPriority(newPriority: Int) {
        homeUiState.update {
            it.copy(currentPriority = newPriority)
        }
    }


    fun getCurrentDate() {
        val currDate = LocalDate.now().format(DateTimeFormatter.ofPattern("dd MMM yyyy"))
        homeUiState.update {
            it.copy(currentDate = currDate, selectedDate = currDate)
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

    fun enableAddTaskButton(): Boolean {
        return homeUiState.value.newTaskTitle.isNotBlank() &&
                homeUiState.value.selectedCategory.let { it != null } &&
                homeUiState.value.currentPriority.let { it != null }
    }

    fun updateSelectedCategory(categoryIndex: Int?) {
        if (categoryIndex != null) {
            homeUiState.update {
                it.copy(selectedCategory = categoryIndex)
            }
        }
    }

    fun onDismissBottomSheet() {
        controlBottomSheetVisibility(false)
        resetBottomSheetState()
    }

    private fun resetBottomSheetState() {
        homeUiState.update {
            it.copy(
                newTaskTitle = "",
                newDescription = "",
                showBottomSheet = false,
                selectedDate = it.currentDate,
                currentPriority = null,
                selectedCategory = null
            )
        }
    }

    // Helper to control the bottom sheet visibility
    fun controlBottomSheetVisibility(visibility: Boolean) {
        homeUiState.update {
            it.copy(showBottomSheet = visibility)
        }
    }

}