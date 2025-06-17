/* *
* Lumolight :: Open-source program under GPL-3.0 :: Copyright - BitMavrick :: https://github.com/BitMavrick
* */

package com.bitmavrick.lumolight.ui.tab.flashlight

import android.os.Build
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.FlashlightOn
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Slider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.bitmavrick.lumolight.R
import com.bitmavrick.lumolight.ui.tab.CustomFilledButton
import com.bitmavrick.lumolight.util.BpmValue
import com.bitmavrick.lumolight.util.TimeDuration
import com.bitmavrick.lumolight.util.formatDuration
import com.bitmavrick.lumolight.util.vibrate
import kotlin.math.roundToInt

@Composable
fun FlashAlertDialog(
    time: State<Int>,
    uiState : FlashlightUiState,
    uiEvent: (FlashlightUiEvent) -> Unit,
    onClickDismiss: () -> Unit,
    hapticStatus: Boolean = false
){
    val context = LocalContext.current

    val flashlightBpmValue = BpmValue.list[uiState.flashlightBpmIndex].value
    val flashlightDurationMin = TimeDuration.list[uiState.flashlightDurationIndex].time

    AlertDialog(
        onDismissRequest = {},
        confirmButton = {},
        icon = {
            Icon(
                Icons.Filled.FlashlightOn,
                contentDescription = null,
                tint = MaterialTheme.colorScheme.primary
            )
        },
        title = {
            Row(
                Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.Center
            ) {
                Text(
                    text = context.getString(R.string.flashlight_active),
                    color = MaterialTheme.colorScheme.primary,
                    fontWeight = FontWeight.SemiBold
                )
            }
        },
        text = {
            Column {
                if(flashlightDurationMin != -1){
                    Text(
                        text = "Duration: ${formatDuration(time.value)}\nBlink per min: $flashlightBpmValue"
                    )
                }else{
                    Text(
                        text = "Duration: N/A\nBlink per min: $flashlightBpmValue"
                    )
                }

                if(Build.VERSION.SDK_INT >= 33 && uiState.flashlightMaxStrengthIndex > 1){
                    Spacer(modifier = Modifier.height(16.dp))

                    FlashStrengthSlider(
                        hapticStatus = hapticStatus,
                        uiState = uiState,
                        uiEvent = uiEvent
                    )
                }
            }
        },

        dismissButton = {
            CustomFilledButton(
                buttonText = context.getString(R.string.stop).uppercase(),
                onClick = {
                    onClickDismiss()
                },
                hapticStatus = hapticStatus
            )
        }
    )
}

@Composable
fun FlashStrengthSlider(
    hapticStatus: Boolean,
    uiState : FlashlightUiState,
    uiEvent: (FlashlightUiEvent) -> Unit,
) {
    val context = LocalContext.current
    val flashlightDurationMin = TimeDuration.list[uiState.flashlightDurationIndex].time

    if(flashlightDurationMin == -1){
        Column {
            Text(
                text = "Strength Level: ${uiState.flashlightStrength}",
                fontWeight = FontWeight.Bold
            )

            Spacer(modifier = Modifier.height(4.dp))

            Slider(
                modifier = Modifier.semantics { contentDescription = "Localized Description" },
                value = uiState.flashlightStrength.toFloat(),
                onValueChange = {
                    uiEvent(FlashlightUiEvent.UpdateFlashlightStrength(it.roundToInt()))

                    if(hapticStatus){
                        vibrate(context)
                    }
                },
                valueRange = 1f..uiState.flashlightMaxStrengthIndex.toFloat(),
                steps = uiState.flashlightMaxStrengthIndex - 2
            )
        }
    }
}

@Preview
@Composable
fun FlashAlertDialogPreview(){
    FlashAlertDialog(
        time = remember { derivedStateOf { 0 } },
        uiState = FlashlightUiState(),
        uiEvent = {},
        onClickDismiss = {}
    )
}

