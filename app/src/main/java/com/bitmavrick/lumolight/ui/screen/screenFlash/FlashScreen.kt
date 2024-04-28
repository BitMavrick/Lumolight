package com.bitmavrick.lumolight.ui.screen.screenFlash

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.core.graphics.toColorInt
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.bitmavrick.lumolight.ui.tab.CustomOutlinedButton
import com.bitmavrick.lumolight.ui.tab.screenFlash.ScreenFlashUiState

@Composable
fun FlashScreen(
    navController: NavController,
    uiState: ScreenFlashUiState
) {
    Scaffold (
        content = {innerPadding ->
            Column (
                Modifier
                    .fillMaxSize()
                    .background(
                        color = Color(uiState.screenFlashColorValue.toColorInt())
                    )
                    .padding(innerPadding).padding(16.dp),
                verticalArrangement = Arrangement.Bottom,
            ){
                CustomOutlinedButton(
                    buttonText = "Close",
                    onClick = { navController.popBackStack() }
                )
            }
        }
    )
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun FlashScreenPreview(){
    FlashScreen(
        navController = rememberNavController(),
        uiState = ScreenFlashUiState()
    )
}