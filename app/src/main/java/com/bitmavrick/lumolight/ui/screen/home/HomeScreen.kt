package com.bitmavrick.lumolight.ui.screen.home

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.outlined.Comment
import androidx.compose.material.icons.filled.FlashlightOn
import androidx.compose.material.icons.filled.Smartphone
import androidx.compose.material.icons.filled.StarRate
import androidx.compose.material.icons.outlined.FlashlightOn
import androidx.compose.material.icons.outlined.Info
import androidx.compose.material.icons.outlined.MoreVert
import androidx.compose.material.icons.outlined.Settings
import androidx.compose.material.icons.outlined.Share
import androidx.compose.material.icons.outlined.Smartphone
import androidx.compose.material.icons.outlined.Spoke
import androidx.compose.material.icons.outlined.StarRate
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LeadingIconTab
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.bitmavrick.lumolight.activity.core.Screen
import com.bitmavrick.lumolight.ui.theme.LumolightTheme

@Composable
fun HomeScreen(
    navController: NavController
) {
    val tabItems = listOf(
        TabItem(
            title = "Actions",
            unselectedIcon = Icons.Outlined.StarRate,
            selectedIcon = Icons.Filled.StarRate
        ),
        TabItem(
            title = "Screen",
            unselectedIcon = Icons.Outlined.Smartphone,
            selectedIcon = Icons.Filled.Smartphone
        ),
        TabItem(
            title = "Flashlight",
            unselectedIcon = Icons.Outlined.FlashlightOn,
            selectedIcon = Icons.Filled.FlashlightOn
        ),
    )
    Scaffold (
        topBar = {
            HomeScreenTopBar(
                onClickSettings = {
                    navController.navigate(Screen.SettingScreen.route)
                }
            )
        },
        content = { innerPadding ->
            var selectedTabIndex by remember { mutableIntStateOf(0) }

            Column(
                Modifier
                    .padding(innerPadding)
                    .fillMaxSize()
            ) {
                TabRow(selectedTabIndex = selectedTabIndex) {
                    tabItems.forEachIndexed{index, item ->
                        LeadingIconTab(
                            selected = index == selectedTabIndex,
                            onClick = {
                                selectedTabIndex = index
                            },
                            text = {
                                Text(text = item.title)
                            },
                            icon = {
                                Icon(
                                    imageVector = if(index == selectedTabIndex){
                                        item.selectedIcon
                                    } else item.unselectedIcon ,
                                    contentDescription = null
                                )
                            }
                        )
                    }
                }
            }
        }
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreenTopBar(
    onClickSettings: () -> Unit,
) {
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
                        Text(text = "About us")
                    },
                    onClick = { /*TODO*/ },
                    leadingIcon = {
                        Icon(imageVector = Icons.Outlined.Info,
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

data class TabItem(
    val title: String,
    val unselectedIcon: ImageVector,
    val selectedIcon: ImageVector
)

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun HomeScreenPreview() {
    LumolightTheme {
        HomeScreen(navController = rememberNavController())
    }
}