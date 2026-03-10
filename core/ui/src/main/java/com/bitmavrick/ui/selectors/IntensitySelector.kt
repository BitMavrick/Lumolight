package com.bitmavrick.ui.selectors

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.FlashOn
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExperimentalMaterial3ExpressiveApi
import androidx.compose.material3.FilledTonalButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Slider
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.dp
import com.bitmavrick.theme.LumolightTheme
import com.bitmavrick.locales.R as localesR

@OptIn(ExperimentalMaterial3Api::class, ExperimentalMaterial3ExpressiveApi::class)
@Composable
fun IntensitySelector(
    initialIntensity: Int = 1,
    maxValue: Int = 5,
    onConfirm: (intensity: Int) -> Unit = {},
    onCancel: () -> Unit = {}
) {
    var intensity by remember { mutableIntStateOf(initialIntensity) }

    AlertDialog(
        icon = {
            Icon(
                imageVector = Icons.Default.FlashOn,
                contentDescription = null
            )
        },
        title = {
            Text(
                text = stringResource(localesR.string.intensity),
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.primary
            )
        },
        onDismissRequest = onCancel,
        confirmButton = {
            FilledTonalButton(
                onClick = { onConfirm(intensity) }
            ) {
                Text(stringResource(localesR.string.ok))
            }
        },
        dismissButton = {
            TextButton(onClick = onCancel) {
                Text(stringResource(localesR.string.cancel))
            }
        },
        text = {
            Column(
                modifier = Modifier.width(300.dp)
            ) {
                Text(
                    text = "Level: $intensity",
                    style = MaterialTheme.typography.titleMedium,
                    modifier = Modifier.padding(bottom = 8.dp)
                )
                Slider(
                    value = intensity.toFloat(),
                    valueRange = 1f..maxValue.toFloat(),
                    onValueChange = {
                        intensity = it.toInt()
                    }
                )

                Row(
                    modifier = Modifier.padding(top = 8.dp),
                ){
                    OutlinedButton(
                        onClick = {
                            intensity = (intensity - 1).coerceIn(1, maxValue)
                        },
                        shapes = ButtonDefaults.shapes()
                    ) {
                        Text(
                            text = "-1",
                            fontWeight = FontWeight.Bold,
                            color = MaterialTheme.colorScheme.tertiary
                        )
                    }

                    Spacer(modifier = Modifier.width(16.dp))

                    OutlinedButton(
                        onClick = {
                            intensity = (intensity + 1).coerceIn(1, maxValue)
                        },
                        shapes = ButtonDefaults.shapes()
                    ) {
                        Text(
                            text = "+1",
                            fontWeight = FontWeight.Bold,
                            color = MaterialTheme.colorScheme.primary
                        )
                    }
                }
            }
        }
    )
}

@PreviewLightDark
@Composable
private fun IntensitySelectorPreview(){
    LumolightTheme {
        IntensitySelector(
            initialIntensity = 1,
            maxValue = 5
        )
    }
}