package com.example.tudee.navigation

import android.annotation.SuppressLint
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.tudee.presentation.TudeeViewModel
import com.example.tudee.presentation.screens.categories.CategoriesScreen
import com.example.tudee.presentation.screens.categories.CategoryTasksScreen
import com.example.tudee.presentation.screens.categories.CategoryViewModel
import com.example.tudee.presentation.screens.home.HomeScreen
import com.example.tudee.presentation.screens.home.HomeViewModel
import com.example.tudee.presentation.screens.onBoarding.OnBoardingScreen
import com.example.tudee.presentation.screens.tasks.TasksScreen
import com.example.tudee.presentation.screens.tasks.TasksViewModel


@RequiresApi(Build.VERSION_CODES.O)
@SuppressLint("ViewModelConstructorInComposable")
@Composable
fun AppNavHost(
    navController: NavHostController,
    modifier: Modifier,
) {
    val tudeeViewModel = hiltViewModel<TudeeViewModel>()
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
            HomeScreen
        } else {
            OnBoardingScreen
        },
        modifier = modifier
    ) {
        composable<OnBoardingScreen> {
            OnBoardingScreen(onCompleteScroll = { tudeeViewModel.completeOnboarding() })
        }

        composable<HomeScreen> {
            val homeViewModel = hiltViewModel<HomeViewModel>()
            HomeScreen(homeViewModel = homeViewModel)
        }

        composable<TasksScreen> {
            val tasksViewModel = hiltViewModel<TasksViewModel>()
            TasksScreen(
                tasksViewModel = tasksViewModel
            )
        }

        composable<CategoriesScreen> {
            val categoryViewModel = hiltViewModel<CategoryViewModel>()
            CategoriesScreen(
                navController = navController,
                categoryViewModel = categoryViewModel
            )
        }

        composable<CategoryTasksScreen> {
            val categoryViewModel = hiltViewModel<CategoryViewModel>()
            CategoryTasksScreen(
                categoryViewModel = categoryViewModel,
                navController = navController
            )
        }
    }
}