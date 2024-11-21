/* *
* Lumolight :: Open-source program under GPL-3.0 :: Copyright - BitMavrick :: https://github.com/BitMavrick
* */

package com.bitmavrick.lumolight.ui.screen.screenFlash

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.bitmavrick.lumolight.system.KeepScreenOn
import com.bitmavrick.lumolight.system.SetBrightness
import com.bitmavrick.lumolight.ui.screen.setting.SettingUiState
import com.bitmavrick.lumolight.ui.tab.CustomOutlinedButton

@Composable
fun QuickFlashScreen(
    settingUiState: SettingUiState,
    onClose: () -> Unit
){

    SetBrightness(1f)
    KeepScreenOn()

    Scaffold (
        content = { innerPadding ->
            Column (
                Modifier
                    .fillMaxSize()
                    .background(
                        color = Color.White
                    )
                    .padding(innerPadding)
                    .padding(16.dp),
                verticalArrangement = Arrangement.Bottom,
            ){
                Column(
                    Modifier
                        .weight(1f)
                        .fillMaxWidth(),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    // ? Empty Space
                }
                CustomOutlinedButton(
                    buttonText = "Close",
                    onClick = {
                        onClose()
                    },
                    color = MaterialTheme.colorScheme.primary,
                    hapticStatus = settingUiState.hapticStatus
                )
            }
        }
    )

    BackHandler {
        onClose()
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun QuickFlashScreenPreview(){
    QuickFlashScreen(
        settingUiState = SettingUiState(),
        onClose = {}
    )
}