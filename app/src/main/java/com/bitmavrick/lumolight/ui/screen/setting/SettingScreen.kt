package com.bitmavrick.lumolight.ui.screen.setting

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.ColorLens
import androidx.compose.material.icons.outlined.DynamicForm
import androidx.compose.material.icons.outlined.Info
import androidx.compose.material.icons.outlined.Sos
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.tooling.preview.Preview
import com.bitmavrick.lumolight.ui.theme.LumolightTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SettingScreen(
    settingUiState: SettingUiState,
    settingOnEvent: (SettingUiEvent) -> Unit,
    onClickBack: () -> Unit,
    onClickAbout: () -> Unit,
    onClickAppearance: () -> Unit
) {
    val scrollBehavior = TopAppBarDefaults.exitUntilCollapsedScrollBehavior()
    Scaffold (
        modifier = Modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = {
            SettingScreenTopBar(
                title = "Settings",
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
                        title = "Appearance",
                        subTitle = "Configure application appearance",
                        leadingIcon = Icons.Outlined.ColorLens,
                        onClick = { onClickAppearance() }
                    )
                }

                item {
                    SettingsItem(
                        title = "Save quick action",
                        subTitle = "Save the last selected status",
                        leadingIcon = Icons.Outlined.DynamicForm,
                        showSwitch = true,
                        switchChecked = settingUiState.saveQuickAction,
                        onClick = {
                            settingOnEvent(SettingUiEvent.UpdateSaveQuickActionSetting(!settingUiState.saveQuickAction))
                        }
                    )
                }

                item {
                    SettingsItem(
                        title = "Show SOS button",
                        subTitle = "Show SOS button on the top bar",
                        leadingIcon = Icons.Outlined.Sos,
                        showSwitch = true,
                        switchChecked = settingUiState.showSosButton,
                        onClick = {
                            settingOnEvent(SettingUiEvent.UpdateShowSosButtonPreference(!settingUiState.showSosButton))
                        }
                    )
                }

                item {
                    SettingsItem(
                        title = "About",
                        subTitle = "About application",
                        leadingIcon = Icons.Outlined.Info,
                        onClick = { onClickAbout() }
                    )
                }
            }
        }
    )
}


@Preview(showBackground = true, showSystemUi = true)
@Composable
fun SettingScreenPreview() {
    LumolightTheme {
        SettingScreen(
            settingUiState = SettingUiState(),
            settingOnEvent = {},
            onClickBack = {},
            onClickAbout = {},
            onClickAppearance = {}
        )
    }
}