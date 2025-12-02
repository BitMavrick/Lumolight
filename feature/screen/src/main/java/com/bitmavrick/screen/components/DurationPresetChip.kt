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
import com.bitmavrick.store.preset.DurationPreset
import com.bitmavrick.ui.elements.Preset

@Composable
fun DurationPresetChips(
    uiState: ScreenFlashUiState,
    onEvent: (ScreenFlashUiEvent) -> Unit
){
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
                    onClick = { onEvent(ScreenFlashUiEvent.UpdateDuration(duration)) }
                )
            }
        }
    }
}

@Preview(showBackground = false)
@Composable
private fun DurationPresetChipsPreview() {
    DurationPresetChips(
        uiState = ScreenFlashUiState(),
        onEvent = {}
    )
}