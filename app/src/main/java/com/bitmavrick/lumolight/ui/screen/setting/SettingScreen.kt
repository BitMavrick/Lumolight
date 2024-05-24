package com.bitmavrick.lumolight.ui.screen.setting

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.outlined.ArrowBack
import androidx.compose.material.icons.outlined.ColorLens
import androidx.compose.material.icons.outlined.DynamicForm
import androidx.compose.material.icons.outlined.Info
import androidx.compose.material.icons.outlined.Sos
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LargeTopAppBar
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.TopAppBarScrollBehavior
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.bitmavrick.lumolight.ui.theme.LumolightTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SettingScreen(
    settingUiState: SettingUiState,
    settingOnEvent: (SettingUiEvent) -> Unit,
    onClickBack: () -> Unit
) {
    val scrollBehavior = TopAppBarDefaults.exitUntilCollapsedScrollBehavior()
    Scaffold (
        modifier = Modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = {
            SettingScreenTopBar(
                scrollBehavior = scrollBehavior,
                onClickBack = { onClickBack() }
            )
        },
        content = { innerPadding ->
            LazyColumn(
                contentPadding = innerPadding,
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                item {
                    SettingsItem(
                        title = "Appearance",
                        subTitle = "Configure application appearance",
                        leadingIcon = Icons.Outlined.ColorLens,
                        onClick = {}
                    )
                }

                item {
                    SettingsItem(
                        title = "Save Quick Action",
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
                        title = "Show SOS Button",
                        subTitle = "Show SOS button on the top bar",
                        leadingIcon = Icons.Outlined.Sos,
                        showSwitch = true,
                        switchChecked = false,
                        onClick = {}
                    )
                }

                item {
                    SettingsItem(
                        title = "About",
                        subTitle = "About application",
                        leadingIcon = Icons.Outlined.Info,
                        onClick = {}
                    )
                }
            }
        }
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SettingScreenTopBar(
    scrollBehavior : TopAppBarScrollBehavior,
    onClickBack: () -> Unit
) {
    LargeTopAppBar(
        title = {
            Text(
                text ="Settings",
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
        },
        navigationIcon = {
            IconButton(onClick = { onClickBack() }) {
                Icon(
                    imageVector = Icons.AutoMirrored.Outlined.ArrowBack,
                    contentDescription = "Localized description"
                )
            }
        },
        scrollBehavior = scrollBehavior
    )
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun SettingScreenPreview() {
    LumolightTheme {
        SettingScreen(
            settingUiState = SettingUiState(),
            settingOnEvent = {},
            onClickBack = {}
        )
    }
}