package com.bitmavrick.screen.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.bitmavrick.screen.ScreenFlashUiEvent
import com.bitmavrick.screen.ScreenFlashUiState
import com.bitmavrick.store.preset.BrightnessPreset
import com.bitmavrick.ui.elements.Preset

@Composable
fun BrightnessPresetChips(
    uiState: ScreenFlashUiState,
    onEvent: (ScreenFlashUiEvent) -> Unit
){
    LazyRow(
        modifier = Modifier.fillMaxWidth()
    ) {
        BrightnessPreset.brightness.forEach { brightness ->
            val isSelected = uiState.brightness == brightness
            item {
                Preset(
                    onClick = { onEvent(ScreenFlashUiEvent.UpdateBrightness(brightness)) },
                    isSelected = isSelected,
                    modifier = Modifier.padding(4.dp),
                    title = "$brightness %"
                )
            }
        }
    }
}

@Preview(showBackground = false)
@Composable
private fun BrightnessPresetChipsPreview(){
    BrightnessPresetChips(
        uiState = ScreenFlashUiState(),
        onEvent = {}
    )
}
