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
import com.bitmavrick.lumolight.system.KeepScreenOn
import com.bitmavrick.lumolight.system.SetMaxBrightness
import com.bitmavrick.lumolight.ui.tab.CustomOutlinedButton
import com.bitmavrick.lumolight.ui.tab.screenFlash.ScreenFlashUiState

@Composable
fun FlashScreen(
    screenFlashUiState: ScreenFlashUiState = ScreenFlashUiState(),
    onClose: () -> Unit
) {
    SetMaxBrightness()
    KeepScreenOn()

    Scaffold (
        content = { innerPadding ->
            Column (
                Modifier
                    .fillMaxSize()
                    .background(
                        color = Color(screenFlashUiState.screenFlashColorValue.toColorInt())
                    )
                    .padding(innerPadding).padding(16.dp),
                verticalArrangement = Arrangement.Bottom,
            ){
                CustomOutlinedButton(
                    buttonText = "Close",
                    onClick = {
                        onClose()
                    }
                )
            }
        }
    )
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun FlashScreenPreview(){
    FlashScreen(
        onClose = {}
    )
}