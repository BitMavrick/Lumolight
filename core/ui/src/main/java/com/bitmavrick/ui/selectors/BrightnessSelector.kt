package com.bitmavrick.ui.selectors

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
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
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.dp
import com.bitmavrick.theme.LumolightTheme
import compose.icons.TablerIcons
import compose.icons.tablericons.BrightnessUp
import com.bitmavrick.locales.R as localesR

@OptIn(ExperimentalMaterial3Api::class, ExperimentalMaterial3ExpressiveApi::class)
@Composable
fun BrightnessSelectorDialog(
    initialBrightness: Float = 0f,
    onConfirm: (brightness: Float) -> Unit = {},
    onCancel: () -> Unit = {}
) {
    var brightness by remember { mutableFloatStateOf(initialBrightness) }

    AlertDialog(
        onDismissRequest = onCancel,
        icon = {
            Icon(
                imageVector = TablerIcons.BrightnessUp,
                contentDescription = null
            )
        },
        title = {
            Text(
                text = stringResource(localesR.string.brightness),
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.primary
            )
        },
        confirmButton = {
            FilledTonalButton(
                onClick = { onConfirm(brightness) }
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
                    text = brightness.toInt().toString() + "%",
                    style = MaterialTheme.typography.titleMedium,
                    modifier = Modifier.padding(bottom = 8.dp)
                )
                Slider(
                    value = brightness,
                    valueRange = 0f..100f,
                    onValueChange = {
                        brightness = it
                    }
                )

                Row(
                    modifier = Modifier.padding(top = 8.dp),
                ){
                    OutlinedButton(
                        onClick = {
                            brightness = (brightness - 1f).coerceIn(0f, 100f)
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
                            brightness = (brightness + 1f).coerceIn(0f, 100f)
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
fun BrightnessSelectorDialogPreview(){
    LumolightTheme {
        BrightnessSelectorDialog()
    }
}