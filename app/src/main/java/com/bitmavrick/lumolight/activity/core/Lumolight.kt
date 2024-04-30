package com.bitmavrick.lumolight.activity.core

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.platform.LocalContext
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
    val context = LocalContext.current

    NavHost(
        navController = navController,
        startDestination = Screen.HomeScreen.route
    ) {
        composable(route = Screen.HomeScreen.route){
            HomeScreen(
                navController = navController,
                homeUiState = homeViewModel.uiState.collectAsState().value,
                homeOnEvent = homeViewModel::onEvent,
                quickActionViewModel = quickActionViewModel,
                screenFlashViewModel = screenFlashViewModel,
                flashlightViewModel = flashlightViewModel
            )
        }

        composable(route = Screen.FlashScreen.route){
            FlashScreen(
                screenFlashUiState = screenFlashViewModel.uiState.collectAsState().value,
                onClose = {
                    quickActionViewModel.stopStartButton()
                    quickActionViewModel.toggleFlashLight(context, false)
                    navController.popBackStack()
                }
            )
        }

        composable(route = Screen.SettingScreen.route){
            SettingScreen(navController = navController)
        }
    }
}