/* *
* Lumolight :: Open-source program under GPL-3.0 :: Copyright - BitMavrick :: https://github.com/BitMavrick
* */

package com.bitmavrick.lumolight.ui.tab.quickAction

import android.content.Context
import androidx.navigation.NavController
import com.bitmavrick.lumolight.activity.core.Screen

fun startButtonActionHandler(
    navController: NavController,
    uiState: QuickActionUiState,
    uiEvent: (QuickActionUiEvent) -> Unit,
    context: Context
){
    if (uiState.segmentedButtonSelectedIndex == 0 && !uiState.startButtonStatus){
        navController.navigate(Screen.FlashScreen.route)
        uiEvent(QuickActionUiEvent.ActiveStartButton)
    }

    if (uiState.segmentedButtonSelectedIndex == 1 && !uiState.startButtonStatus){
        uiEvent(QuickActionUiEvent.ToggleFlashLight(context, true))
        navController.navigate(Screen.FlashScreen.route)
        uiEvent(QuickActionUiEvent.ActiveStartButton)
    }


    if (uiState.segmentedButtonSelectedIndex == 2 && !uiState.startButtonStatus){
        uiEvent(QuickActionUiEvent.ToggleFlashLight(context, true))
        uiEvent(QuickActionUiEvent.ActiveStartButton)
    }

    if (uiState.segmentedButtonSelectedIndex == 2 && uiState.startButtonStatus){
        uiEvent(QuickActionUiEvent.ToggleFlashLight(context, false))
        uiEvent(QuickActionUiEvent.StopStartButton)
    }
}