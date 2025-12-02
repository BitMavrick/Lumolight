package com.bitmavrick.flash.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.util.fastForEachIndexed
import com.bitmavrick.flash.FlashlightUiEvent
import com.bitmavrick.flash.FlashlightUiState
import com.bitmavrick.store.preset.BpmPreset
import com.bitmavrick.ui.elements.Preset

@Composable
fun BpmPresetChips(
    uiState: FlashlightUiState,
    onEvent: (FlashlightUiEvent) -> Unit
) {
    LazyRow(
        modifier = Modifier.fillMaxWidth()
    ) {
        BpmPreset.list.forEach {  bpm ->
            val isSelected = uiState.bpm == bpm.value

            item {
                Preset(
                    modifier = Modifier.padding(end = 8.dp),
                    title = bpm.title,
                    isSelected = isSelected,
                    onClick = { onEvent(FlashlightUiEvent.UpdateFlashlightBpmRate(bpm.value)) }
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun BpmPresetPreview(){
    BpmPresetChips(
        uiState = FlashlightUiState(),
        onEvent = {}
    )
}