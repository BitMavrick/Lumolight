/* *
* Lumolight :: Open-source program under GPL-3.0 :: Copyright - BitMavrick :: https://github.com/BitMavrick
* */

package com.bitmavrick.lumolight.ui.screen.setting

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Code
import androidx.compose.material.icons.outlined.Commit
import androidx.compose.material.icons.outlined.Cookie
import androidx.compose.material.icons.outlined.Groups
import androidx.compose.material.icons.outlined.Person
import androidx.compose.material.icons.outlined.PrivacyTip
import androidx.compose.material.icons.outlined.Widgets
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
                title = context.getString(R.string.about_title),
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
                        title = context.getString(R.string.app_name_title),
                        subTitle = AppConstants.APP_NAME,
                        leadingIcon = Icons.Outlined.Widgets,
                        onClick = {}
                    )
                }

                item {
                    SettingsItem(
                        title = context.getString(R.string.version_title),
                        subTitle = getAppVersion(context), // shows only release mode
                        leadingIcon = Icons.Outlined.Commit,
                        onClick = {}
                    )
                }

                item {
                    SettingsItem(
                        title = context.getString(R.string.package_type_title),
                        subTitle = AppConstants.APP_PRODUCTION_MODE.name.lowercase(),
                        leadingIcon = Icons.Outlined.Cookie,
                        onClick = {}
                    )
                }

                item {
                    SettingsItem(
                        title = context.getString(R.string.developer_title),
                        subTitle = AppConstants.DEVELOPER,
                        leadingIcon = Icons.Outlined.Person,
                        onClick = {}
                    )
                }

                item {
                    SettingSubDividerText(
                        subtitle = context.getString(R.string.links)
                    )
                }

                item {
                    SettingsItem(
                        title = context.getString(R.string.repository_title),
                        subTitle = AppConstants.REPOSITORY,
                        leadingIcon = Icons.Outlined.Code,
                        onClick = {
                            openUrl(context, AppConstants.REPOSITORY)
                        }
                    )
                }

                item {
                    SettingsItem(
                        title = context.getString(R.string.reddit_community),
                        subTitle = AppConstants.REDDIT,
                        leadingIcon = Icons.Outlined.Groups,
                        onClick = {
                            openUrl(context, AppConstants.REDDIT)
                        }
                    )
                }

                item {
                    SettingsItem(
                        title = context.getString(R.string.privacy_policy),
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