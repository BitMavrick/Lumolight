package com.bitmavrick.lumolight.ui.screen.setting

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.DarkMode
import androidx.compose.material.icons.outlined.SettingsBrightness
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
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
                verticalArrangement = Arrangement.spacedBy(4.dp)
            ) {
                item {
                    SettingsItem(
                        title = "App theme",
                        subTitle = "Follow System",
                        leadingIcon = Icons.Outlined.DarkMode,
                        onClick = {}
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
            settingUiState = SettingUiState(),
            settingOnEvent = {},
            onClickBack = {}
        )
    }
}