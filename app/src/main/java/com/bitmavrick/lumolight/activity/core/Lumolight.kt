/* *
* Lumolight :: Open-source program under GPL-3.0 :: Copyright - BitMavrick :: https://github.com/BitMavrick
* */

package com.bitmavrick.lumolight.activity.core

import androidx.compose.animation.EnterTransition
import androidx.compose.animation.ExitTransition
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.scaleIn
import androidx.compose.animation.scaleOut
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
import com.bitmavrick.lumolight.ui.screen.setting.AboutScreen
import com.bitmavrick.lumolight.ui.screen.setting.AppearanceScreen
import com.bitmavrick.lumolight.ui.screen.setting.SettingScreen
import com.bitmavrick.lumolight.ui.screen.setting.SettingViewModel
import com.bitmavrick.lumolight.ui.screen.sos.SosScreen
import com.bitmavrick.lumolight.ui.screen.sos.SosViewModel
import com.bitmavrick.lumolight.ui.tab.flashlight.FlashlightViewModel
import com.bitmavrick.lumolight.ui.tab.quickAction.QuickActionUiEvent
import com.bitmavrick.lumolight.ui.tab.quickAction.QuickActionViewModel
import com.bitmavrick.lumolight.ui.tab.screenFlash.ScreenFlashViewModel

sealed class Screen(val route: String){
    data object HomeScreen : Screen("home_screen")
    data object FlashScreen : Screen("flash_screen")
    data object SettingScreen : Screen("setting_screen")
    data object AboutScreen : Screen("about_screen")
    data object AppearanceScreen : Screen("appearance_screen")
    data object SosScreen : Screen("sos_screen")
}

@Composable
fun Lumolight(
    homeViewModel: HomeViewModel = viewModel(),
    quickActionViewModel: QuickActionViewModel = viewModel(),
    screenFlashViewModel: ScreenFlashViewModel = viewModel(),
    flashlightViewModel: FlashlightViewModel = viewModel(),
    settingViewModel: SettingViewModel = viewModel(),
    sosViewModel: SosViewModel = viewModel()
) {
    val navController = rememberNavController()
    val context = LocalContext.current

    NavHost(
        navController = navController,
        startDestination = Screen.HomeScreen.route
    ) {
        composable(
            route = Screen.HomeScreen.route,
            enterTransition = {
                zoomInTransition()
            },
            exitTransition = {
                zoomOutTransition()
            }
        ){
            HomeScreen(
                navController = navController,
                homeUiState = homeViewModel.uiState.collectAsState().value,
                homeOnEvent = homeViewModel::onEvent,
                quickActionUiState = quickActionViewModel.uiState.collectAsState().value,
                quickActionUiEvent = quickActionViewModel::onEvent,
                screenFlashViewModel = screenFlashViewModel,
                flashlightViewModel = flashlightViewModel,
                sosViewModel = sosViewModel,
            )
        }

        composable(route = Screen.FlashScreen.route){
            FlashScreen(
                navController = navController,
                screenFlashUiState = screenFlashViewModel.uiState.collectAsState().value,
                onClose = {
                    val quickActionUiEvent = quickActionViewModel::onEvent
                    quickActionUiEvent(QuickActionUiEvent.StopStartButton)
                    quickActionUiEvent(QuickActionUiEvent.ToggleFlashLight(context, false))
                    navController.navigateUp()
                }
            )
        }

        composable(route = Screen.SosScreen.route){
            SosScreen(
                sosViewModel = sosViewModel,
                onExitClick = {
                    navController.navigateUp()
                }
            )
        }

        composable(
            route = Screen.SettingScreen.route,
            enterTransition = {
                zoomInTransition()
            },
            exitTransition = {
                zoomOutTransition()
            }
        ){
            SettingScreen(
                settingUiState = settingViewModel.uiState.collectAsState().value,
                settingOnEvent = settingViewModel::onEvent,
                onClickBack = { navController.navigateUp() },
                onClickAbout = { navController.navigate(Screen.AboutScreen.route) },
                onClickAppearance = { navController.navigate(Screen.AppearanceScreen.route) }
            )
        }

        composable(
            route = Screen.AboutScreen.route,
            enterTransition = {
                zoomInTransition()
            },
            exitTransition = {
                zoomOutTransition()
            }
        ){
            AboutScreen(
                onClickBack = { navController.navigateUp() }
            )
        }

        composable(
            route = Screen.AppearanceScreen.route,
            enterTransition = {
                zoomInTransition()
            },
            exitTransition = {
                zoomOutTransition()
            }
        ){
            AppearanceScreen(
                settingUiState = settingViewModel.uiState.collectAsState().value,
                settingUiEvent = settingViewModel::onEvent,
                onClickBack = { navController.navigateUp() }
            )
        }
    }
}

// * Optimized only for the forward view
fun zoomInTransition(): EnterTransition {
    return scaleIn(
        initialScale = 0.85f,
        animationSpec = tween(
            durationMillis = 250,
            easing = FastOutSlowInEasing
        )
    )+ fadeIn(
        animationSpec = tween(
            durationMillis = 250,
            easing = FastOutSlowInEasing
        )
    )
}

fun zoomOutTransition(): ExitTransition {
    return scaleOut(
        targetScale = 1.15f,
        animationSpec = tween(
            durationMillis = 250,
            easing = FastOutSlowInEasing
        )
    ) + fadeOut(
        animationSpec = tween(
            durationMillis = 250,
            easing = FastOutSlowInEasing
        )
    )
}