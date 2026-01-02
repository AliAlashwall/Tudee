package com.example.tudee.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.tudee.presentation.screens.HomeScreen
import com.example.tudee.presentation.screens.MenuScreen
import com.example.tudee.presentation.screens.ProfileScreen

sealed class Screens(val route: String) {
    object Home : Screens("home")
    object Document : Screens("Document")
    object Menu : Screens("Menu") {
    }
}

@Composable
fun AppNavHost(navController: NavHostController) {

    NavHost(navController = navController, startDestination = Screens.Home.route) {

        composable(route = Screens.Home.route) {
            HomeScreen(navController = navController)
        }

        composable(Screens.Document.route) {
            MenuScreen(navController = navController)
        }

        composable(
            route = Screens.Menu.route
        ) {
            ProfileScreen(navController = navController)
        }

    }
}