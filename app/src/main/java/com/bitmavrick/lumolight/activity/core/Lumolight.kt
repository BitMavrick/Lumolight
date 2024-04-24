package com.bitmavrick.lumolight.activity.core

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.bitmavrick.lumolight.ui.screen.home.HomeScreen
import com.bitmavrick.lumolight.ui.screen.home.HomeViewModel
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
            val homeViewModel : HomeViewModel = viewModel()
            val homeUiState = homeViewModel.uiState.collectAsState().value
            HomeScreen(
                navController = navController,
                homeUiState = homeUiState,
                homeOnEvent = homeViewModel::onEvent
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