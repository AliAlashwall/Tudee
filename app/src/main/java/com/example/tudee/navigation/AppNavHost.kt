package com.example.tudee.navigation

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.tudee.presentation.TudeeViewModel
import com.example.tudee.presentation.onBoarding.OnBoardingScreen
import com.example.tudee.presentation.screens.HomeScreen
import com.example.tudee.presentation.screens.MenuScreen
import com.example.tudee.presentation.screens.ProfileScreen

sealed class Screens(val route: String) {
    object OnBoarding : Screens("onBoarding")
    object Home : Screens("home")
    object Document : Screens("Document")
    object Menu : Screens("Menu") {
    }
}

@Composable
fun AppNavHost(
    navController: NavHostController,
    modifier: Modifier,
    tudeeViewModel: TudeeViewModel
) {

    val isOnboardingCompleted by tudeeViewModel.isOnboardingCompleted.collectAsState()

    if (isOnboardingCompleted == null) {
        // still loading the preference — avoid creating NavHost with wrong startDestination
        Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
            CircularProgressIndicator()
        }
        return
    }

    NavHost(
        navController = navController,
        startDestination = if (isOnboardingCompleted == true) {
            Screens.Home.route
        } else {
            Screens.OnBoarding.route
        },
        modifier = modifier
    ) {
        composable(route = Screens.OnBoarding.route) {
            OnBoardingScreen(onCompleteScroll = { tudeeViewModel.completeOnboarding() })
        }

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