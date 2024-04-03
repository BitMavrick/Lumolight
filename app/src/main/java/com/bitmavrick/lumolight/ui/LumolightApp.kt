package com.bitmavrick.lumolight.ui

import androidx.compose.material3.windowsizeclass.WindowWidthSizeClass
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import com.bitmavrick.lumolight.ui.utils.LumolightNavigationType
import androidx.lifecycle.viewmodel.compose.viewModel

@Composable
fun LumolightApp(
    windowSize: WindowWidthSizeClass
){
    val navigationType: LumolightNavigationType
    val viewModel: LumolightViewModel = viewModel()
    val lumolightUiState = viewModel.uiState.collectAsState().value

    when(windowSize){
        WindowWidthSizeClass.Compact -> {
            // For Mobile Devices
            navigationType = LumolightNavigationType.BOTTOM_NAVIGATION
        }

        WindowWidthSizeClass.Medium -> {
            // For Foldable Devices
            navigationType = LumolightNavigationType.NAVIGATION_RAIL
        }

        WindowWidthSizeClass.Expanded -> {
            // For Tablet or Chromebooks
            navigationType = LumolightNavigationType.PERMANENT_NAVIGATION_DRAWER
        }

        else -> {
            // For other devices
            navigationType = LumolightNavigationType.BOTTOM_NAVIGATION
        }
    }
}