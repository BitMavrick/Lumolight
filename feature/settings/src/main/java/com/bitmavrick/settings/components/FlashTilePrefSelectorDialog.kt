package com.bitmavrick.settings.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.selection.selectable
import androidx.compose.foundation.selection.selectableGroup
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.FlashlightOn
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FilledTonalButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.dp
import com.bitmavrick.settings.SettingsUiEvent
import com.bitmavrick.settings.SettingsUiState
import com.bitmavrick.theme.LumolightTheme
import com.bitmavrick.locales.R as localesR

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FlashTilePrefSelectorDialog(
    uiState: SettingsUiState,
    onEvent: (SettingsUiEvent) -> Unit,
    onDismissRequest: () -> Unit
) {
    AlertDialog(
        icon = {
            Icon(
                imageVector = Icons.Rounded.FlashlightOn,
                contentDescription = null
            )
        },
        onDismissRequest = {
            onDismissRequest()
        },
        title = {
            Text(stringResource(localesR.string.flash_tile_preference))
        },
        confirmButton = {},
        dismissButton = {
            FilledTonalButton(
                onClick = {
                    onDismissRequest()
                }
            ) {
                Text(stringResource(localesR.string.cancel))
            }
        },
        text = {
            Column(Modifier.selectableGroup()) {
                Row(
                    Modifier
                        .fillMaxWidth()
                        .clip(RoundedCornerShape(10.dp))
                        .height(46.dp)
                        .selectable(
                            selected = uiState.flashTilePreference == 0,
                            onClick = {
                                onEvent(SettingsUiEvent.UpdateFlashTilePreference(0))
                                onDismissRequest() },
                            role = Role.RadioButton
                        )
                        .padding(horizontal = 12.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    RadioButton(
                        selected = uiState.flashTilePreference == 0,
                        onClick = null
                    )
                    Text(
                        text = stringResource(localesR.string.screen_flash),
                        style = MaterialTheme.typography.bodyLarge,
                        modifier = Modifier.padding(start = 12.dp)
                    )
                }

                Row(
                    Modifier
                        .fillMaxWidth()
                        .clip(RoundedCornerShape(10.dp))
                        .height(46.dp)
                        .selectable(
                            selected = uiState.flashTilePreference == 1,
                            onClick = { onEvent(SettingsUiEvent.UpdateFlashTilePreference(1))
                                onDismissRequest()
                            },
                            role = Role.RadioButton
                        )
                        .padding(horizontal = 12.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    RadioButton(
                        selected = uiState.flashTilePreference == 1,
                        onClick = null
                    )
                    Text(
                        text = stringResource(localesR.string.dual_flash),
                        style = MaterialTheme.typography.bodyLarge,
                        modifier = Modifier.padding(start = 12.dp)
                    )
                }

                Row(
                    Modifier
                        .fillMaxWidth()
                        .clip(RoundedCornerShape(10.dp))
                        .height(46.dp)
                        .selectable(
                            selected = uiState.flashTilePreference == 2,
                            onClick = {
                                onEvent(SettingsUiEvent.UpdateFlashTilePreference(2))
                                onDismissRequest() },
                            role = Role.RadioButton
                        )
                        .padding(horizontal = 12.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    RadioButton(
                        selected = uiState.flashTilePreference == 2,
                        onClick = null
                    )
                    Text(
                        text = stringResource(localesR.string.rear_flash),
                        style = MaterialTheme.typography.bodyLarge,
                        modifier = Modifier.padding(start = 12.dp)
                    )
                }
            }
        }
    )
}

@PreviewLightDark
@Composable
private fun FlashTilePrefSelectorDialogPreview(){
    LumolightTheme {
        FlashTilePrefSelectorDialog(
            uiState = SettingsUiState(),
            onEvent = {},
            onDismissRequest = {}
        )
    }
}