package com.example.tudee.presentation.screens.categories

import android.net.Uri
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.tudee.database.dao.TasksDao
import com.example.tudee.database.entity.TasksEntity
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import com.example.tudee.presentation.screens.categories.TudeeCategories.categoriesList

class CategoryViewModel(val tasksDao: TasksDao) : ViewModel() {
    private val _categoryUiState = MutableStateFlow(CategoryUiState())
    
    val allTasks: StateFlow<List<TasksEntity>> = tasksDao.getAllTasks()
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    val categoryUiState: StateFlow<CategoryUiState> = combine(
        _categoryUiState,
        allTasks
    ) { state, tasks ->
        val counts = tasks.groupBy { it.categoryIcon }
        val updatedCategories = categoriesList.map { category ->
            category.copy(count = counts[category.icon]?.size ?: 0)
        }
        state.copy(categories = updatedCategories)
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), CategoryUiState(categories = categoriesList))


    fun onFABClicked() {
        controlBottomSheetVisibility(true)
    }


    fun enableAddTaskButton(): Boolean {
        return _categoryUiState.value.categoryTitle.isNotBlank() &&
                _categoryUiState.value.selectedCategoryImage != null
    }

    fun onCategoryTitleChange(categoryTitle: String) {
        _categoryUiState.update {
            it.copy(categoryTitle = categoryTitle)
        }
    }

    fun onDismissBottomSheet() {
        controlBottomSheetVisibility(false)
        resetBottomSheetState()
    }

    fun updateCategoryImage(imageUri: Uri?) {
        _categoryUiState.update {
            it.copy(selectedCategoryImage = imageUri)
        }
    }

    fun controlBottomSheetVisibility(visibility: Boolean) {
        _categoryUiState.update {
            it.copy(showBottomSheet = visibility)
        }
    }

    private fun resetBottomSheetState() {
        _categoryUiState.update {
            it.copy(categoryTitle = "", selectedCategoryImage = null)
        }
    }
}


class TaskViewModelFactoryForCategories(private val dao: TasksDao) : ViewModelProvider.Factory {

    @RequiresApi(Build.VERSION_CODES.O)
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T =
        CategoryViewModel(dao) as T
}