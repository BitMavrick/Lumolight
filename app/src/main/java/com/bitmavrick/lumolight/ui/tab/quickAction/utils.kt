package com.bitmavrick.lumolight.ui.tab.quickAction

import android.content.Context
import android.widget.Toast
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

        Toast.makeText(context, "Screen flash is on", Toast.LENGTH_SHORT).show()
        viewModel.activeStartButton()
    }

    if (uiState.segmentedButtonSelectedIndex == 1 && !uiState.startButtonStatus){

        viewModel.toggleFlashLight(context, true)
        navController.navigate(Screen.FlashScreen.route)

        Toast.makeText(context, "Both flash is on", Toast.LENGTH_SHORT).show()
        viewModel.activeStartButton()
    }


    if (uiState.segmentedButtonSelectedIndex == 2 && !uiState.startButtonStatus){

        viewModel.toggleFlashLight(context, true)

        // Toast.makeText(context, "Back flash is on", Toast.LENGTH_SHORT).show()
        viewModel.activeStartButton()
    }

    if (uiState.segmentedButtonSelectedIndex == 2 && uiState.startButtonStatus){

        viewModel.toggleFlashLight(context, false)

        // Toast.makeText(context, "Back flash is off", Toast.LENGTH_SHORT).show()
        viewModel.stopStartButton()
    }
}