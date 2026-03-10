package com.bitmavrick.ui.panels

import android.annotation.SuppressLint
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.AvTimer
import androidx.compose.material.icons.outlined.Timer
import androidx.compose.material.icons.outlined.TimerOff
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedCard
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.bitmavrick.theme.LumolightTheme
import com.bitmavrick.ui.selectors.DurationSelectorDialog

@SuppressLint("DefaultLocale")
@Composable
fun DurationPanel(
    duration: Int,
    isInfinite: Boolean,
    onConfirmDuration: (duration: Int) -> Unit,
    onConfirmInfiniteDuration: (isInfinite: Boolean) -> Unit
) {
    val openDurationSelector = remember { mutableStateOf(false) }

    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        OutlinedCard(
            modifier = Modifier
                .weight(1f)
                .height(64.dp)
                .graphicsLayer {
                    alpha = if (isInfinite) 0.5f else 1f
                },
            shape = RoundedCornerShape(16.dp),
            border = BorderStroke(1.dp, MaterialTheme.colorScheme.outlineVariant),
            onClick = { openDurationSelector.value = true },
            enabled = !isInfinite
        ) {
            Row(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(horizontal = 16.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center
            ) {
                Icon(
                    imageVector = Icons.Outlined.AvTimer,
                    contentDescription = null,
                    modifier = Modifier.padding(end = 8.dp),
                    tint = MaterialTheme.colorScheme.primary
                )
                Text(
                    text = String.format("%02d:%02d m", duration / 60, duration % 60),
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
            onClick = { onConfirmInfiniteDuration(!isInfinite) }
        ) {
            Row(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(horizontal = 16.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center
            ) {
                Icon(
                    imageVector = if (isInfinite) Icons.Outlined.TimerOff else Icons.Outlined.Timer,
                    contentDescription = null,
                    modifier = Modifier.padding(end = 8.dp),
                    tint = if (isInfinite) MaterialTheme.colorScheme.error else MaterialTheme.colorScheme.primary
                )
                Text(
                    text = if (isInfinite) "INFINITE" else "SPECIFIC",
                    style = MaterialTheme.typography.labelLarge,
                    fontWeight = FontWeight.Bold
                )
            }
        }
    }

    if(openDurationSelector.value){
        DurationSelectorDialog(
            initialSeconds = duration,
            onConfirm = {
                onConfirmDuration(it)
                openDurationSelector.value = false
            },
            onCancel = {
                openDurationSelector.value = false
            }
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun DurationPanelPreview(){
    LumolightTheme {
        DurationPanel(
            duration = 121,
            isInfinite = false,
            onConfirmDuration = {},
            onConfirmInfiniteDuration = {}
        )
    }
}