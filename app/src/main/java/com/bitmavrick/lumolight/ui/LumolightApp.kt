package com.bitmavrick.lumolight.ui

import androidx.compose.material3.windowsizeclass.WindowWidthSizeClass
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import com.bitmavrick.lumolight.ui.utils.LumolightNavigationType
import androidx.lifecycle.viewmodel.compose.viewModel
import com.bitmavrick.lumolight.ui.screen.HomeScreen
import com.bitmavrick.lumolight.ui.utils.NavigationItem

@Composable
fun LumolightApp(
    windowSize: WindowWidthSizeClass
){
    val navigationType: LumolightNavigationType
    val viewModel: LumolightViewModel = viewModel()
    val lumolightUiState = viewModel.uiState.collectAsState().value

    when(windowSize){
        WindowWidthSizeClass.Compact -> {
            navigationType = LumolightNavigationType.BOTTOM_NAVIGATION
        }

        WindowWidthSizeClass.Medium -> {
            navigationType = LumolightNavigationType.NAVIGATION_RAIL
        }

        WindowWidthSizeClass.Expanded -> {
            navigationType = LumolightNavigationType.PERMANENT_NAVIGATION_DRAWER
        }

        else -> {
            navigationType = LumolightNavigationType.BOTTOM_NAVIGATION
        }
    }

    HomeScreen(
        navigationType = navigationType,
        uiState = lumolightUiState,
        onTabPressed = {navigationItem: NavigationItem ->
            viewModel.updateCurrentNavigationItem(navigationItem)
        }
    )
}