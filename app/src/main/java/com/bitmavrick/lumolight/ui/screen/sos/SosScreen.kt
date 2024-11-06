/* *
* Lumolight :: Open-source program under GPL-3.0 :: Copyright - BitMavrick :: https://github.com/BitMavrick
* */

package com.bitmavrick.lumolight.ui.screen.sos

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
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.bitmavrick.lumolight.system.KeepScreenOn
import com.bitmavrick.lumolight.system.SetBrightness
import com.bitmavrick.lumolight.ui.tab.CustomOutlinedButton
import kotlinx.coroutines.delay

@Composable
fun SosScreen(
    sosViewModel: SosViewModel,
    onExitClick: () -> Unit
) {
    var backgroundColor by remember { mutableStateOf(Color.Red) }
    val context = LocalContext.current

    SetBrightness(1f)
    KeepScreenOn()

    BackHandler {
        sosViewModel.toggleFlashLight(context, false)
        sosViewModel.updateFlashlightStatus(false)
        onExitClick()
    }

    // * Black and Red Combination is also good (Next Update: We can consider to may give users an extra option there)

    LaunchedEffect(key1 = Unit) {
        while (true){
            backgroundColor = if (backgroundColor == Color.Red) Color.White else Color.Red
            delay(700L)
        }
    }

    Scaffold (
        content = { innerPadding ->
            Column (
                Modifier
                    .fillMaxSize()
                    .background(
                        color = backgroundColor
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

                }
                CustomOutlinedButton(
                    buttonText = "Exit",
                    onClick = {
                        sosViewModel.toggleFlashLight(context, false)
                        sosViewModel.updateFlashlightStatus(false)
                        onExitClick()
                    },
                    color = MaterialTheme.colorScheme.primary
                )
            }
        }
    )
}

@Preview(showBackground = true)
@Composable
fun SosScreenPreview() {
    val sosViewModel : SosViewModel = viewModel()
    SosScreen(
        sosViewModel = sosViewModel,
        onExitClick = {}
    )
}