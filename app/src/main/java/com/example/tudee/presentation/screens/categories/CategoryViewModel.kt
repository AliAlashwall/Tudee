package com.example.tudee.presentation.screens.categories

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.tudee.database.dao.CategoryDao
import com.example.tudee.database.dao.TasksDao
import com.example.tudee.database.mapper.toCategoryEntity
import com.example.tudee.database.mapper.toDomain
import com.example.tudee.domain.model.Category
import com.example.tudee.domain.model.Task
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class CategoryViewModel(
    tasksDao: TasksDao,
    val categoryDao: CategoryDao
) : ViewModel() {

        /*init {
            viewModelScope.launch {
                categoryDao.deleteCategory(23)
                categoryDao.deleteCategory(24)
            }
        }*/

    /*fun insertInitCategories() {
        val categories = listOf(
            CategoryEntity(
                name = "Education",
                icon = R.drawable.ic_book_open,
                count = 0,
                isCustom = false
            ),
            CategoryEntity(
                name = "Shopping",
                icon = R.drawable.ic_shopping_cart,
                count = 0,
                isCustom = false
            ),
            CategoryEntity(
                name = "Medical",
                icon = R.drawable.ic_hospital_location,
                count = 0,
                isCustom = false
            ),
            CategoryEntity(
                name = "Jym",
                icon = R.drawable.ic_body_part_muscle,
                count = 0,
                isCustom = false
            ),
            CategoryEntity(
                name = "Entertainment",
                icon = R.drawable.ic_baseball_bat,
                count = 0,
                isCustom = false
            ),
            CategoryEntity(
                name = "Cooking",
                icon = R.drawable.ic_chef,
                count = 0,
                isCustom = false
            ),
            CategoryEntity(
                name = "family & friend",
                icon = R.drawable.ic_user_multiple,
                count = 0,
                isCustom = false
            ),
            CategoryEntity(
                name = "traveling",
                icon = R.drawable.ic_airplane,
                count = 0,
                isCustom = false
            ),
            CategoryEntity(
                name = "agriculture",
                icon = R.drawable.ic_plant,
                count = 0,
                isCustom = false
            ),
            CategoryEntity(
                name = "coding",
                icon = R.drawable.ic_developer,
                count = 0,
                isCustom = false
            ),
            CategoryEntity(
                name = "adoration",
                icon = R.drawable.ic_quran,
                count = 0,
                isCustom = false
            ),
            CategoryEntity(
                name = "fixing bugs",
                icon = R.drawable.ic_bug,
                count = 0,
                isCustom = false
            ),
            CategoryEntity(
                name = "cleaning",
                icon = R.drawable.ic_blush_brush,
                count = 0,
                isCustom = false
            ),
            CategoryEntity(
                name = "work",
                icon = R.drawable.ic_money_bag,
                count = 0,
                isCustom = false
            ),
            CategoryEntity(
                name = "budgeting",
                icon = R.drawable.ic_money_bag,
                count = 0,
                isCustom = false
            ),
            CategoryEntity(
                name = "self - care",
                icon = R.drawable.ic_in_love,
                count = 0,
                isCustom = false
            ),
            CategoryEntity(
                name = "event",
                icon = R.drawable.ic_birthday_cake,
                count = 0,
                isCustom = false
            ),
            CategoryEntity(
                name = "Reading novels",
                icon = R.drawable.reading_novels,
                count = 0,
                isCustom = false
            ),
        )
        categories.forEach {
            viewModelScope.launch {
                categoryDao.insertCategory(it)
            }
        }
    }*/

    val allTasks: StateFlow<List<Task>> =
        tasksDao.getAllTasks().map { tasksEntities -> tasksEntities.map { it.toDomain() } }
            .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    val allCategories: StateFlow<List<Category>> = categoryDao.getAllCategories().map { categoriesEntities ->
        categoriesEntities.map { it.toDomain() }
    }
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    private val _categoryUiState =
        MutableStateFlow(CategoryUiState(categories = allCategories.value))

    // Every time one of the combine's flows changed its block would be executed
    val categoriesWithCount: StateFlow<List<Category>> = combine(
        flow = allCategories,
        flow2 = allTasks
    ) { categories, tasks ->
        categories.map { category ->
            val taskCountPerCategory = tasks.count { task ->
                if (category.isCustom) {
                    task.categoryIcon == category.uriImage
                } else {
                    task.categoryIcon == category.icon.toString()
                }
            }
            category.copy(count = taskCountPerCategory)
        }
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())


    val categoryUiState: StateFlow<CategoryUiState> = combine(
        _categoryUiState,
        categoriesWithCount
    ) { state, categoriesCounted ->

        state.copy(categories = categoriesCounted)
    }.stateIn(
        viewModelScope,
        SharingStarted.WhileSubscribed(5000),
        CategoryUiState()
    )


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

    fun onAddCategoryClicked(name: String, imageUri: String) {
        _categoryUiState.update {
            it.copy(
                categoryTitle = name,
                selectedCategoryImage = imageUri
            )
        }
        viewModelScope.launch {
            val newCategory = Category(
                name = _categoryUiState.value.categoryTitle,
                uriImage = _categoryUiState.value.selectedCategoryImage ?: "",
                count = 0,
                isCustom = true   // it is always equal true, as every entered image would be URI
            )
            categoryDao.insertCategory(newCategory.toCategoryEntity())
        }
        onDismissBottomSheet()
    }

    fun onDismissBottomSheet() {
        controlBottomSheetVisibility(false)
        resetBottomSheetState()
    }

    fun updateCategoryImage(imageUri: String?) {
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


class TaskViewModelFactoryForCategories(
    private val tasksDao: TasksDao,
    private val categoryDao: CategoryDao
) : ViewModelProvider.Factory {

    @RequiresApi(Build.VERSION_CODES.O)
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T =
        CategoryViewModel(tasksDao, categoryDao) as T
}