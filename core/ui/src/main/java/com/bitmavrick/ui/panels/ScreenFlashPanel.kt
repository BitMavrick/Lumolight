package com.bitmavrick.ui.panels

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Brightness5
import androidx.compose.material.icons.rounded.Square
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedCard
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.bitmavrick.theme.LumolightTheme
import com.bitmavrick.ui.selectors.BrightnessSelectorDialog
import com.bitmavrick.ui.selectors.ColorSelectorDialog
import com.bitmavrick.ui.selectors.toHexColor

@Composable
fun ScreenFlashPanel(
    brightness: Int,
    color: Color,
    onConfirmBrightness: (brightness: Int) -> Unit,
    onConfirmColor: (color: Color) -> Unit
) {
    val openColorSelector = remember { mutableStateOf(false) }
    val openBrightnessSelector = remember { mutableStateOf(false) }

    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        OutlinedCard(
            modifier = Modifier
                .weight(1f)
                .height(64.dp),
            shape = RoundedCornerShape(16.dp),
            border = BorderStroke(1.dp, MaterialTheme.colorScheme.outlineVariant),
            onClick = { openColorSelector.value = true }
        ) {
            Row(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(horizontal = 16.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center
            ) {
                Icon(
                    imageVector = Icons.Rounded.Square,
                    contentDescription = null,
                    tint = color,
                    modifier = Modifier.padding(end = 8.dp)
                )
                Text(
                    text = color.toArgb().toHexColor().uppercase(),
                    style = MaterialTheme.typography.labelLarge,
                    fontWeight = FontWeight.Bold
                )
            }
        }

        OutlinedCard(
            modifier = Modifier
                .weight(1f)
                .height(64.dp),
            shape = RoundedCornerShape(16.dp),
            border = BorderStroke(1.dp, MaterialTheme.colorScheme.outlineVariant),
            onClick = { openBrightnessSelector.value = true }
        ) {
            Row(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(horizontal = 16.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center
            ) {
                Icon(
                    imageVector = Icons.Outlined.Brightness5,
                    contentDescription = null,
                    modifier = Modifier.padding(end = 8.dp),
                    tint = MaterialTheme.colorScheme.primary
                )
                Text(
                    text = "Bright: $brightness%",
                    style = MaterialTheme.typography.labelLarge,
                    fontWeight = FontWeight.Bold
                )
            }
        }
    }


    if(openColorSelector.value){
        ColorSelectorDialog(
            initialColor = color,
            onConfirm = {
                onConfirmColor(it)
                openColorSelector.value = false
            },
            onCancel = {
                openColorSelector.value = false
            }
        )
    }

    if(openBrightnessSelector.value){
        BrightnessSelectorDialog(
            initialBrightness = brightness.toFloat(),
            onConfirm = {
                onConfirmBrightness(it.toInt())
                openBrightnessSelector.value = false
            },
            onCancel = {
                openBrightnessSelector.value = false
            }
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun ScreenFlashPanelPreview(){
    LumolightTheme {
        ScreenFlashPanel(
            brightness = 78,
            color = Color.Blue,
            onConfirmBrightness = {},
            onConfirmColor = {}
        )
    }
}