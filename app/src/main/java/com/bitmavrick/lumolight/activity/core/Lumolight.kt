/* *
* Lumolight :: Open-source program under GPL-3.0 :: Copyright - BitMavrick :: https://github.com/BitMavrick
* */

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
import com.bitmavrick.lumolight.ui.screen.screenFlash.QuickFlashScreen
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

@Composable
fun Lumolight(
    homeViewModel: HomeViewModel = viewModel(),
    quickActionViewModel: QuickActionViewModel = viewModel(),
    screenFlashViewModel: ScreenFlashViewModel = viewModel(),
    flashlightViewModel: FlashlightViewModel = viewModel(),
    settingViewModel: SettingViewModel = viewModel(),
    sosViewModel: SosViewModel = viewModel()
) {
    val quickActionUiEvent = quickActionViewModel::onEvent
    val navController = rememberNavController()
    val context = LocalContext.current

    val forwardTransitionSpeed = 275
    val backwardTransitionSpeed = 225

    NavHost(
        navController = navController,
        startDestination = Screen.HomeScreen.route
    ) {
        composable(
            route = Screen.HomeScreen.route,
            enterTransition = {
                zoomInTransition(
                    forwardTransitionSpeed = forwardTransitionSpeed
                )
            },
            exitTransition = {
                zoomOutTransition(
                    forwardTransitionSpeed = forwardTransitionSpeed
                )
            },
            popEnterTransition = {
                popZoomInTransition(
                    backwardTransitionSpeed = backwardTransitionSpeed
                )
            },
            popExitTransition = {
                popZoomOutTransition(
                    backwardTransitionSpeed = backwardTransitionSpeed
                )
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

        composable(route = Screen.QuickFlashScreen.route){
            QuickFlashScreen(
                settingUiState = settingViewModel.uiState.collectAsState().value,
                onClose = {
                    quickActionUiEvent(QuickActionUiEvent.StopStartButton)
                    quickActionUiEvent(QuickActionUiEvent.ToggleFlashLight(context, false))
                    navController.navigateUp()
                }
            )
        }

        composable(route = Screen.FlashScreen.route){
            FlashScreen(
                screenFlashUiState = screenFlashViewModel.uiState.collectAsState().value,
                settingUiState = settingViewModel.uiState.collectAsState().value,
                onClose = {
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
                zoomInTransition(
                    forwardTransitionSpeed = forwardTransitionSpeed
                )
            },
            exitTransition = {
                zoomOutTransition(
                    forwardTransitionSpeed = forwardTransitionSpeed
                )
            },
            popEnterTransition = {
                popZoomInTransition(
                    backwardTransitionSpeed = backwardTransitionSpeed
                )
            },
            popExitTransition = {
                popZoomOutTransition(
                    backwardTransitionSpeed = backwardTransitionSpeed
                )
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
            route = Screen.AppearanceScreen.route,
            enterTransition = {
                zoomInTransition(
                    forwardTransitionSpeed = forwardTransitionSpeed
                )
            },
            exitTransition = {
                zoomOutTransition(
                    forwardTransitionSpeed = forwardTransitionSpeed
                )
            },
            popEnterTransition = {
                popZoomInTransition(
                    backwardTransitionSpeed = backwardTransitionSpeed
                )
            },
            popExitTransition = {
                popZoomOutTransition(
                    backwardTransitionSpeed = backwardTransitionSpeed
                )
            }
        ){
            AppearanceScreen(
                settingUiState = settingViewModel.uiState.collectAsState().value,
                settingUiEvent = settingViewModel::onEvent,
                onClickBack = { navController.navigateUp() }
            )
        }

        composable(
            route = Screen.AboutScreen.route,
            enterTransition = {
                zoomInTransition(
                    forwardTransitionSpeed = forwardTransitionSpeed
                )
            },
            exitTransition = {
                zoomOutTransition(
                    forwardTransitionSpeed = forwardTransitionSpeed
                )
            },
            popEnterTransition = {
                popZoomInTransition(
                    backwardTransitionSpeed = backwardTransitionSpeed
                )
            },
            popExitTransition = {
                popZoomOutTransition(
                    backwardTransitionSpeed = backwardTransitionSpeed
                )
            }
        ){
            AboutScreen(
                onClickBack = { navController.navigateUp() }
            )
        }
    }
}



