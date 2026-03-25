package com.example.tudee

import android.annotation.SuppressLint
import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.tudee.database.AppDatabase
import com.example.tudee.navigation.AppNavHost
import com.example.tudee.navigation.Screens
import com.example.tudee.presentation.TudeeViewModel
import com.example.tudee.presentation.components.BottomNavBar
import com.example.tudee.presentation.designSystem.theme.Theme
import com.example.tudee.presentation.designSystem.theme.TudeeTheme
import com.example.tudee.presentation.screens.categories.CategoryViewModel
import com.example.tudee.presentation.screens.categories.TaskViewModelFactoryForCategories
import com.example.tudee.presentation.screens.home.HomeViewModel
import com.example.tudee.presentation.screens.home.TaskViewModelFactoryForHome
import com.example.tudee.presentation.screens.tasks.TaskViewModelFactoryForTask
import com.example.tudee.presentation.screens.tasks.TasksViewModel

//https://www.figma.com/design/Kc0YU5ycMGzo48f0suelUc/Tudee?node-id=4-138&p=f&t=JzUjibAXo4u2ypgb-0
class MainActivity : ComponentActivity() {
    @RequiresApi(Build.VERSION_CODES.O)
    @SuppressLint("ViewModelConstructorInComposable")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            val navController = rememberNavController()
            val navBackStackEntry by navController.currentBackStackEntryAsState()
            TudeeTheme {
                Scaffold(
                    modifier = Modifier.fillMaxSize(),
                    bottomBar = {
                        if ((navBackStackEntry?.destination?.route) in listOf(
                                Screens.Home.route,
                                Screens.Tasks.route,
                                Screens.Categories.route,
                            )
                        ) {
                            BottomNavBar(navController = navController)
                        }
                    },
                    containerColor = Color.Transparent,
                    contentColor = Theme.colors.overlay,
                ) { innerPadding ->
                    val tasksDao = AppDatabase.getInstance(applicationContext).tasksDao()
                    val categoryDao = AppDatabase.getInstance(applicationContext).categoryDao()

                    val homeViewModel: HomeViewModel by viewModels {
                        TaskViewModelFactoryForHome(
                            tasksDao,
                            categoryDao
                        )
                    }
                    val tasksViewModel: TasksViewModel by viewModels {
                        TaskViewModelFactoryForTask(
                            tasksDao
                        )
                    }
                    val categoryViewModel: CategoryViewModel by viewModels {
                        TaskViewModelFactoryForCategories(tasksDao, categoryDao)
                    }
                    AppNavHost(
                        navController = navController,
                        modifier = Modifier.padding(innerPadding),
                        tudeeViewModel = TudeeViewModel(context = LocalContext.current),
                        homeViewModel = homeViewModel,
                        tasksViewModel = tasksViewModel,
                        categoryViewModel = categoryViewModel

                    )
                }
            }
        }

    }
}
