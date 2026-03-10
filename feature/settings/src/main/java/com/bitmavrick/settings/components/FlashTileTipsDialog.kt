package com.bitmavrick.settings.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Info
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.FilledTonalButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.dp
import com.bitmavrick.ui.R as ResR
import com.bitmavrick.locales.R as localesR

@PreviewLightDark
@Composable
fun FlashTileTipsDialog(
    onDismissRequest: () -> Unit = {}
) {
    AlertDialog(
        icon = {
            Icon(
                imageVector = Icons.Outlined.Info,
                contentDescription = null
            )
        },
        onDismissRequest = {
            onDismissRequest()
        },
        title = {
            Text(
                text = stringResource(localesR.string.lumolight_tile),
                fontWeight = FontWeight.SemiBold
            )
        },
        confirmButton = {},
        dismissButton = {
            FilledTonalButton(
                onClick = {
                    onDismissRequest()
                }
            ) {
                Text(stringResource(localesR.string.ok))
            }
        },
        text = {
            Column {
                Image(
                    painter = painterResource(ResR.drawable.img_tile_showcase),
                    contentDescription = null,
                    modifier = Modifier.fillMaxWidth(),
                    contentScale = ContentScale.Fit
                )

                Text(
                    text = stringResource(localesR.string.lumolight_tile_suggestion),
                    modifier = Modifier.padding(top = 16.dp),
                    style = MaterialTheme.typography.bodyMedium
                )
            }
        }
    )
}