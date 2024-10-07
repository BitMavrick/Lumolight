/* *
* Lumolight :: Open-source program under GPL-3.0 :: Copyright - BitMavrick :: https://github.com/BitMavrick
* */

package com.bitmavrick.lumolight.ui.screen.setting

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.ColorLens
import androidx.compose.material.icons.outlined.DynamicForm
import androidx.compose.material.icons.outlined.Info
import androidx.compose.material.icons.outlined.Sos
import androidx.compose.material.icons.outlined.Vibration
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import com.bitmavrick.lumolight.R
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
    val context = LocalContext.current
    Scaffold (
        modifier = Modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = {
            SettingScreenTopBar(
                title = context.getString(R.string.settings),
                scrollBehavior = scrollBehavior,
                onClickBack = { onClickBack() },
                backIconDescription = context.getString(R.string.navigation_icon_description)
            )
        },
        content = { innerPadding ->
            LazyColumn(
                modifier = Modifier.fillMaxSize(),
                contentPadding = innerPadding,
            ) {
                item {
                    SettingsItem(
                        title = context.getString(R.string.appearance),
                        subTitle = context.getString(R.string.appearance_description),
                        leadingIcon = Icons.Outlined.ColorLens,
                        iconDescription = context.getString(R.string.appearance_icon),
                        onClick = { onClickAppearance() }
                    )
                }

                item {
                    SettingsItem(
                        title = context.getString(R.string.save_quick_action_title),
                        subTitle = context.getString(R.string.save_quick_action_description),
                        leadingIcon = Icons.Outlined.DynamicForm,
                        iconDescription = context.getString(R.string.save_quick_action_icon_description),
                        showSwitch = true,
                        switchChecked = settingUiState.saveQuickAction,
                        onClick = {
                            settingOnEvent(SettingUiEvent.UpdateSaveQuickActionSetting(!settingUiState.saveQuickAction))
                        }
                    )
                }

                item {
                    SettingsItem(
                        title = context.getString(R.string.show_sos_button_title),
                        subTitle = context.getString(R.string.show_sos_button_description),
                        leadingIcon = Icons.Outlined.Sos,
                        iconDescription = context.getString(R.string.sos_icon_description),
                        showSwitch = true,
                        switchChecked = settingUiState.showSosButton,
                        onClick = {
                            settingOnEvent(SettingUiEvent.UpdateShowSosButtonPreference(!settingUiState.showSosButton))
                        }
                    )
                }

                item {
                    SettingsItem(
                        title = context.getString(R.string.enable_haptic_title),
                        subTitle = context.getString(R.string.enable_haptic_description),
                        leadingIcon = Icons.Outlined.Vibration,
                        iconDescription = context.getString(R.string.vibration_icon_description),
                        showSwitch = true,
                        switchChecked = settingUiState.hapticStatus,
                        onClick = {
                            settingOnEvent(SettingUiEvent.UpdateHapticStatus(!settingUiState.hapticStatus))
                        }
                    )
                }

                item {
                    SettingsItem(
                        title = context.getString(R.string.about_title),
                        subTitle = context.getString(R.string.about_description),
                        leadingIcon = Icons.Outlined.Info,
                        iconDescription = context.getString(R.string.info_icon_description),
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