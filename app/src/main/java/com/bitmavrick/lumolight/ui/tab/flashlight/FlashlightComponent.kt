/* *
* Lumolight :: Open-source program under GPL-3.0 :: Copyright - BitMavrick :: https://github.com/BitMavrick
* */

package com.bitmavrick.lumolight.ui.tab.flashlight

import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.viewmodel.compose.viewModel
import com.bitmavrick.lumolight.R
import com.bitmavrick.lumolight.ui.tab.CustomFilledButton
import com.bitmavrick.lumolight.util.formatDuration
import androidx.compose.runtime.State
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.remember


@Composable
fun FlashAlertDialog(
    time: State<Int>,
    flashlightViewModel: FlashlightViewModel,
    onClickDismiss: () -> Unit
){
    val context = LocalContext.current
    val uiState = flashlightViewModel.uiState.collectAsState().value

    AlertDialog(
        onDismissRequest = {},
        confirmButton = {},
        title = { Text(text = context.getString(R.string.flashlight_active)) },
        text = {
            if(uiState.flashlightDurationMin != -1){
                Text(
                    text = "Duration: ${formatDuration(time.value)}\nBlink per min: ${uiState.flashlightBpmValue}"
                )
            }else{
                Text(
                    text = "Duration: N/A\nBlink per min: ${uiState.flashlightBpmValue}"
                )
            }
        },
        dismissButton = {
            CustomFilledButton(
                buttonText = context.getString(R.string.stop).uppercase(),
                onClick = {
                    onClickDismiss()
                }
            )
        }
    )
}

@Preview
@Composable
fun FlashAlertDialogPreview(){
    val flashlightViewModel : FlashlightViewModel = viewModel()
    FlashAlertDialog(
        time = remember { derivedStateOf { 0 } },
        flashlightViewModel = flashlightViewModel,
        onClickDismiss = {}
    )
}

