package com.bitmavrick.lumolight.ui.screen.setting

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Code
import androidx.compose.material.icons.outlined.Commit
import androidx.compose.material.icons.outlined.Cookie
import androidx.compose.material.icons.outlined.PrivacyTip
import androidx.compose.material.icons.outlined.Token
import androidx.compose.material.icons.outlined.Widgets
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import com.bitmavrick.lumolight.ui.theme.LumolightTheme
import com.bitmavrick.lumolight.util.AppConstants
import com.bitmavrick.lumolight.util.getAppVersion
import com.bitmavrick.lumolight.util.openUrl

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AboutScreen(
    onClickBack: () -> Unit
) {
    val context = LocalContext.current
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
            ) {
                item {
                    SettingsItem(
                        title = "App name",
                        subTitle = AppConstants.APP_NAME,
                        leadingIcon = Icons.Outlined.Widgets,
                        onClick = {}
                    )
                }

                item {
                    SettingsItem(
                        title = "Version",
                        subTitle = getAppVersion(context), // Works only release mode
                        leadingIcon = Icons.Outlined.Commit,
                        onClick = {}
                    )
                }

                item {
                    SettingsItem(
                        title = "Package type",
                        subTitle = AppConstants.APP_PRODUCTION_MODE.name.lowercase(),
                        leadingIcon = Icons.Outlined.Cookie,
                        onClick = {}
                    )
                }

                item {
                    SettingsItem(
                        title = "Developer",
                        subTitle = AppConstants.DEVELOPER,
                        leadingIcon = Icons.Outlined.Code,
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
                        subTitle = AppConstants.REPOSITORY,
                        leadingIcon = Icons.Outlined.Token,
                        onClick = {
                            openUrl(context, AppConstants.REPOSITORY)
                        }
                    )
                }

                item {
                    SettingsItem(
                        title = "Privacy policy",
                        subTitle = AppConstants.PRIVACY_POLICY,
                        leadingIcon = Icons.Outlined.PrivacyTip,
                        onClick = {
                            openUrl(context, AppConstants.PRIVACY_POLICY)
                        }
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
            onClickBack = {}
        )
    }
}