package com.bitmavrick.lumolight.activity.core

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.bitmavrick.lumolight.ui.screen.home.HomeScreen
import com.bitmavrick.lumolight.ui.screen.setting.SettingScreen

@Composable
fun Lumolight() {
    val navController = rememberNavController()
    NavHost(
        navController = navController,
        startDestination = Screen.HomeScreen.route
        // DRAFT : LUMOLIGHT CUSTOM NAVIGATION ANIMATION
    ) {
        composable(route = Screen.HomeScreen.route){
            HomeScreen(
                navController = navController
            )
        }
        composable(route = Screen.SettingScreen.route){
            SettingScreen(navController = navController)
        }
    }
}

sealed class Screen(val route: String){
    data object HomeScreen : Screen("home_screen")
    data object SettingScreen : Screen("setting_screen")
}