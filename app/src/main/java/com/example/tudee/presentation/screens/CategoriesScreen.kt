package com.example.tudee.presentation.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.tudee.R
import com.example.tudee.presentation.components.CategoryCard
import com.example.tudee.presentation.designSystem.theme.Theme
import com.example.tudee.presentation.designSystem.theme.TudeeTheme
import com.example.tudee.presentation.screens.home.categoryList

@Composable
fun CategoriesScreen(modifier: Modifier = Modifier, navController: NavController) {
    Column(
        modifier = Modifier
            .background(Theme.colors.surface)
            .fillMaxSize()
            .padding(vertical = 20.dp, horizontal = 16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(20.dp)

    ) {
        Text(
            text = stringResource(R.string.categories),
            modifier = modifier.fillMaxWidth(),
            color = Theme.colors.title,
            style = Theme.textStyle.title.large,
            textAlign = TextAlign.Start

        )

        LazyVerticalGrid(columns = GridCells.Fixed(3),
            verticalArrangement = Arrangement.spacedBy(24.dp),
            horizontalArrangement = Arrangement.SpaceBetween) {
            items(categoryList) { category ->
                CategoryCard(
                    icon = painterResource(category[1] as Int),
                    label = category[0] as String,
                    onClickCategory = { TODO() },
                    iconTint = Color.Unspecified,
                    count = 0 //TODO
                )
            }
        }
    }
}

@Preview
@Composable
private fun CategoriesScreenPreview() {
    val navController = rememberNavController()
    TudeeTheme {
        CategoriesScreen(navController = navController)
    }
}