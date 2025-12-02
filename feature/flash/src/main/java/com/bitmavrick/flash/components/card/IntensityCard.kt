package com.bitmavrick.flash.components.card

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Bolt
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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.bitmavrick.flash.FlashlightUiEvent
import com.bitmavrick.flash.FlashlightUiState
import com.bitmavrick.ui.system.getMaxFlashlightStrengthValue
import com.bitmavrick.locales.R as localesR

@RequiresApi(Build.VERSION_CODES.TIRAMISU)
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun IntensityCard(
    uiState: FlashlightUiState,
    onEvent: (FlashlightUiEvent) -> Unit
) {
    val context = LocalContext.current

    var intensitySliderPosition by rememberSaveable { mutableFloatStateOf(uiState.intensity.toFloat()) }

    LaunchedEffect(uiState.intensity) {
        intensitySliderPosition = uiState.intensity.toFloat()
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
                    imageVector = Icons.Outlined.Bolt,
                    contentDescription = null
                )

                Spacer(Modifier.padding(2.dp))

                Text(
                    text = stringResource(localesR.string.intensity_level),
                    style = MaterialTheme.typography.bodyLarge,
                    fontWeight = FontWeight.SemiBold
                )
            }

            Spacer(Modifier.padding(2.dp))

            Text(
                text = "${intensitySliderPosition.toInt()}",
                style = MaterialTheme.typography.displayMedium,
                color = MaterialTheme.colorScheme.tertiary
            )

            Spacer(Modifier.padding(2.dp))

            Slider(
                value = intensitySliderPosition,
                onValueChange = { intensitySliderPosition = it },
                valueRange = 1f..getMaxFlashlightStrengthValue(context).toFloat(),
                onValueChangeFinished = { onEvent(FlashlightUiEvent.UpdateFlashlightIntensity(intensitySliderPosition.toInt())) },
                track = {
                    SliderDefaults.Track(
                        sliderState = it,
                        modifier = Modifier.height(42.dp)
                    )
                }
            )

            Spacer(Modifier.padding(2.dp))
        }
    }
}

@RequiresApi(Build.VERSION_CODES.TIRAMISU)
@Preview(showBackground = true)
@Composable
private fun IntensityCardPreview(){
    IntensityCard(
        uiState = FlashlightUiState(),
        onEvent = {}
    )
}