package com.bitmavrick.flash.components.card

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Fluorescent
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedCard
import androidx.compose.material3.Slider
import androidx.compose.material3.SliderDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.bitmavrick.flash.FlashlightUiEvent
import com.bitmavrick.flash.FlashlightUiState
import com.bitmavrick.flash.components.BpmPresetChips
import com.bitmavrick.locales.R as localesR

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BpmCard(
    uiState: FlashlightUiState,
    onEvent: (FlashlightUiEvent) -> Unit
) {
    var bpmSliderPosition by rememberSaveable { mutableFloatStateOf(uiState.bpm.toFloat()) }

    LaunchedEffect(uiState.bpm) {
        bpmSliderPosition = uiState.bpm.toFloat()
    }

    OutlinedCard(
        modifier = Modifier.fillMaxWidth().padding(8.dp)
    ) {
        Column(
            modifier = Modifier.fillMaxWidth().padding(16.dp)
        ) {
            Row (
                verticalAlignment = Alignment.CenterVertically
            ){
                Icon(
                    imageVector = Icons.Outlined.Fluorescent,
                    contentDescription = null
                )

                Spacer(Modifier.padding(2.dp))

                Text(
                    text = stringResource(localesR.string.bpm_title),
                    style = MaterialTheme.typography.bodyLarge,
                    fontWeight = FontWeight.SemiBold
                )
            }

            Spacer(Modifier.padding(2.dp))

            Text(
                text = "${bpmSliderPosition.toInt()} bpm",
                style = MaterialTheme.typography.displayMedium,
                color = MaterialTheme.colorScheme.tertiary
            )

            Spacer(Modifier.padding(2.dp))

            Slider(
                value = bpmSliderPosition,
                onValueChange = { bpmSliderPosition = it },
                valueRange = 0f..150f,
                onValueChangeFinished = { onEvent(FlashlightUiEvent.UpdateFlashlightBpmRate(bpmSliderPosition.toInt())) },
                track = {
                    SliderDefaults.Track(
                        sliderState = it,
                        modifier = Modifier.height(42.dp)
                    )
                }
            )

            Spacer(Modifier.padding(4.dp))

            BpmPresetChips(
                uiState = uiState,
                onEvent = onEvent
            )
        }
    }
}

@Preview(showBackground = false)
@Composable
fun BpmCardPreview(){
    BpmCard(
        uiState = FlashlightUiState(),
        onEvent = {}
    )
}