package com.bitmavrick.screen.components.card

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Brightness7
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
import com.bitmavrick.screen.ScreenFlashUiEvent
import com.bitmavrick.screen.ScreenFlashUiState
import com.bitmavrick.screen.components.BrightnessPresetChips
import com.bitmavrick.locales.R as localesR

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BrightnessCard(
    uiState: ScreenFlashUiState,
    onEvent: (ScreenFlashUiEvent) -> Unit
) {
    var brightnessSliderPosition by rememberSaveable { mutableFloatStateOf(uiState.brightness.toFloat()) }

    LaunchedEffect(uiState.brightness) {
        brightnessSliderPosition = uiState.brightness.toFloat()
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
                    imageVector = Icons.Default.Brightness7,
                    contentDescription = null
                )

                Spacer(Modifier.padding(2.dp))

                Text(
                    text = stringResource(localesR.string.brightness),
                    style = MaterialTheme.typography.bodyLarge,
                    fontWeight = FontWeight.SemiBold
                )
            }

            Spacer(Modifier.padding(2.dp))

            Text(
                text = "${brightnessSliderPosition.toInt()}%",
                style = MaterialTheme.typography.displayMedium,
                color = MaterialTheme.colorScheme.tertiary
            )

            Spacer(Modifier.padding(2.dp))

            Slider(
                value = brightnessSliderPosition,
                onValueChange = { brightnessSliderPosition = it },
                valueRange = 1f..100f,
                onValueChangeFinished = { onEvent(ScreenFlashUiEvent.UpdateBrightness(brightnessSliderPosition.toInt())) },
                track = {
                    SliderDefaults.Track(
                        sliderState = it,
                        modifier = Modifier.height(42.dp)
                    )
                }
            )

            Spacer(Modifier.padding(4.dp))

            BrightnessPresetChips(
                uiState = uiState,
                onEvent = onEvent
            )
        }
    }
}

@Preview(showBackground = false)
@Composable
private fun BrightnessCardPreview(){
    BrightnessCard(
        uiState = ScreenFlashUiState(
            brightness = 30
        ),
        onEvent = {}
    )
}