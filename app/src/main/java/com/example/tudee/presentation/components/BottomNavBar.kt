package com.example.tudee.presentation.components

import androidx.annotation.DrawableRes
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.NavigationBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.tudee.R
import com.example.tudee.navigation.Screens
import com.example.tudee.presentation.designSystem.theme.Theme
import com.example.tudee.presentation.designSystem.theme.TudeeTheme

@Composable
fun BottomNavBar(
    modifier: Modifier = Modifier,
    navController: NavController
) {
    NavigationBar(containerColor = Theme.colors.surfaceHigh) {
        val navBackStackEntry by navController.currentBackStackEntryAsState()
        val currentRoute = navBackStackEntry?.destination?.route

        Row(
            modifier = modifier
                .fillMaxWidth()
                .padding(horizontal = 32.dp, vertical = 8.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.Bottom
        ) {
            NavBarIcon(
                iconId = if (currentRoute == Screens.Home.route) R.drawable.ic_home_filled else R.drawable.ic_home_outlined,
                contentDescription = "Home",
                isSelected = currentRoute == Screens.Home.route,
                onClick = {
                    navController.navigate(Screens.Home.route) {
                        launchSingleTop = true   // to avoid duplicated nav
                    }
                }
            )

            NavBarIcon(
                iconId = if (currentRoute == Screens.Tasks.route) R.drawable.ic_document_filled else R.drawable.ic_document_outlined,
                contentDescription = "document",
                isSelected = currentRoute == Screens.Tasks.route,
                onClick = {
                    navController.navigate(Screens.Tasks.route) {
                        launchSingleTop = true
                    }
                }
            )

            NavBarIcon(
                iconId = if (currentRoute == Screens.Categories.route) R.drawable.ic_menu_circle_filled else R.drawable.ic_menu_circle_outlined,
                contentDescription = "menu",
                isSelected = currentRoute == Screens.Categories.route,
                onClick = {
                    navController.navigate(Screens.Categories.route) {
                        launchSingleTop = true
                    }
                },
            )
        }
    }
}

@Composable
fun NavBarIcon(
    modifier: Modifier = Modifier,
    @DrawableRes iconId: Int,
    contentDescription: String,
    onClick: () -> Unit = {},
    isSelected: Boolean
) {
    val backgroundColor = if (isSelected) Theme.colors.primaryVariant else Theme.colors.surfaceHigh
    val tintColor = if (isSelected) Theme.colors.primary else Theme.colors.hint.copy(alpha = 1f)
    IconButton(
        onClick = onClick,
        colors = IconButtonDefaults.iconButtonColors(containerColor = backgroundColor),
        modifier = Modifier
            .size(42.dp)
            .background(color = backgroundColor, shape = RoundedCornerShape(16.dp))
    ) {
        Icon(
            painterResource(iconId),
            tint = tintColor,
            contentDescription = contentDescription,
            modifier = modifier
                .size(22.dp)
        )
    }
}

@Preview
@Composable
private fun BottomNavBarPreview() {
    val navController = rememberNavController()
    TudeeTheme {
        BottomNavBar(navController = navController)
    }
}