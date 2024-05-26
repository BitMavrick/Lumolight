package com.bitmavrick.lumolight.ui.screen.home

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.outlined.Comment
import androidx.compose.material.icons.outlined.Info
import androidx.compose.material.icons.outlined.MoreVert
import androidx.compose.material.icons.outlined.PrivacyTip
import androidx.compose.material.icons.outlined.Settings
import androidx.compose.material.icons.outlined.Share
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
import com.bitmavrick.lumolight.util.openUrl

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreenTopBar(
    showSosButton : Boolean,
    onClickSettings: () -> Unit,
    onClickAbout : () -> Unit
) {
    val context = LocalContext.current
    var expanded by remember { mutableStateOf(false) }

    TopAppBar(
        title = {
            Text(
                text = "Lumolight",
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
                fontWeight = FontWeight.Bold
            )
        },
        actions = {

            if(showSosButton){
                IconButton(
                    onClick = { /* TODO */ }
                ) {
                    Icon(
                        imageVector = Icons.Outlined.Sos,
                        contentDescription = null
                    )
                }
            }

            IconButton(
                onClick = { onClickSettings() }
            ) {
                Icon(
                    imageVector = Icons.Outlined.Settings,
                    contentDescription = null
                )
            }

            IconButton(
                onClick = { expanded = true }
            ) {
                Icon(
                    imageVector = Icons.Outlined.MoreVert,
                    contentDescription = null
                )
            }


            DropdownMenu(
                expanded = expanded,
                onDismissRequest = { expanded = false }
            ) {

                DropdownMenuItem(
                    text = {
                        Text(text = "About")
                    },
                    onClick = { onClickAbout() },
                    leadingIcon = {
                        Icon(imageVector = Icons.Outlined.Info,
                            contentDescription = null)
                    }
                )


                DropdownMenuItem(
                    text = {
                        Text(text = "Feedback")
                    },
                    onClick = { /*TODO*/ },
                    leadingIcon = {
                        Icon(imageVector = Icons.AutoMirrored.Outlined.Comment,
                            contentDescription = null)
                    }
                )

                DropdownMenuItem(
                    text = {
                        Text(text = "Share")
                    },
                    onClick = { /*TODO*/ },
                    leadingIcon = {
                        Icon(imageVector = Icons.Outlined.Share,
                            contentDescription = null)
                    }
                )

                DropdownMenuItem(
                    text = {
                        Text(text = "Privacy Policy")
                    },
                    onClick = {
                        openUrl(
                            context = context,
                            url = "https://bitmavrick.github.io/privacy-policy/lumolight.html"
                        )
                    },
                    leadingIcon = {
                        Icon(imageVector = Icons.Outlined.PrivacyTip,
                            contentDescription = null)
                    }
                )

                HorizontalDivider()

                DropdownMenuItem(
                    text = {
                        Text(text = "Rate Lumolight")
                    },
                    onClick = { /*TODO*/ },
                    leadingIcon = {
                        Icon(imageVector = Icons.Outlined.StarRate,
                            contentDescription = null)
                    }
                )

                DropdownMenuItem(
                    text = {
                        Text(text = "Try more apps")
                    },
                    onClick = { /*TODO*/ },
                    leadingIcon = {
                        Icon(imageVector = Icons.Outlined.Spoke,
                            contentDescription = null)
                    }
                )


            }
        }
    )
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