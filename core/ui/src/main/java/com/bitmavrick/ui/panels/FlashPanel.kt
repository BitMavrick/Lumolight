package com.bitmavrick.ui.panels

import android.widget.Toast
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.FlashOn
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedCard
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.bitmavrick.theme.LumolightTheme
import com.bitmavrick.ui.selectors.BpmSelectorDialog
import com.bitmavrick.ui.selectors.IntensitySelector
import com.bitmavrick.ui.system.getMaxFlashlightStrengthValue
import com.bitmavrick.ui.system.hasFlashlight
import compose.icons.TablerIcons
import compose.icons.tablericons.Urgent
import com.bitmavrick.locales.R as localesR

@Composable
fun FlashPanel(
    bpm: Int,
    intensity: Int,
    onConfirmBpm: (bpm: Int) -> Unit,
    onConfirmIntensity: (intensity: Int) -> Unit
) {
    val context = LocalContext.current
    val openBpmSelector = remember { mutableStateOf(false) }
    val openIntensitySelector = remember { mutableStateOf(false) }
    val maxIntensity = remember { mutableIntStateOf(1) }

    if(hasFlashlight(context)){
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
                onClick = { openBpmSelector.value = true }
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(horizontal = 16.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Center
                ) {
                    Icon(
                        imageVector = TablerIcons.Urgent,
                        contentDescription = null,
                        modifier = Modifier.padding(end = 8.dp),
                        tint = MaterialTheme.colorScheme.secondary
                    )
                    Text(
                        text = "$bpm BPM",
                        style = MaterialTheme.typography.labelLarge,
                        fontWeight = FontWeight.Bold
                    )
                }
            }

            val unsupported = stringResource(localesR.string.unsupported_hardware)

            OutlinedCard(
                modifier = Modifier
                    .weight(1f)
                    .height(64.dp)
                    .graphicsLayer {
                        alpha = if (getMaxFlashlightStrengthValue(context) > 1) 1f else 0.5f
                    },
                shape = RoundedCornerShape(16.dp),
                border = BorderStroke(1.dp, MaterialTheme.colorScheme.outlineVariant),
                onClick = {
                    if(getMaxFlashlightStrengthValue(context) > 1){
                        maxIntensity.intValue = getMaxFlashlightStrengthValue(context)
                        openIntensitySelector.value = true
                    }else{
                        Toast.makeText(context, unsupported, Toast.LENGTH_SHORT).show()
                    }
                }
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(horizontal = 16.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Center
                ) {
                    Icon(
                        imageVector = Icons.Outlined.FlashOn,
                        contentDescription = null,
                        modifier = Modifier.padding(end = 8.dp),
                        tint = MaterialTheme.colorScheme.tertiary
                    )
                    Text(
                        text = "Level $intensity",
                        style = MaterialTheme.typography.labelLarge,
                        fontWeight = FontWeight.Bold
                    )
                }
            }
        }
    }else{
        Column(
            modifier = Modifier.fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = stringResource(localesR.string.no_flash_hardware_found),
                color = MaterialTheme.colorScheme.error,
                modifier = Modifier.padding(vertical = 8.dp)
            )
        }
    }

    if(openBpmSelector.value){
        BpmSelectorDialog(
            initialBpm = bpm,
            onConfirm = {
                onConfirmBpm(it)
                openBpmSelector.value = false
            },
            onCancel = {
                openBpmSelector.value = false
            }
        )
    }

    if(openIntensitySelector.value){
        IntensitySelector(
            initialIntensity = intensity,
            maxValue = maxIntensity.intValue,
            onConfirm = {
                onConfirmIntensity(it)
                openIntensitySelector.value = false
            },
            onCancel = {
                openIntensitySelector.value = false
            }
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun FlashPanelPreview(){
    LumolightTheme {
        FlashPanel(
            bpm = 0,
            intensity = 5,
            onConfirmBpm = {},
            onConfirmIntensity = {}
        )
    }
}