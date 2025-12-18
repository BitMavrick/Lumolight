package com.bitmavrick.settings

import android.content.ActivityNotFoundException
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.provider.Settings
import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.clickable
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.AdsClick
import androidx.compose.material.icons.outlined.Android
import androidx.compose.material.icons.outlined.Commit
import androidx.compose.material.icons.outlined.Contrast
import androidx.compose.material.icons.outlined.DarkMode
import androidx.compose.material.icons.outlined.Feedback
import androidx.compose.material.icons.outlined.FormatPaint
import androidx.compose.material.icons.outlined.LightMode
import androidx.compose.material.icons.outlined.Shield
import androidx.compose.material.icons.outlined.StarRate
import androidx.compose.material.icons.outlined.Translate
import androidx.compose.material.icons.rounded.Settings
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FilledTonalButton
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.dp
import androidx.core.net.toUri
import com.bitmavrick.settings.components.SectionTitle
import com.bitmavrick.settings.components.SettingItem
import com.bitmavrick.settings.components.SettingsSection
import com.bitmavrick.settings.components.SettingsSectionDivider
import com.bitmavrick.settings.components.ThemeSelectorDialog
import com.bitmavrick.theme.LumolightTheme
import com.bitmavrick.locales.R as localesR

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SettingsScreen(
    uiState: SettingsUiState,
    onEvent: (SettingsUiEvent) -> Unit
) {
    val context = LocalContext.current

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = {
            Column {
                TopAppBar(
                    title = {
                        Row(
                            verticalAlignment = Alignment.CenterVertically
                        ){
                            Icon(
                                imageVector = Icons.Rounded.Settings,
                                contentDescription = null,
                                tint = MaterialTheme.colorScheme.primary
                            )

                            Spacer(Modifier.padding(4.dp))

                            Text(
                                text = stringResource(localesR.string.settings),
                                fontWeight = FontWeight.SemiBold,
                                color = MaterialTheme.colorScheme.primary
                            )
                        }
                    }
                )

                HorizontalDivider(Modifier.fillMaxWidth())
            }
        }
    ) { innerPadding ->

        val showThemeSelectorDialog = remember { mutableStateOf(false) }

        LazyColumn(
            modifier = Modifier.fillMaxSize().padding(innerPadding)
        ) {
            item{
                Spacer(Modifier.padding(4.dp))
                SectionTitle(stringResource(localesR.string.appearance))

                SettingsSection {
                    SettingItem(
                        modifier = Modifier.clickable {
                            showThemeSelectorDialog.value = true
                        },
                        headlineText = stringResource(localesR.string.theme),
                        supportingText = when(uiState.darkTheme){
                            0 -> {
                                stringResource(localesR.string.follow_system)
                            }
                            1 -> {
                                stringResource(localesR.string.light)
                            }
                            2 -> {
                                stringResource(localesR.string.dark)
                            }
                            else -> {
                                stringResource(localesR.string.follow_system)
                            }
                        },
                        leadingContent = {
                            Icon(
                                imageVector = when(uiState.darkTheme){
                                    0 -> {
                                        if(isSystemInDarkTheme()){
                                            Icons.Outlined.DarkMode
                                        }else{
                                            Icons.Outlined.LightMode
                                        }
                                    }
                                    1 -> {
                                        Icons.Outlined.LightMode
                                    }
                                    2 -> {
                                        Icons.Outlined.DarkMode
                                    }
                                    else -> {
                                        Icons.Outlined.LightMode
                                    }
                                },
                                contentDescription = stringResource(localesR.string.theme)
                            )
                        }
                    )

                    SettingsSectionDivider()

                    SettingItem(
                        modifier = Modifier.clickable {
                            onEvent(SettingsUiEvent.UpdateDynamicTheme(!uiState.dynamicTheme))
                        },
                        headlineText = stringResource(localesR.string.dynamic_color),
                        supportingText = stringResource(localesR.string.dynamic_color_des),
                        leadingContent = {
                            Icon(
                                imageVector = Icons.Outlined.FormatPaint,
                                contentDescription = stringResource(localesR.string.dynamic_color),
                            )
                        },
                        trailingContent = {
                            Switch(
                                checked = uiState.dynamicTheme,
                                onCheckedChange = {
                                    onEvent(SettingsUiEvent.UpdateDynamicTheme(!uiState.dynamicTheme))
                                }
                            )
                        }
                    )

                    SettingsSectionDivider()

                    SettingItem(
                        modifier = Modifier.clickable {
                            onEvent(SettingsUiEvent.UpdateAmoledTheme(!uiState.amoledTheme))
                        },
                        headlineText = stringResource(localesR.string.oled_dark),
                        supportingText = stringResource(localesR.string.oled_dark_des),
                        leadingContent = {
                            Icon(
                                imageVector = Icons.Outlined.Contrast,
                                contentDescription = stringResource(localesR.string.oled_dark)
                            )
                        },
                        trailingContent = {
                            Switch(
                                checked = uiState.amoledTheme,
                                onCheckedChange = {
                                    onEvent(SettingsUiEvent.UpdateAmoledTheme(!uiState.amoledTheme))
                                }
                            )
                        }
                    )
                }
            }

            item{
                SectionTitle(stringResource(localesR.string.general))

                SettingsSection {
                    SettingItem(
                        modifier = Modifier.clickable {
                            try {
                                val intent = Intent(Settings.ACTION_APP_LOCALE_SETTINGS)
                                intent.data = Uri.fromParts(
                                    "package",
                                    context.packageName,
                                    null
                                )
                                context.startActivity(intent)
                            } catch (_: Exception) {
                                try {
                                    val intent =
                                        Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS)
                                    intent.data = Uri.fromParts(
                                        "package", context.packageName, null
                                    )
                                    context.startActivity(intent)
                                } catch (_: Exception) {
                                    // * There's noting we can do!
                                }
                            }
                        },
                        headlineText = stringResource(localesR.string.language),
                        supportingText = stringResource(localesR.string.language_des),
                        leadingContent = {
                            Icon(
                                imageVector = Icons.Outlined.Translate,
                                contentDescription = stringResource(localesR.string.language)
                            )
                        }
                    )
                }
            }

            item{
                SectionTitle(stringResource(localesR.string.about))

                SettingsSection {
                    SettingItem(
                        headlineText = stringResource(localesR.string.app_name_title),
                        supportingText = stringResource(localesR.string.app_name),
                        leadingContent = {
                            Icon(
                                imageVector = Icons.Outlined.Android,
                                contentDescription = stringResource(localesR.string.app_name_title)
                            )
                        }
                    )

                    SettingsSectionDivider()

                    SettingItem(
                        headlineText = stringResource(localesR.string.version),
                        supportingText = if(BuildConfig.DEBUG){
                            "Unknown"
                        }else {
                            getAppVersionName(context).toString() + " | FOSS"
                        },
                        leadingContent = {
                            Icon(
                                imageVector = Icons.Outlined.Commit,
                                contentDescription = stringResource(localesR.string.version)
                            )
                        }
                    )

                    SettingsSectionDivider()

                    SettingItem(
                        modifier = Modifier.clickable {
                            openUrl(
                                context, "https://bitmavrick.github.io/privacy-policy/lumolight.html"
                            )
                        },
                        headlineText = stringResource(localesR.string.privacy_policy),
                        supportingText = stringResource(localesR.string.privacy_policy_des),
                        leadingContent = {
                            Icon(
                                imageVector = Icons.Outlined.Shield,
                                contentDescription = stringResource(localesR.string.privacy_policy)
                            )
                        }
                    )
                }
            }

            item{
                SectionTitle(stringResource(localesR.string.support))

                SettingsSection {
                    SettingItem(
                        modifier = Modifier.clickable {
                            openUrl(
                                context,
                                "https://play.google.com/store/apps/details?id=com.bitmavrick.lumolight"
                            )
                        },
                        headlineText = stringResource(localesR.string.rate_us),
                        supportingText = stringResource(localesR.string.rate_us_des),
                        leadingContent = {
                            Icon(
                                imageVector = Icons.Outlined.StarRate,
                                contentDescription = stringResource(localesR.string.rate_us)
                            )
                        }
                    )

                    SettingsSectionDivider()

                    SettingItem(
                        modifier = Modifier.clickable {
                            openUrl(
                                context,
                                "https://play.google.com/store/apps/dev?id=8579128568898304037"
                            )
                        },
                        headlineText = stringResource(localesR.string.try_more_apps),
                        supportingText = stringResource(localesR.string.try_more_apps_des),
                        leadingContent = {
                            Icon(
                                imageVector = Icons.Outlined.AdsClick,
                                contentDescription = stringResource(localesR.string.try_more_apps)
                            )
                        }
                    )

                    SettingsSectionDivider()

                    SettingItem(
                        modifier = Modifier.clickable {
                            val intent = Intent(Intent.ACTION_SENDTO).apply {
                                data = "mailto:".toUri()
                                putExtra(Intent.EXTRA_EMAIL, arrayOf("hello.bitmavrick@gmail.com"))
                                putExtra(Intent.EXTRA_SUBJECT, "App Feedback (Lumolight)")                        }
                            try {
                                context.startActivity(Intent.createChooser(intent, "Send feedback"))
                            } catch (e: ActivityNotFoundException) {
                                Toast.makeText(context, "No email app found!", Toast.LENGTH_SHORT).show()
                                Log.e("SettingsScreen", "No email app found! log: $e")
                            }
                        },
                        headlineText = stringResource(localesR.string.feedback),
                        supportingText = stringResource(localesR.string.feedback_des),
                        leadingContent = {
                            Icon(
                                imageVector = Icons.Outlined.Feedback,
                                contentDescription = stringResource(localesR.string.feedback)
                            )
                        }
                    )
                }
            }

            item{
                Spacer(Modifier.padding(4.dp))
            }
        }

        if(showThemeSelectorDialog.value){
            ThemeSelectorDialog(
                uiState = uiState,
                onEvent = onEvent,
                onDismissRequest = {
                    showThemeSelectorDialog.value = false
                }
            )
        }
    }
}

fun openUrl(context: Context, url: String) {
    val intent = Intent(Intent.ACTION_VIEW, url.toUri())
    try {
        context.startActivity(intent)
    } catch (e: ActivityNotFoundException) {
        Toast.makeText(context, "No browser found", Toast.LENGTH_SHORT).show()
        Log.e("SettingsScreen", "Link opens failed! log: $e")
    }
}

fun getAppVersionName(context: Context): String? {
    return try {
        val packageInfo = context.packageManager.getPackageInfo(context.packageName, 0)
        packageInfo.versionName
    } catch (e: PackageManager.NameNotFoundException) {
        Log.e("SettingsScreen", "Failed to get app version name! log: $e")
        null
    }
}

@PreviewLightDark
@Composable
private fun SettingsScreenPreview(){
    LumolightTheme {
        SettingsScreen(
            uiState = SettingsUiState(),
            onEvent = {}
        )
    }
}