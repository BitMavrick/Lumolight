package com.bitmavrick.lumolight.ui.screen.screenFlash

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.core.graphics.toColorInt
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.bitmavrick.lumolight.ui.tab.screenFlash.ScreenFlashUiState

@Composable
fun FlashScreen(
    navController: NavController,
    uiState: ScreenFlashUiState
) {
    Column (
        Modifier.fillMaxSize().background(
            color = Color(uiState.screenFlashColorValue.toColorInt())
        ),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ){
        Text(text = "Selected Color: ${uiState.screenFlashColorValue}")
    }
}

@Preview(showBackground = true)
@Composable
fun FlashScreenPreview(){
    FlashScreen(
        navController = rememberNavController(),
        uiState = ScreenFlashUiState()
    )
}