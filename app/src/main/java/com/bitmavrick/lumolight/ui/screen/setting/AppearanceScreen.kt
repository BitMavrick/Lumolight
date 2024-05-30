package com.bitmavrick.lumolight.ui.screen.setting

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.selection.selectable
import androidx.compose.foundation.selection.selectableGroup
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.DarkMode
import androidx.compose.material.icons.outlined.SettingsBrightness
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.bitmavrick.lumolight.ui.theme.LumolightTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AppearanceScreen(
    settingUiState: SettingUiState,
    settingOnEvent: (SettingUiEvent) -> Unit,
    onClickBack: () -> Unit
) {
    val scrollBehavior = TopAppBarDefaults.exitUntilCollapsedScrollBehavior()

    if(settingUiState.showThemeDialog){
        AlertDialog(
            title = {
                Text(text = "Select Theme")
            },

            text = {
                val radioOptions = listOf("Follow System", "Light", "Dark")
                val (selectedOption, onOptionSelected) = remember { mutableStateOf(radioOptions[0]) }
                Column(Modifier.selectableGroup()) {
                    radioOptions.forEach { text ->
                        Row(
                            Modifier
                                .fillMaxWidth()
                                .height(46.dp)
                                .selectable(
                                    selected = (text == selectedOption),
                                    onClick = { onOptionSelected(text) },
                                    role = Role.RadioButton
                                )
                                .padding(horizontal = 12.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            RadioButton(
                                selected = (text == selectedOption),
                                onClick = null
                            )
                            Text(
                                text = text,
                                style = MaterialTheme.typography.bodyLarge,
                                modifier = Modifier.padding(start = 12.dp)
                            )
                        }
                    }
                }

            },
            onDismissRequest = { settingOnEvent(SettingUiEvent.UpdateThemeDialog(false)) },
            confirmButton = { /*TODO*/ },
            dismissButton = {
                TextButton(
                    onClick = {
                        settingOnEvent(SettingUiEvent.UpdateThemeDialog(false))
                    }
                ) {
                    Text(text = "Dismiss")
                }
            }
        )
    }

    Scaffold (
        modifier = Modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = {
            SettingScreenTopBar(
                title = "Appearance",
                scrollBehavior = scrollBehavior,
                onClickBack = { onClickBack() }
            )
        },

        content = { innerPadding ->
            LazyColumn(
                modifier = Modifier.fillMaxSize(),
                contentPadding = innerPadding,
            ) {
                item {
                    SettingsItem(
                        title = "App theme",
                        subTitle = "Follow System",
                        leadingIcon = Icons.Outlined.DarkMode,
                        onClick = { settingOnEvent(SettingUiEvent.UpdateThemeDialog(true)) }
                    )
                }

                item {
                    SettingsItem(
                        title = "OLED theme",
                        subTitle = "Enable pure black background",
                        leadingIcon = Icons.Outlined.SettingsBrightness,
                        showSwitch = true,
                        switchChecked = false,
                        onClick = {}
                    )
                }
            }
        }
    )
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun AppearanceScreenPreview() {
    LumolightTheme {
        AppearanceScreen(
            settingUiState = SettingUiState(
                showThemeDialog = true
            ),
            settingOnEvent = {},
            onClickBack = {}
        )
    }
}