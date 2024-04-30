package com.bitmavrick.lumolight.activity.core

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.bitmavrick.lumolight.ui.screen.home.HomeScreen
import com.bitmavrick.lumolight.ui.screen.home.HomeViewModel
import com.bitmavrick.lumolight.ui.screen.screenFlash.FlashScreen
import com.bitmavrick.lumolight.ui.screen.setting.SettingScreen
import com.bitmavrick.lumolight.ui.tab.flashlight.FlashlightViewModel
import com.bitmavrick.lumolight.ui.tab.quickAction.QuickActionViewModel
import com.bitmavrick.lumolight.ui.tab.screenFlash.ScreenFlashViewModel

sealed class Screen(val route: String){
    data object HomeScreen : Screen("home_screen")
    data object SettingScreen : Screen("setting_screen")
    data object FlashScreen : Screen("flash_screen")
}

@Composable
fun Lumolight(
    homeViewModel: HomeViewModel = viewModel(),
    quickActionViewModel: QuickActionViewModel = viewModel(),
    screenFlashViewModel: ScreenFlashViewModel = viewModel(),
    flashlightViewModel: FlashlightViewModel = viewModel()
) {
    val navController = rememberNavController()
    NavHost(
        navController = navController,
        startDestination = Screen.HomeScreen.route
    ) {
        composable(route = Screen.HomeScreen.route){
            val homeUiState = homeViewModel.uiState.collectAsState().value
            HomeScreen(
                navController = navController,
                homeUiState = homeUiState,
                homeOnEvent = homeViewModel::onEvent,
                quickActionViewModel = quickActionViewModel,
                screenFlashViewModel = screenFlashViewModel,
                flashlightViewModel = flashlightViewModel
            )
        }
        composable(route = Screen.SettingScreen.route){
            SettingScreen(navController = navController)
        }
        composable(route = Screen.FlashScreen.route){
            val screenFlashUiState = screenFlashViewModel.uiState.collectAsState().value
            FlashScreen(
                screenFlashUiState = screenFlashUiState,
                onClose = {
                    quickActionViewModel.stopStartButton()
                    navController.popBackStack()
                }
            )
        }
    }
}

