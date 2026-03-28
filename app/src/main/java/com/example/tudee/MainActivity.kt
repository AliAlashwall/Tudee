package com.example.tudee

import android.annotation.SuppressLint
import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.annotation.RequiresApi
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavDestination.Companion.hasRoute
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.tudee.navigation.AppNavHost
import com.example.tudee.navigation.CategoriesScreen
import com.example.tudee.navigation.HomeScreen
import com.example.tudee.navigation.TasksScreen
import com.example.tudee.presentation.TudeeViewModel
import com.example.tudee.presentation.components.BottomNavBar
import com.example.tudee.presentation.designSystem.theme.Theme
import com.example.tudee.presentation.designSystem.theme.ThemeState
import com.example.tudee.presentation.designSystem.theme.TudeeTheme
import com.example.tudee.presentation.screens.categories.CategoryViewModel
import com.example.tudee.presentation.screens.home.HomeViewModel
import com.example.tudee.presentation.screens.tasks.TasksViewModel
import dagger.hilt.android.AndroidEntryPoint

//https://www.figma.com/design/Kc0YU5ycMGzo48f0suelUc/Tudee?node-id=4-138&p=f&t=JzUjibAXo4u2ypgb-0

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    @RequiresApi(Build.VERSION_CODES.O)
    @SuppressLint("ViewModelConstructorInComposable", "RestrictedApi")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            val navController = rememberNavController()
            val navBackStackEntry by navController.currentBackStackEntryAsState()

            val bottomBarScreens =
                listOf(HomeScreen::class, TasksScreen::class, CategoriesScreen::class)

            val showBottomBar = bottomBarScreens.any { screen ->
                navBackStackEntry?.destination?.hasRoute(screen) == true
            }

            val isDark = isSystemInDarkTheme()
            val (isDarkThemeState, onThemeStateChanged) = remember { mutableStateOf(isDark) }
            val themeState = remember(isDarkThemeState) {
                ThemeState(
                    isDark = isDarkThemeState,
                    onThemeChanged = onThemeStateChanged
                )
            }
            TudeeTheme(themeState) {
                Scaffold(
                    modifier = Modifier.fillMaxSize(),
                    bottomBar = {
                        if (showBottomBar) {
                            BottomNavBar(navController = navController)
                        }
                    },
                    containerColor = Color.Transparent,
                    contentColor = Theme.colors.overlay,
                ) { innerPadding ->


                    val homeViewModel: HomeViewModel = hiltViewModel()
                    val tasksViewModel: TasksViewModel = hiltViewModel()
                    val categoryViewModel: CategoryViewModel = hiltViewModel()
                    val tudeeViewModel: TudeeViewModel = hiltViewModel()


                    AppNavHost(
                        navController = navController,
                        modifier = Modifier.padding(innerPadding),
                        tudeeViewModel = tudeeViewModel,
                        homeViewModel = homeViewModel,
                        tasksViewModel = tasksViewModel,
                        categoryViewModel = categoryViewModel
                    )
                }
            }
        }
    }
}
