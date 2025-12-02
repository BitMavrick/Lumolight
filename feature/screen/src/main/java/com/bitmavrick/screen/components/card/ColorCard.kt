package com.bitmavrick.screen.components.card

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ColorLens
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedCard
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.core.graphics.toColorInt
import com.bitmavrick.screen.ScreenFlashUiEvent
import com.bitmavrick.screen.ScreenFlashUiState
import com.bitmavrick.screen.components.ColorPresetChips
import com.bitmavrick.store.preset.ColorPreset
import com.bitmavrick.ui.elements.HueBar
import java.util.Locale
import com.bitmavrick.locales.R as localesR

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ColorCard(
    uiState: ScreenFlashUiState,
    onEvent: (ScreenFlashUiEvent) -> Unit
) {
    val backgroundColor = remember(uiState.screenColorHue, uiState.screenColorSat, uiState.screenColorVal) {
        Color.hsv(
            uiState.screenColorHue,
            uiState.screenColorSat,
            uiState.screenColorVal
        )
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
                    imageVector = Icons.Default.ColorLens,
                    contentDescription = null
                )

                Spacer(Modifier.padding(2.dp))

                Text(
                    text = stringResource(localesR.string.color),
                    style = MaterialTheme.typography.bodyLarge,
                    fontWeight = FontWeight.SemiBold
                )
            }

            val color = if(uiState.screenColorPresetSelection){
                Color(ColorPreset.list[uiState.screenColorPresetIndex].code.toColorInt())
            }else{
                backgroundColor
            }

            val colorHex = String.format(
                Locale.getDefault(),
                "#%06X",
                0xFFFFFF and color.toArgb()
            )

            Spacer(Modifier.padding(2.dp))

            Text(
                text = colorHex,
                color = color,
                style = MaterialTheme.typography.displayMedium,
            )

            Spacer(Modifier.padding(2.dp))

            HueBar (
                hue = uiState.screenColorHue,
                onHueChange = {},
                onHueChangeFinished = { hue ->
                    onEvent(ScreenFlashUiEvent.UpdateScreenColor(hue, 1f, 1f))
                }
            )

            Spacer(Modifier.padding(4.dp))

            ColorPresetChips(
                uiState = uiState,
                onEvent = onEvent
            )
        }
    }
}

@Preview(showBackground = false)
@Composable
fun ColorCardPreview(){
    ColorCard(
        uiState = ScreenFlashUiState(),
        onEvent = {}
    )
}