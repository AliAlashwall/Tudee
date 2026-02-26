package com.example.tudee.presentation.screens

import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.tudee.R
import com.example.tudee.presentation.components.EmptyTasks
import com.example.tudee.presentation.components.HomeTopBar
import com.example.tudee.presentation.components.OverviewCard
import com.example.tudee.presentation.design.theme.Theme
import com.example.tudee.presentation.design.theme.TudeeTheme

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun HomeScreen(
    modifier: Modifier = Modifier, navController: NavController,
    onFABClicked: () -> Unit = {}
) {
    Scaffold(
        floatingActionButton = {
            FloatingActionButton(
                onClick = { onFABClicked() },
                containerColor = Theme.colors.primary
            ) {
                Icon(
                    painterResource(R.drawable.ic_add_task),
                    contentDescription = "Add Task",
                    tint = Theme.colors.onPrimary,
                    modifier = Modifier.size(24.dp)
                )
            }
        })
    {
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
                    HomeTopBar(modifier = Modifier.padding(vertical = 12.dp))

                    OverviewCard(
                        currentDate = "today, 22 Jun 2025",
                        statusIconId = R.drawable.ic_status_neutral,
                        tudeeStatusImgId = R.drawable.im_robot_neutral,
                        notificationTitle = "Stay working!",
                        notificationDescription = "You've completed 3 out of 10 tasks Keep going!"
                    )

                    Spacer(Modifier.height(48.dp))

                    EmptyTasks(modifier= Modifier.fillMaxWidth())

                }
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