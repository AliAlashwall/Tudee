package com.example.tudee.presentation.screens.category

import android.Manifest
import android.annotation.SuppressLint
import android.net.Uri
import android.os.Build
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.PathEffect
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import coil.compose.AsyncImage
import com.example.tudee.R
import com.example.tudee.presentation.components.CategoryCard
import com.example.tudee.presentation.components.TudeeTextField
import com.example.tudee.presentation.components.bottomSheet.BottomSheetButtons
import com.example.tudee.presentation.components.bottomSheet.TudeeBottomSheet
import com.example.tudee.presentation.designSystem.theme.Theme
import com.example.tudee.presentation.designSystem.theme.TudeeTheme
import com.example.tudee.presentation.screens.category.TudeeCategories.categoriesList

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun CategoriesScreen(
    modifier: Modifier = Modifier, navController: NavController,
    categoryViewModel: CategoryViewModel
) {
    val categoryUiState = categoryViewModel.categoryUiState.collectAsStateWithLifecycle().value
    Scaffold(
        containerColor = Theme.colors.surface,
        floatingActionButton = {
            FloatingActionButton(
                onClick = { categoryViewModel.onFABClicked() },
                containerColor = Theme.colors.primary,
                shape = RoundedCornerShape(50.dp),
                modifier = Modifier.size(64.dp)
            ) {
                Icon(
                    painterResource(R.drawable.ic_add),
                    contentDescription = "Add Task",
                    tint = Theme.colors.onPrimary,
                    modifier = Modifier.size(28.dp)
                )
            }
        },
        modifier = modifier.fillMaxSize(),
    ) {

        CategoryScreenContent()

        if (categoryUiState.showBottomSheet) {
            TudeeBottomSheet(
                expanded = false,
                onDismissRequest = { categoryViewModel.onDismissBottomSheet() },
                content = {
                    BottomSheetContent(
                        categoryTitle = categoryUiState.categoryTitle,
                        onCategoryTitleChanges = { categoryViewModel.onCategoryTitleChange(it) },
                        enableAddTaskButton = categoryViewModel.enableAddTaskButton(),
                        onCancelBottomSheetClicked = { categoryViewModel.onDismissBottomSheet() },
                        onAddClicked = {/*TODO*/ },
                        updateSelectedCategoryImage = { categoryViewModel.updateCategoryImage(it) },
                        selectedImage = categoryUiState.selectedCategoryImage
                    )
                }
            )
        }

    }
}

@Composable
fun CategoryScreenContent(modifier: Modifier = Modifier) {
    Column(
        modifier = modifier.fillMaxSize().padding(horizontal = 16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(20.dp)
    ) {
        Text(
            text = stringResource(R.string.categories),
            modifier = Modifier.fillMaxWidth(),
            color = Theme.colors.title,
            style = Theme.textStyle.title.large,
            textAlign = TextAlign.Start

        )
        // remember the static categories list so it's not re-evaluated on every recomposition
        val categories = remember { categoriesList }

        LazyVerticalGrid(
            modifier = Modifier.fillMaxWidth(),
            columns = GridCells.Fixed(3),
            verticalArrangement = Arrangement.spacedBy(24.dp),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            items(categories, key = { it.name }) { category ->
                CategoryCard(
                    icon = painterResource(category.icon),
                    label = category.name,
                    onClickCategory = { /*TODO*/ },
                    iconTint = Color.Unspecified,
                    count = 0 //TODO
                )
            }
        }
    }
}


@Composable
fun BottomSheetContent(
    modifier: Modifier = Modifier,
    categoryTitle: String,
    onCategoryTitleChanges: (String) -> Unit,
    enableAddTaskButton: Boolean,
    onCancelBottomSheetClicked: (Boolean) -> Unit,
    onAddClicked: () -> Unit,
    updateSelectedCategoryImage: (Uri?) -> Unit,
    selectedImage: Uri?
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(horizontal = 16.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        Text(
            text = stringResource(R.string.add_new_category),
            style = Theme.textStyle.title.large,
            color = Theme.colors.title,
        )

        TudeeTextField(
            value = categoryTitle,
            onValueChange = { onCategoryTitleChanges(it) },
            startIcon = painterResource(R.drawable.ic_menu_circle_outlined),
            hint = stringResource(R.string.category_title),
            keyboardOptions = KeyboardOptions.Default,
            singleLine = true,
            modifier = Modifier
                .fillMaxWidth()
                .height(56.dp)
        )

        Text(
            text = stringResource(R.string.category_image),
            style = Theme.textStyle.title.medium,
            color = Theme.colors.title,
        )

        val requiredPermission = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            Manifest.permission.READ_MEDIA_IMAGES
        } else {
            Manifest.permission.READ_EXTERNAL_STORAGE
        }

        val imagePickerLauncher = rememberLauncherForActivityResult(
            contract = ActivityResultContracts.GetContent()
        ) { uri: Uri? ->
            updateSelectedCategoryImage(uri)
        }

        val permissionLauncher = rememberLauncherForActivityResult(
            contract = ActivityResultContracts.RequestPermission()
        ) { isGranted ->
            if (isGranted) {
                // Open picker once permission is granted
                imagePickerLauncher.launch("image/*")
            }
        }

        Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier
                .clickable { permissionLauncher.launch(requiredPermission) }
                .size(width = 112.dp, height = 113.dp)
        ) {
            Canvas(
                modifier = Modifier.fillMaxSize()
            ) {
                drawRoundRect(
                    color = Color.LightGray,
                    cornerRadius = CornerRadius(16.dp.toPx()),
                    style = Stroke(
                        width = 2f,
                        pathEffect = PathEffect.dashPathEffect(
                            intervals = floatArrayOf(20f, 20f),
                            phase = 0f
                        )
                    )
                )
            }

            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Icon(
                    painterResource(R.drawable.ic_image_add),
                    contentDescription = stringResource(R.string.upload),
                    tint = Theme.colors.hint,
                    modifier = Modifier.size(24.dp)
                )
                Text(
                    text = stringResource(R.string.upload),
                    style = Theme.textStyle.label.medium,
                    color = Theme.colors.hint
                )
            }

            if (selectedImage != null) {
                AsyncImage(
                    model = selectedImage,
                    contentDescription = "Selected image",
                    modifier = Modifier
                        .fillMaxSize()
                        .clip(RoundedCornerShape(12.dp)),
                    contentScale = ContentScale.Crop
                )

                Box(
                    modifier = Modifier
//                        .align(Alignment.TopEnd)
                        .size(32.dp)
                        .background(
                            Theme.colors.surfaceHigh,
                            shape = RoundedCornerShape(12.dp)
                        ),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        painterResource(R.drawable.ic_pencil_edit_filled),
                        contentDescription = stringResource(R.string.edit_category_icon),
                        tint = Theme.colors.secondary,
                        modifier = Modifier.size(20.dp)
                    )
                }
            }
        }
        BottomSheetButtons(
            enableAddTaskButton = enableAddTaskButton,
            onAddClicked = { onAddClicked() },
            onCancelBottomSheetClicked = { onCancelBottomSheetClicked(false) }
        )
    }
}

@SuppressLint("ViewModelConstructorInComposable")
@Preview(showBackground = true)
@Composable
private fun CategoriesScreenPreview() {
    val navController = rememberNavController()
    val categoryViewModel = CategoryViewModel()
    TudeeTheme {
        CategoriesScreen(navController = navController, categoryViewModel = categoryViewModel)
    }
}