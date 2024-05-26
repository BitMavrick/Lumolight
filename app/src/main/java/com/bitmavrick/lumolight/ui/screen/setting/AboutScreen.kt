package com.bitmavrick.lumolight.ui.screen.setting

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.ColorLens
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
fun AboutScreen(
    settingUiState: SettingUiState,
    settingOnEvent: (SettingUiEvent) -> Unit,
    onClickBack: () -> Unit
) {
    val scrollBehavior = TopAppBarDefaults.exitUntilCollapsedScrollBehavior()

    Scaffold (

        modifier = Modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = {
            SettingScreenTopBar(
                title = "About",
                scrollBehavior = scrollBehavior,
                onClickBack = { onClickBack() }
            )
        },

        content = { innerPadding ->
            LazyColumn(
                modifier = Modifier.fillMaxSize(),
                contentPadding = innerPadding,
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                item {
                    SettingsItem(
                        title = "App name",
                        subTitle = "Lumolight",
                        leadingIcon = Icons.Outlined.ColorLens,
                        onClick = {}
                    )
                }

                item {
                    SettingsItem(
                        title = "Version",
                        subTitle = "1.0.0",
                        leadingIcon = Icons.Outlined.ColorLens,
                        onClick = {}
                    )
                }

                item {
                    SettingsItem(
                        title = "Developer",
                        subTitle = "BitMavrick",
                        leadingIcon = Icons.Outlined.ColorLens,
                        onClick = {}
                    )
                }

                item {
                    SettingSubDividerText(
                        subtitle = "Links"
                    )
                }

                item {
                    SettingsItem(
                        title = "Repository",
                        subTitle = "github.com/lumolight",
                        leadingIcon = Icons.Outlined.ColorLens,
                        onClick = {}
                    )
                }

                item {
                    SettingsItem(
                        title = "Privacy policy",
                        subTitle = "github.com/lumolight",
                        leadingIcon = Icons.Outlined.ColorLens,
                        onClick = {}
                    )
                }
            }
        }
    )
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun AboutScreenPreview() {
    LumolightTheme {
        AboutScreen(
            settingUiState = SettingUiState(),
            settingOnEvent = {},
            onClickBack = {}
        )
    }
}