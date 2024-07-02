/* *
* Lumolight :: Open-source program under GPL-3.0 :: Copyright - BitMavrick :: https://github.com/BitMavrick
* */

package com.bitmavrick.lumolight.ui.screen.home

import android.content.ActivityNotFoundException
import android.content.Context
import android.content.Intent
import android.net.Uri
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.outlined.Comment
import androidx.compose.material.icons.outlined.Info
import androidx.compose.material.icons.outlined.MoreVert
import androidx.compose.material.icons.outlined.PrivacyTip
import androidx.compose.material.icons.outlined.Settings
import androidx.compose.material.icons.outlined.Sos
import androidx.compose.material.icons.outlined.Spoke
import androidx.compose.material.icons.outlined.StarRate
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.TabPosition
import androidx.compose.material3.TabRow
import androidx.compose.material3.TabRowDefaults
import androidx.compose.material3.TabRowDefaults.tabIndicatorOffset
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.surfaceColorAtElevation
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.core.content.ContextCompat.startActivity
import com.bitmavrick.lumolight.R
import com.bitmavrick.lumolight.util.openUrl

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreenTopBar(
    showSosButton : Boolean,
    onClickSos : () -> Unit,
    onClickSettings: () -> Unit,
    onClickAbout : () -> Unit
) {
    val context = LocalContext.current
    var expanded by remember { mutableStateOf(false) }

    TopAppBar(
        title = {
            Text(
                text = context.getString(R.string.app_name),
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
                fontWeight = FontWeight.Bold
            )
        },
        actions = {

            if(showSosButton){
                IconButton(
                    onClick = { onClickSos() }
                ) {
                    Icon(
                        imageVector = Icons.Outlined.Sos,
                        contentDescription = context.getString(R.string.sos_icon_description)
                    )
                }
            }

            IconButton(
                onClick = { onClickSettings() }
            ) {
                Icon(
                    imageVector = Icons.Outlined.Settings,
                    contentDescription = context.getString(R.string.settings_icon_description)
                )
            }

            IconButton(
                onClick = { expanded = true }
            ) {
                Icon(
                    imageVector = Icons.Outlined.MoreVert,
                    contentDescription = context.getString(R.string.more_icon_description)
                )
            }


            DropdownMenu(
                expanded = expanded,
                onDismissRequest = { expanded = false }
            ) {

                DropdownMenuItem(
                    text = {
                        Text(
                            text = context.getString(R.string.about_title)
                        )
                    },
                    onClick = { onClickAbout() },
                    leadingIcon = {
                        Icon(imageVector = Icons.Outlined.Info,
                            contentDescription = context.getString(R.string.info_icon_description)
                        )
                    }
                )


                DropdownMenuItem(
                    text = {
                        Text(
                            text = context.getString(R.string.report_issue)
                        )
                    },
                    onClick = {
                        openUrl(
                            context = context,
                            url = "https://github.com/BitMavrick/Lumolight/issues"
                        )
                    },
                    leadingIcon = {
                        Icon(imageVector = Icons.AutoMirrored.Outlined.Comment,
                            contentDescription = context.getString(R.string.comment_icon_description)
                        )
                    }
                )

                DropdownMenuItem(
                    text = {
                        Text(
                            text = context.getString(R.string.privacy_policy)
                        )
                    },
                    onClick = {
                        openUrl(
                            context = context,
                            url = "https://bitmavrick.github.io/privacy-policy/lumolight.html"
                        )
                    },
                    leadingIcon = {
                        Icon(imageVector = Icons.Outlined.PrivacyTip,
                            contentDescription = context.getString(R.string.privacy_icon_description)
                        )
                    }
                )

                HorizontalDivider()

                DropdownMenuItem(
                    text = {
                        Text(
                            text = context.getString(R.string.rate_lumolight)
                        )
                    },
                    onClick = {
                        openAppInPlayStore(context)
                    },
                    leadingIcon = {
                        Icon(imageVector = Icons.Outlined.StarRate,
                            contentDescription = null
                        )
                    }
                )

                DropdownMenuItem(
                    text = {
                        Text(
                            text = context.getString(R.string.try_more_apps)
                        )
                    },
                    onClick = {
                        openUrl(
                            context = context,
                            url = "https://play.google.com/store/apps/dev?id=8579128568898304037"
                        )
                    },
                    leadingIcon = {
                        Icon(imageVector = Icons.Outlined.Spoke,
                            contentDescription = null
                        )
                    }
                )
            }
        }
    )
}


fun openAppInPlayStore(context: Context) {
    val uri = Uri.parse("market://details?id=" + context.packageName)
    val goToMarketIntent = Intent(Intent.ACTION_VIEW, uri)

    var flags = Intent.FLAG_ACTIVITY_NO_HISTORY or Intent.FLAG_ACTIVITY_MULTIPLE_TASK
    flags =
        flags or Intent.FLAG_ACTIVITY_NEW_DOCUMENT
    goToMarketIntent.addFlags(flags)

    try {
        startActivity(context, goToMarketIntent, null)
    } catch (e: ActivityNotFoundException) {
        val intent = Intent(Intent.ACTION_VIEW,
            Uri.parse("http://play.google.com/store/apps/details?id=" + context.packageName))

        startActivity(context, intent, null)
    }
}


@Composable
fun CustomTabRow(
    selectedTabIndex: Int,
    modifier: Modifier = Modifier,
    containerColor: Color = MaterialTheme.colorScheme.surfaceColorAtElevation(TabRowElevation),
    contentColor: Color = MaterialTheme.colorScheme.primary,
    indicator: @Composable (tabPositions: List<TabPosition>) -> Unit = @Composable { tabPositions ->
        TabRowDefaults.SecondaryIndicator(
            modifier = Modifier.tabIndicatorOffset(tabPositions[selectedTabIndex])
        )
    },
    divider: @Composable () -> Unit = {},
    tabs: @Composable () -> Unit
) {
    TabRow(
        selectedTabIndex = selectedTabIndex,
        modifier = modifier,
        containerColor = containerColor,
        contentColor = contentColor,
        indicator = indicator,
        divider = divider,
        tabs = tabs
    )
}

private val TabRowElevation = 0.dp