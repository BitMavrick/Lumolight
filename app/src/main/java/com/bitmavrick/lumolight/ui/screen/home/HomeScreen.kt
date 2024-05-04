package com.bitmavrick.lumolight.ui.screen.home

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.outlined.Comment
import androidx.compose.material.icons.filled.CameraRear
import androidx.compose.material.icons.filled.DynamicForm
import androidx.compose.material.icons.filled.PhoneIphone
import androidx.compose.material.icons.outlined.CameraRear
import androidx.compose.material.icons.outlined.DynamicForm
import androidx.compose.material.icons.outlined.Info
import androidx.compose.material.icons.outlined.MoreVert
import androidx.compose.material.icons.outlined.PowerSettingsNew
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
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.TabRow
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.bitmavrick.lumolight.activity.core.Screen
import com.bitmavrick.lumolight.ui.tab.flashlight.FlashlightScreen
import com.bitmavrick.lumolight.ui.tab.flashlight.FlashlightViewModel
import com.bitmavrick.lumolight.ui.tab.quickAction.QuickActionScreen
import com.bitmavrick.lumolight.ui.tab.quickAction.QuickActionViewModel
import com.bitmavrick.lumolight.ui.tab.screenFlash.ScreenFlashScreen
import com.bitmavrick.lumolight.ui.tab.screenFlash.ScreenFlashViewModel
import com.bitmavrick.lumolight.ui.theme.LumolightTheme
import kotlin.system.exitProcess

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun HomeScreen(
    navController: NavController,
    homeUiState: HomeUiState,
    homeOnEvent: (HomeUiEvent) -> Unit,
    quickActionViewModel : QuickActionViewModel = viewModel(),
    screenFlashViewModel : ScreenFlashViewModel = viewModel(),
    flashlightViewModel : FlashlightViewModel = viewModel()
) {
    val tabItems = listOf(
        TabItem(
            title = "Actions",
            unselectedIcon = Icons.Outlined.DynamicForm,
            selectedIcon = Icons.Filled.DynamicForm
        ),
        TabItem(
            title = "Screen",
            unselectedIcon = Icons.Outlined.Smartphone,
            selectedIcon = Icons.Filled.PhoneIphone
        ),
        TabItem(
            title = "Flashlight",
            unselectedIcon = Icons.Outlined.CameraRear,
            selectedIcon = Icons.Filled.CameraRear
        ),
    )

    val snackBarHostState = remember { SnackbarHostState() }

    Scaffold (
        topBar = {
            HomeScreenTopBar(
                onClickSettings = {
                    // navController.navigate(Screen.SettingScreen.route)
                    exitProcess(0)
                }
            )
        },
        content = { innerPadding ->
            val pagerState = rememberPagerState {
                tabItems.size
            }

            LaunchedEffect(homeUiState.selectedTabIndex) {
                pagerState.animateScrollToPage(homeUiState.selectedTabIndex)
            }

            LaunchedEffect(pagerState.currentPage, pagerState.isScrollInProgress) {
                if(!pagerState.isScrollInProgress){
                    homeOnEvent(HomeUiEvent.updateTab(pagerState.currentPage))
                }
            }

            Column(
                Modifier
                    .padding(innerPadding)
                    .fillMaxSize()
            ) {
                TabRow(selectedTabIndex = homeUiState.selectedTabIndex) {
                    tabItems.forEachIndexed{index, item ->
                        LeadingIconTab(
                            selected = index == homeUiState.selectedTabIndex,
                            onClick = {
                                homeOnEvent(HomeUiEvent.updateTab(index))
                            },
                            text = {
                                Text(
                                    text = item.title,
                                    maxLines = 1
                                )
                            },
                            icon = {
                                Icon(
                                    imageVector = if(index == homeUiState.selectedTabIndex){
                                        item.selectedIcon
                                    } else item.unselectedIcon ,
                                    contentDescription = null
                                )
                            }
                        )
                    }
                }

                HorizontalPager(
                    state = pagerState,
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(1f)
                ) {index ->
                    Box(
                        modifier = Modifier.fillMaxSize(),
                        contentAlignment = Alignment.Center
                    ) {
                        when(index){
                            0 -> QuickActionScreen(
                                navController = navController,
                                viewModel = quickActionViewModel,
                                snakeBarHost = snackBarHostState
                            )
                            1 -> ScreenFlashScreen(
                                viewModel = screenFlashViewModel,
                                onClickStart = { navController.navigate(Screen.FlashScreen.route) }
                            )
                            2 -> FlashlightScreen(
                                viewModel = flashlightViewModel
                            )
                        }
                    }
                }
            }
        },

        snackbarHost = {
            SnackbarHost(snackBarHostState)
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

            /* ! SOS feature will not be implemented in first release
            IconButton(
                onClick = { /* TODO */ }
            ) {
                Icon(
                    imageVector = Icons.Outlined.Sos,
                    contentDescription = null
                )
            }
            */

            IconButton(
                onClick = { onClickSettings() }
            ) {
                Icon(
                    imageVector = Icons.Outlined.PowerSettingsNew,
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
        HomeScreen(
            navController = rememberNavController(),
            homeUiState = HomeUiState(),
            homeOnEvent = {}
        )
    }
}