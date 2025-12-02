package com.bitmavrick.screen.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.util.fastForEachIndexed
import androidx.core.graphics.toColorInt
import com.bitmavrick.screen.ScreenFlashUiEvent
import com.bitmavrick.screen.ScreenFlashUiState
import com.bitmavrick.store.preset.ColorPreset
import com.bitmavrick.ui.elements.Preset

@Composable
fun ColorPresetChips(
    uiState: ScreenFlashUiState,
    onEvent: (ScreenFlashUiEvent) -> Unit
) {
    LazyRow(
        modifier = Modifier.fillMaxWidth()
    ) {
        ColorPreset.list.fastForEachIndexed { index, color ->
            val isSelected = if(uiState.screenColorPresetSelection){
                uiState.screenColorPresetIndex == index
            } else {
                false
            }

            item {
                Preset(
                    title = color.name,
                    textTint = Color(color.code.toColorInt()),
                    modifier = Modifier.padding(4.dp),
                    isSelected = isSelected,
                    onClick = { onEvent(ScreenFlashUiEvent.UpdateScreenColorPreset(index)) }
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun ColorPresetChipsPreview(){
    ColorPresetChips(
        uiState = ScreenFlashUiState(),
        onEvent = {}
    )
}