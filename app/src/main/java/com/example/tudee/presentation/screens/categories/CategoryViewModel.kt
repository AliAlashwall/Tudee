package com.example.tudee.presentation.screens.categories

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.tudee.domain.model.Category
import com.example.tudee.domain.model.Task
import com.example.tudee.domain.repository.CategoryRepository
import com.example.tudee.domain.repository.TaskRepository
import com.example.tudee.presentation.screens.home.components.TaskStatus
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CategoryViewModel @Inject constructor(
    private val taskRepository: TaskRepository,
    private val categoryRepository: CategoryRepository
) : ViewModel() {

    /*init {
        viewModelScope.launch {
            categoryDao.deleteCategory(23)
            categoryDao.deleteCategory(24)
        }
    }*/
    /*    init {
            insertInitCategories()
        }

        fun insertInitCategories() {
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
                    categoryRepository.insertCategory(it.toDomain())
                }
            }
        }*/

    val allTasks: StateFlow<List<Task>> =
        taskRepository.getAllTasks()
            .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    val allCategories: StateFlow<List<Category>> = categoryRepository.getAllCategories()
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
                task.categoryId == category.id
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
            categoryRepository.insertCategory(newCategory)
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

    // I also want to change categoryIcon to categoryId into TaskEntity
    fun onCategoryClicked(category: Category) {
        viewModelScope.launch {
            val tasksPerCategory = taskRepository.getTasksByCategoryId(category.id)
            val todoTasks = tasksPerCategory.filter { it.status == TaskStatus.TO_DO.label }
            val inProgressTasks =
                tasksPerCategory.filter { it.status == TaskStatus.IN_PROGRESS.label }
            val doneTasks = tasksPerCategory.filter { it.status == TaskStatus.DONE.label }
            _categoryUiState.update {
                it.copy(
                    tasksPerCategory = tasksPerCategory,
                    clickedCategory = category,
                    todoTasks = todoTasks,
                    inProgressTasks = inProgressTasks,
                    doneTasks = doneTasks
                )
            }
        }
    }

    fun onTabClicked(status: TaskStatus) {
        _categoryUiState.update { it.copy(selectedTab = status) }
    }
}