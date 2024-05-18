package com.bitmavrick.lumolight.ui.screen.screenFlash

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.core.graphics.toColorInt
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.bitmavrick.lumolight.system.KeepScreenOn
import com.bitmavrick.lumolight.system.SetBrightness
import com.bitmavrick.lumolight.ui.tab.CustomOutlinedButton
import com.bitmavrick.lumolight.ui.tab.screenFlash.ScreenFlashUiState
import com.bitmavrick.lumolight.util.formatDuration
import kotlinx.coroutines.delay

@Composable
fun FlashScreen(
    navController: NavController,
    screenFlashUiState: ScreenFlashUiState = ScreenFlashUiState(),
    onClose: () -> Unit
) {
    SetBrightness(screenFlashUiState.screenFlashBrightnessValue)
    KeepScreenOn()

    var time by remember { mutableIntStateOf(screenFlashUiState.screenFlashDurationMin) }

    if(screenFlashUiState.screenFlashDurationMin != -1){

        time = screenFlashUiState.screenFlashDurationMin * 60

        LaunchedEffect(key1 = Unit) {
            for (i in 1..screenFlashUiState.screenFlashDurationMin * 60) {
                delay(1000L)
                time--
            }
            navController.popBackStack()
        }
    }


    Scaffold (
        content = { innerPadding ->
            Column (
                Modifier
                    .fillMaxSize()
                    .background(
                        color = Color(screenFlashUiState.screenFlashColorValue.toColorInt())
                    )
                    .padding(innerPadding)
                    .padding(16.dp),
                verticalArrangement = Arrangement.Bottom,
            ){
                Column(
                    Modifier.weight(1f).fillMaxWidth(),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    if(time != -1){
                        Text(
                            text = formatDuration(time),
                            color = MaterialTheme.colorScheme.primary,
                        )
                    }
                }
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
        navController = rememberNavController(),
        onClose = {}
    )
}