/* *
* Lumolight :: Open-source program under GPL-3.0 :: Copyright - BitMavrick :: https://github.com/BitMavrick
* */

package com.bitmavrick.lumolight.ui.screen.setting

import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme
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
import androidx.compose.material.icons.outlined.ColorLens
import androidx.compose.material.icons.outlined.DarkMode
import androidx.compose.material.icons.outlined.LightMode
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.bitmavrick.lumolight.ui.theme.LumolightTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AppearanceScreen(
    settingUiState: SettingUiState,
    settingUiEvent: (SettingUiEvent) -> Unit,
    onClickBack: () -> Unit
) {
    val scrollBehavior = TopAppBarDefaults.exitUntilCollapsedScrollBehavior()

    if(settingUiState.showThemeDialog){
        AlertDialog(
            title = {
                Text(text = "Select Theme")
            },

            text = {
                Column(Modifier.selectableGroup()) {
                    Row(
                        Modifier
                            .fillMaxWidth()
                            .height(46.dp)
                            .selectable(
                                selected = (settingUiState.appearance == Appearance.DEFAULT),
                                onClick = { settingUiEvent(SettingUiEvent.UpdateAppearance(Appearance.DEFAULT)) },
                                role = Role.RadioButton
                            )
                            .padding(horizontal = 12.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        RadioButton(
                            selected = (settingUiState.appearance == Appearance.DEFAULT),
                            onClick = null
                        )
                        Text(
                            text = "Follow System",
                            style = MaterialTheme.typography.bodyLarge,
                            modifier = Modifier.padding(start = 12.dp)
                        )
                    }

                    Row(
                        Modifier
                            .fillMaxWidth()
                            .height(46.dp)
                            .selectable(
                                selected = (settingUiState.appearance == Appearance.LIGHT),
                                onClick = { settingUiEvent(SettingUiEvent.UpdateAppearance(Appearance.LIGHT)) },
                                role = Role.RadioButton
                            )
                            .padding(horizontal = 12.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        RadioButton(
                            selected = (settingUiState.appearance == Appearance.LIGHT),
                            onClick = null
                        )
                        Text(
                            text = "Light",
                            style = MaterialTheme.typography.bodyLarge,
                            modifier = Modifier.padding(start = 12.dp)
                        )
                    }

                    Row(
                        Modifier
                            .fillMaxWidth()
                            .height(46.dp)
                            .selectable(
                                selected = (settingUiState.appearance == Appearance.DARK),
                                onClick = { settingUiEvent(SettingUiEvent.UpdateAppearance(Appearance.DARK)) },
                                role = Role.RadioButton
                            )
                            .padding(horizontal = 12.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        RadioButton(
                            selected = (settingUiState.appearance == Appearance.DARK),
                            onClick = null
                        )
                        Text(
                            text = "Dark",
                            style = MaterialTheme.typography.bodyLarge,
                            modifier = Modifier.padding(start = 12.dp)
                        )
                    }
                }
            },

            onDismissRequest = { settingUiEvent(SettingUiEvent.UpdateThemeDialog(false)) },
            confirmButton = {},
            dismissButton = {
                TextButton(
                    onClick = {
                        settingUiEvent(SettingUiEvent.UpdateThemeDialog(false))
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
                        subTitle = getAppearanceName(settingUiState),
                        leadingIcon = getProperThemeIcon(settingUiState),
                        onClick = { settingUiEvent(SettingUiEvent.UpdateThemeDialog(true)) }
                    )
                }

                /* For the future update

                item {
                    SettingsItem(
                        title = "OLED dark",
                        subTitle = "Enable pure black background",
                        leadingIcon = Icons.Outlined.SettingsBrightness,
                        showSwitch = true,
                        switchChecked = false,
                        onClick = {}
                    )
                }

                 */

                if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.S){
                    item {
                        SettingsItem(
                            title = "Dynamic color",
                            subTitle = "Color based on your wallpaper accent",
                            leadingIcon = Icons.Outlined.ColorLens,
                            showSwitch = true,
                            switchChecked = settingUiState.dynamicTheme,
                            onClick = { settingUiEvent(SettingUiEvent.UpdateDynamicTheme(!settingUiState.dynamicTheme)) }
                        )
                    }
                }
            }
        }
    )
}

private fun getAppearanceName(
    settingUiState: SettingUiState
): String {
    return when(settingUiState.appearance){
        Appearance.DEFAULT -> {
            "Follow System"
        }
        Appearance.LIGHT -> {
            "Light"
        }
        Appearance.DARK -> {
            "Dark"
        }
    }
}


@Composable
private fun getProperThemeIcon(
    settingUiState: SettingUiState
) : ImageVector {
    return when(settingUiState.appearance){
        Appearance.DEFAULT -> {
            if(isSystemInDarkTheme()){
                Icons.Outlined.DarkMode
            }else{
                Icons.Outlined.LightMode
            }
        }

        Appearance.LIGHT -> {
            Icons.Outlined.LightMode
        }

        Appearance.DARK -> {
            Icons.Outlined.DarkMode
        }
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun AppearanceScreenPreview() {
    LumolightTheme {
        AppearanceScreen(
            settingUiState = SettingUiState(),
            settingUiEvent = {},
            onClickBack = {}
        )
    }
}