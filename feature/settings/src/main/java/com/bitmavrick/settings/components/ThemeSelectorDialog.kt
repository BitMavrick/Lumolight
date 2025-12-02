package com.bitmavrick.settings.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.selection.selectable
import androidx.compose.foundation.selection.selectableGroup
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.dp
import com.bitmavrick.settings.SettingsUiEvent
import com.bitmavrick.settings.SettingsUiState
import com.bitmavrick.locales.R as localesR

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ThemeSelectorDialog(
    uiState: SettingsUiState,
    onEvent: (SettingsUiEvent) -> Unit,
    onDismissRequest: () -> Unit
) {
    AlertDialog(
        icon = {

        },
        onDismissRequest = {
            onDismissRequest()
        },
        title = {
            Text(stringResource(localesR.string.select_theme))

        },
        confirmButton = {},
        dismissButton = {},
        text = {
            Column(Modifier.selectableGroup()) {
                Row(
                    Modifier
                        .fillMaxWidth()
                        .height(46.dp)
                        .selectable(
                            selected = uiState.darkTheme == 0,
                            onClick = {
                                onEvent(SettingsUiEvent.UpdateDarkTheme(0))
                                onDismissRequest() },
                            role = Role.RadioButton
                        )
                        .padding(horizontal = 12.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    RadioButton(
                        selected = uiState.darkTheme == 0,
                        onClick = null
                    )
                    Text(
                        text = stringResource(localesR.string.follow_system),
                        style = MaterialTheme.typography.bodyLarge,
                        modifier = Modifier.padding(start = 12.dp)
                    )
                }

                Row(
                    Modifier
                        .fillMaxWidth()
                        .height(46.dp)
                        .selectable(
                            selected = uiState.darkTheme == 1,
                            onClick = { onEvent(SettingsUiEvent.UpdateDarkTheme(1))
                                        onDismissRequest()
                                      },
                            role = Role.RadioButton
                        )
                        .padding(horizontal = 12.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    RadioButton(
                        selected = uiState.darkTheme == 1,
                        onClick = null
                    )
                    Text(
                        text = stringResource(localesR.string.light),
                        style = MaterialTheme.typography.bodyLarge,
                        modifier = Modifier.padding(start = 12.dp)
                    )
                }

                Row(
                    Modifier
                        .fillMaxWidth()
                        .height(46.dp)
                        .selectable(
                            selected = uiState.darkTheme == 2,
                            onClick = {
                                onEvent(SettingsUiEvent.UpdateDarkTheme(2))
                                onDismissRequest() },
                            role = Role.RadioButton
                        )
                        .padding(horizontal = 12.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    RadioButton(
                        selected = uiState.darkTheme == 2,
                        onClick = null
                    )
                    Text(
                        text = stringResource(localesR.string.dark),
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
private fun ThemeSelectorDialogPreview(){
    ThemeSelectorDialog(
        uiState = SettingsUiState(),
        onEvent = {},
        onDismissRequest = {}
    )
}