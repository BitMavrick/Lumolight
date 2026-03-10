package com.bitmavrick.ui.components

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FilledTonalButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import com.bitmavrick.data.domain.model.LumoFlash
import com.bitmavrick.theme.LumolightTheme
import com.bitmavrick.locales.R as localesR

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DeleteFlashDialog(
    flash: LumoFlash,
    onDelete: (flash: LumoFlash) -> Unit,
    onCancel: () -> Unit
) {
    AlertDialog(
        icon = {
            Icon(
                imageVector = Icons.Default.Delete,
                contentDescription = null
            )
        },
        title = {
            Text(
                text = stringResource(localesR.string.delete) +  " ${flash.title}?",
                textAlign = TextAlign.Center
            )
        },
        text = {
            Text(
                text = "${flash.title} " + stringResource(localesR.string.delete_des),
            )
        },
        onDismissRequest = onCancel,
        dismissButton = {
            TextButton(
                onClick = onCancel
            ) {
                Text(stringResource(localesR.string.cancel))
            }
        },
        confirmButton = {
            FilledTonalButton(
                onClick = {
                    onDelete(flash)
                }
            ) {
                Text(stringResource(localesR.string.yes_delete))
            }
        }
    )
}

@Preview(showBackground = true)
@Composable
private fun DeleteFlashDialogPreview(){
    val flash = LumoFlash(
        title = "Late night flash",
        flashType = 1,
        isPinned = false,
        duration = 121,
        infiniteDuration = false,
        screenColor = "#FFFFFF",
        screenBrightness = 10f,
        flashBpmRate = 0,
        flashIntensity = 1,
        pinnedOrderIndex = null
    )

    LumolightTheme {
        DeleteFlashDialog(
            flash = flash,
            onDelete = {},
            onCancel = {}
        )
    }
}