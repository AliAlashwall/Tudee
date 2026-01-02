package com.example.tudee.presentation.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.tudee.navigation.Screens

@Composable

fun MenuScreen(modifier: Modifier = Modifier,navController: NavController) {
    Column(
        Modifier.fillMaxSize(), verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "Menu Screen",
            modifier = modifier
                .background(Color.Red)
                .padding(16.dp)
                .clickable { navController.navigate(Screens.Menu.route) },
            color = Color.White,
            fontSize = 49.sp
        )
    }
}