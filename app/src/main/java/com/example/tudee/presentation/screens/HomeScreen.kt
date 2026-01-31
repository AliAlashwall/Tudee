package com.example.tudee.presentation.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.tudee.presentation.components.HomeTopBar
import com.example.tudee.presentation.components.OverviewCard
import com.example.tudee.presentation.design.theme.Theme
import com.example.tudee.presentation.design.theme.TudeeTheme


@Composable
fun HomeScreen(modifier: Modifier = Modifier, navController: NavController) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .background(Theme.colors.surface),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Box() {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(167.dp)
                    .background(Theme.colors.primary)
            )
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp)
            ) {
                HomeTopBar(modifier = Modifier.padding(vertical = 12.dp, horizontal = 16.dp))

                OverviewCard()
            }
        }

    }
}

@Preview
@Composable
private fun HomeScreenPreview() {
    TudeeTheme {
        val navController = rememberNavController()
        HomeScreen(navController = navController)
    }
}