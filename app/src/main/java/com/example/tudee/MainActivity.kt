package com.example.tudee

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.tudee.navigation.AppNavHost
import com.example.tudee.navigation.Screens
import com.example.tudee.presentation.TudeeViewModel
import com.example.tudee.presentation.components.BottomNavBar
import com.example.tudee.presentation.design.theme.Theme
import com.example.tudee.presentation.design.theme.TudeeTheme

//https://www.figma.com/design/Kc0YU5ycMGzo48f0suelUc/Tudee?node-id=4-138&p=f&t=JzUjibAXo4u2ypgb-0
class MainActivity : ComponentActivity() {
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
                                Screens.Document.route,
                                Screens.Menu.route,
                            )
                        ) {
                            BottomNavBar(navController = navController)
                        }
                    },
                    containerColor = Color.Transparent,
                    contentColor = Theme.colors.overlay,
                ) { innerPadding ->
                    AppNavHost(
                        navController = navController,
                        modifier = Modifier.padding(innerPadding),
                        tudeeViewModel = TudeeViewModel(context = LocalContext.current)
                    )

                }
            }
        }
    }
}
