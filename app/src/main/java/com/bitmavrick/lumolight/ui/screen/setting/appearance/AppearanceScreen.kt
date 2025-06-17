 /* *
* Lumolight :: Open-source program under GPL-3.0 :: Copyright - BitMavrick :: https://github.com/BitMavrick
* */

 package com.bitmavrick.lumolight.ui.screen.setting.appearance

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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.bitmavrick.lumolight.R
import com.bitmavrick.lumolight.ui.screen.setting.SettingScreenTopBar
import com.bitmavrick.lumolight.ui.screen.setting.SettingsItem
import com.bitmavrick.lumolight.ui.theme.LumolightTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AppearanceScreen(
    themeUiState: ThemeUiState,
    themeUiEvent: (ThemeUiEvent) -> Unit,
    onClickBack: () -> Unit
) {
    val scrollBehavior = TopAppBarDefaults.exitUntilCollapsedScrollBehavior()
    val context = LocalContext.current

    if(themeUiState.showThemeDialog){
        AlertDialog(
            title = {
                Text(
                    text = context.getString(R.string.select_theme)
                )
            },

            text = {
                Column(Modifier.selectableGroup()) {
                    Row(
                        Modifier
                            .fillMaxWidth()
                            .height(46.dp)
                            .selectable(
                                selected = (themeUiState.appearance == Appearance.DEFAULT),
                                onClick = { themeUiEvent(ThemeUiEvent.UpdateAppearance(Appearance.DEFAULT)) },
                                role = Role.RadioButton
                            )
                            .padding(horizontal = 12.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        RadioButton(
                            selected = (themeUiState.appearance == Appearance.DEFAULT),
                            onClick = null
                        )
                        Text(
                            text = context.getString(R.string.follow_system),
                            style = MaterialTheme.typography.bodyLarge,
                            modifier = Modifier.padding(start = 12.dp)
                        )
                    }

                    Row(
                        Modifier
                            .fillMaxWidth()
                            .height(46.dp)
                            .selectable(
                                selected = (themeUiState.appearance == Appearance.LIGHT),
                                onClick = { themeUiEvent(ThemeUiEvent.UpdateAppearance(Appearance.LIGHT)) },
                                role = Role.RadioButton
                            )
                            .padding(horizontal = 12.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        RadioButton(
                            selected = (themeUiState.appearance == Appearance.LIGHT),
                            onClick = null
                        )
                        Text(
                            text = context.getString(R.string.light),
                            style = MaterialTheme.typography.bodyLarge,
                            modifier = Modifier.padding(start = 12.dp)
                        )
                    }

                    Row(
                        Modifier
                            .fillMaxWidth()
                            .height(46.dp)
                            .selectable(
                                selected = (themeUiState.appearance == Appearance.DARK),
                                onClick = { themeUiEvent(ThemeUiEvent.UpdateAppearance(Appearance.DARK)) },
                                role = Role.RadioButton
                            )
                            .padding(horizontal = 12.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        RadioButton(
                            selected = (themeUiState.appearance == Appearance.DARK),
                            onClick = null
                        )
                        Text(
                            text = context.getString(R.string.dark),
                            style = MaterialTheme.typography.bodyLarge,
                            modifier = Modifier.padding(start = 12.dp)
                        )
                    }
                }
            },

            onDismissRequest = { themeUiEvent(ThemeUiEvent.UpdateThemeDialog(false)) },
            confirmButton = {},
            dismissButton = {
                TextButton(
                    onClick = {
                        themeUiEvent(ThemeUiEvent.UpdateThemeDialog(false))
                    }
                ) {
                    Text(
                        text = context.getString(R.string.dismiss)
                    )
                }
            }
        )
    }

    Scaffold (
        modifier = Modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = {
            SettingScreenTopBar(
                title = context.getString(R.string.appearance),
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
                        title = context.getString(R.string.app_theme),
                        subTitle = getAppearanceName(themeUiState),
                        leadingIcon = getProperThemeIcon(themeUiState),
                        iconDescription = context.getString(R.string.app_theme_icon_description),
                        onClick = { themeUiEvent(ThemeUiEvent.UpdateThemeDialog(true)) }
                    )
                }

                if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.S){
                    item {
                        SettingsItem(
                            title = context.getString(R.string.dynamic_color_title),
                            subTitle = context.getString(R.string.dynamic_color_description),
                            leadingIcon = Icons.Outlined.ColorLens,
                            iconDescription = context.getString(R.string.dynamic_color_icon_description),
                            showSwitch = true,
                            switchChecked = themeUiState.dynamicTheme,
                            onClick = { themeUiEvent(ThemeUiEvent.UpdateDynamicTheme(!themeUiState.dynamicTheme)) }
                        )
                    }
                }

                item {
                    SettingsItem(
                        title = context.getString(R.string.oled_dark_title),
                        subTitle = context.getString(R.string.oled_dark_description),
                        leadingIcon = Icons.Outlined.SettingsBrightness,
                        iconDescription = context.getString(R.string.oled_dark_icon_description),
                        showSwitch = true,
                        switchChecked = themeUiState.oledTheme,
                        onClick = { themeUiEvent(ThemeUiEvent.UpdateOledTheme(!themeUiState.oledTheme)) }
                    )
                }
            }
        }
    )
}

private fun getAppearanceName(
    themeUiState: ThemeUiState
): String {
    return when(themeUiState.appearance){
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
    themeUiState: ThemeUiState
) : ImageVector {
    return when(themeUiState.appearance){
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
            themeUiState = ThemeUiState(),
            themeUiEvent = {},
            onClickBack = {}
        )
    }
}