package com.bitmavrick.lumolight.ui.tab.quickAction

import android.content.Context
import androidx.navigation.NavController
import com.bitmavrick.lumolight.activity.core.Screen

fun startButtonActionHandler(
    navController: NavController,
    viewModel: QuickActionViewModel,
    uiState: QuickActionUiState,
    context: Context
){
    if (uiState.segmentedButtonSelectedIndex == 0 && !uiState.startButtonStatus){
        navController.navigate(Screen.FlashScreen.route)
        viewModel.activeStartButton()
    }

    if (uiState.segmentedButtonSelectedIndex == 1 && !uiState.startButtonStatus){
        viewModel.toggleFlashLight(context, true)
        navController.navigate(Screen.FlashScreen.route)
        viewModel.activeStartButton()
    }


    if (uiState.segmentedButtonSelectedIndex == 2 && !uiState.startButtonStatus){
        viewModel.toggleFlashLight(context, true)
        viewModel.activeStartButton()
    }

    if (uiState.segmentedButtonSelectedIndex == 2 && uiState.startButtonStatus){
        viewModel.toggleFlashLight(context, false)
        viewModel.stopStartButton()
    }
}