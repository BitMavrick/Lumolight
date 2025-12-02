package com.bitmavrick.flash.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.bitmavrick.flash.FlashlightUiEvent
import com.bitmavrick.flash.FlashlightUiState
import com.bitmavrick.store.preset.DurationPreset
import com.bitmavrick.ui.elements.Preset

@Composable
fun DurationPresetChips(
    uiState: FlashlightUiState,
    onEvent: (FlashlightUiEvent) -> Unit
) {
    LazyRow(
        modifier = Modifier.fillMaxWidth()
    ) {
        DurationPreset.durations.forEach { duration ->
            val isSelected = uiState.duration == duration

            item {
                Preset(
                    modifier = Modifier.padding(end = 8.dp),
                    title = "$duration mins",
                    isSelected = isSelected,
                    onClick = { onEvent(FlashlightUiEvent.UpdateFlashlightDuration(duration)) }
                )
            }
        }
    }
}

@Preview(showBackground = false)
@Composable
private fun DurationPresetChipPreview(){
    DurationPresetChips(
        uiState = FlashlightUiState(),
        onEvent = {}
    )
}