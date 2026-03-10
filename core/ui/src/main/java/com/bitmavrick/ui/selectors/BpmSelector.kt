package com.bitmavrick.ui.selectors

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.ButtonDefaults
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
import compose.icons.TablerIcons
import compose.icons.tablericons.Urgent
import com.bitmavrick.locales.R as localesR

@OptIn(ExperimentalMaterial3ExpressiveApi::class)
@Composable
fun BpmSelectorDialog(
    initialBpm : Int = 0,
    onConfirm: (bpm: Int) -> Unit = {},
    onCancel: () -> Unit = {}
) {
    var bpm by remember { mutableIntStateOf(initialBpm) }

    AlertDialog(
        onDismissRequest = onCancel,
        confirmButton = {
            FilledTonalButton(
                onClick = {
                    onConfirm(bpm)
                }
            ) {
                Text(stringResource(localesR.string.ok))
            }
        },
        dismissButton = {
            TextButton(onClick = onCancel) {
                Text(stringResource(localesR.string.cancel))
            }
        },
        icon = {
            Icon(
                imageVector = TablerIcons.Urgent,
                contentDescription = null
            )
        },
        title = {
            Text(
                text = "BPM (Bit per minute)",
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.primary
            )
        },
        text = {
            Column(
                modifier = Modifier.width(300.dp)
            ) {
                Text(
                    text = "BPM: $bpm",
                    style = MaterialTheme.typography.titleMedium,
                    modifier = Modifier.padding(bottom = 8.dp)
                )

                Slider(
                    value = bpm.toFloat(),
                    valueRange = 0f..150f,
                    onValueChange = {
                        bpm = it.toInt()
                    }
                )

                Row(
                    modifier = Modifier.padding(top = 8.dp),
                ){
                    OutlinedButton(
                        onClick = {
                            bpm = (bpm - 1).coerceIn(0, 150)
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
                            bpm = (bpm + 1).coerceIn(0, 150)
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
private fun BpmSelectorDialogPreview(){
    LumolightTheme {
        BpmSelectorDialog()
    }
}