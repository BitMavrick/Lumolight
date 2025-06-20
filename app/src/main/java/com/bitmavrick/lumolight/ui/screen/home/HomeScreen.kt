/* *
* Lumolight :: Open-source program under GPL-3.0 :: Copyright - BitMavrick :: https://github.com/BitMavrick
* */

package com.bitmavrick.lumolight.ui.screen.home

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.PagerState
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CameraRear
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.PhoneIphone
import androidx.compose.material.icons.filled.Sos
import androidx.compose.material.icons.outlined.CameraRear
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.Smartphone
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.LeadingIconTab
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.TabPosition
import androidx.compose.material3.TabRowDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.layout
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Constraints
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.lerp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.bitmavrick.lumolight.R
import com.bitmavrick.lumolight.activity.core.Screen
import com.bitmavrick.lumolight.ui.screen.sos.SosViewModel
import com.bitmavrick.lumolight.ui.tab.CustomFilledButton
import com.bitmavrick.lumolight.ui.tab.flashlight.FlashlightScreen
import com.bitmavrick.lumolight.ui.tab.flashlight.FlashlightUiEvent
import com.bitmavrick.lumolight.ui.tab.flashlight.FlashlightUiState
import com.bitmavrick.lumolight.ui.tab.quickAction.QuickActionScreen
import com.bitmavrick.lumolight.ui.tab.quickAction.QuickActionUiEvent
import com.bitmavrick.lumolight.ui.tab.quickAction.QuickActionUiState
import com.bitmavrick.lumolight.ui.tab.screenFlash.ScreenFlashScreen
import com.bitmavrick.lumolight.ui.tab.screenFlash.ScreenFlashUiEvent
import com.bitmavrick.lumolight.ui.tab.screenFlash.ScreenFlashUiState
import com.bitmavrick.lumolight.ui.theme.LumolightTheme
import com.bitmavrick.lumolight.util.getAppVersion
import kotlinx.coroutines.launch

@Composable
fun HomeScreen(
    navController: NavController,
    homeUiState: HomeUiState,
    homeOnEvent: (HomeUiEvent) -> Unit,
    quickActionUiState: QuickActionUiState,
    quickActionUiEvent: (QuickActionUiEvent) -> Unit,
    screenFlashUiSate: ScreenFlashUiState,
    screenFlashUiEvent: (ScreenFlashUiEvent) -> Unit,
    flashlightUiState: FlashlightUiState,
    flashlightUiEvent: (FlashlightUiEvent) -> Unit,
    sosViewModel: SosViewModel = viewModel()
) {
    val context = LocalContext.current

    val tabItems = listOf(
        TabItem(
            title = context.getString(R.string.actions),
            unselectedIcon = Icons.Outlined.Home,
            selectedIcon = Icons.Filled.Home,
        ),
        TabItem(
            title = context.getString(R.string.screen),
            unselectedIcon = Icons.Outlined.Smartphone,
            selectedIcon = Icons.Filled.PhoneIphone
        ),
        TabItem(
            title = context.getString(R.string.flashlight),
            unselectedIcon = Icons.Outlined.CameraRear,
            selectedIcon = Icons.Filled.CameraRear
        ),
    )

    val snackBarHostState = remember { SnackbarHostState() }
    val coroutineScope = rememberCoroutineScope()

    Scaffold (
        topBar = {
            HomeScreenTopBar(
                showSosButton = homeUiState.showSosButton,
                onClickSos = {
                    homeOnEvent(HomeUiEvent.InitializeSosTimer)
                },
                onClickSettings = {
                    navController.navigate(Screen.SettingScreen.route)
                },
                onClickAbout = {
                    homeOnEvent(HomeUiEvent.UpdateShowAboutDialog(true))
                }
            )
        },
        content = { innerPadding ->
            val pagerState = rememberPagerState {
                tabItems.size
            }

            if(homeUiState.showSosDialog){
                AlertDialog(
                    icon = {
                        Icon(
                            imageVector = Icons.Default.Sos,
                            tint = MaterialTheme.colorScheme.error,
                            contentDescription = null
                        )
                    },
                    title = {
                        Text(
                            text = context.getString(R.string.emergency_sos),
                            color = MaterialTheme.colorScheme.error,
                            fontWeight = FontWeight.Bold
                        )
                    },
                    text = {
                        Text(
                            text = homeUiState.quickSOSCountingSeconds.toString(),
                            modifier = Modifier.fillMaxWidth(),
                            textAlign = TextAlign.Center,
                            style = MaterialTheme.typography.titleLarge,
                            fontWeight = FontWeight.Bold
                        )
                    },
                    onDismissRequest = {},
                    confirmButton = {},
                    containerColor = MaterialTheme.colorScheme.errorContainer,
                    dismissButton = {
                        CustomFilledButton(
                            buttonText = context.getString(R.string.stop).uppercase(),
                            onClick = {
                                homeOnEvent(HomeUiEvent.CeaseSosTimer)
                            }
                        )
                    }
                )
            }

            if(homeUiState.topSOSButtonStatus == TopSOSButtonStatus.ACTIVE){
                homeOnEvent(HomeUiEvent.StopSos)
                sosViewModel.updateFlashlightStatus(true)
                sosViewModel.toggleFlashLight(context, true)
                navController.navigate(Screen.SosScreen.route)
            }

            if(homeUiState.showAboutDialog){
                AlertDialog(
                    title = {
                        Column(
                            modifier = Modifier.height(60.dp),
                        ) {
                            AppIconRound()
                        }
                    },
                    text = {

                        Column {
                            Text(
                                text = context.getString(R.string.app_name),
                                style = MaterialTheme.typography.titleLarge,
                                fontWeight = FontWeight.Bold
                            )
                            HorizontalDivider(
                                thickness = 1.dp,
                                modifier = Modifier.padding(vertical = 8.dp)
                            )

                            Text(text = "Version: ${getAppVersion(LocalContext.current)}\nDeveloped by BitMavrick\nLicensed under GPL-3.0")
                        }

                    },
                    onDismissRequest = { homeOnEvent(HomeUiEvent.UpdateShowAboutDialog(false))  },
                    confirmButton = {}
                )
            }

            LaunchedEffect(pagerState.currentPage, pagerState.isScrollInProgress) {
                if(!pagerState.isScrollInProgress){
                    homeOnEvent(HomeUiEvent.UpdateTab(pagerState.currentPage))
                }
            }

            Column(
                Modifier
                    .padding(innerPadding)
                    .fillMaxSize()
            ) {
                CustomTabRow(
                    selectedTabIndex = homeUiState.selectedTabIndex,
                    indicator = { tabPositions ->
                        TabRowDefaults.SecondaryIndicator(
                            modifier = Modifier.pagerTabIndicatorOffset(pagerState, tabPositions)
                        )
                    }
                ) {
                    tabItems.forEachIndexed{index, item ->
                        LeadingIconTab(
                            selected = index == homeUiState.selectedTabIndex,
                            onClick = {
                                homeOnEvent(HomeUiEvent.UpdateTab(index))
                                coroutineScope.launch { pagerState.animateScrollToPage(index) }
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
                                    contentDescription = context.getString(R.string.tab_icon_description)
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
                ) { index ->
                    Box(
                        modifier = Modifier.fillMaxSize(),
                    ) {
                        when(index){
                            0 -> QuickActionScreen(
                                navController = navController,
                                quickActionUiState = quickActionUiState,
                                quickActionUiEvent = quickActionUiEvent,
                                snakeBarHost = snackBarHostState
                            )
                            1 -> ScreenFlashScreen(
                                homeUiState = homeUiState,
                                screenFlashUiState = screenFlashUiSate,
                                screenFlashUiEvent = screenFlashUiEvent,
                                onClickStart = { navController.navigate(Screen.FlashScreen.route) }
                            )
                            2 -> FlashlightScreen(
                                homeUiState = homeUiState,
                                quickActionUiEvent = quickActionUiEvent,
                                flashlightUiState = flashlightUiState,
                                flashlightUiEvent = flashlightUiEvent
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


data class TabItem(
    val title: String,
    val unselectedIcon: ImageVector,
    val selectedIcon: ImageVector
)

private fun Modifier.pagerTabIndicatorOffset(
    pagerState: PagerState,
    tabPositions: List<TabPosition>,
    pageIndexMapping: (Int) -> Int = { it },
) = layout { measurable, constraints ->
    if (tabPositions.isEmpty()) {
        layout(constraints.maxWidth, 0) {}
    } else {
        val currentPage = minOf(tabPositions.lastIndex, pageIndexMapping(pagerState.currentPage))
        val currentTab = tabPositions[currentPage]
        val previousTab = tabPositions.getOrNull(currentPage - 1)
        val nextTab = tabPositions.getOrNull(currentPage + 1)
        val fraction = pagerState.currentPageOffsetFraction
        val indicatorWidth = if (fraction > 0 && nextTab != null) {
            lerp(currentTab.width, nextTab.width, fraction).roundToPx()
        } else if (fraction < 0 && previousTab != null) {
            lerp(currentTab.width, previousTab.width, -fraction).roundToPx()
        } else {
            currentTab.width.roundToPx()
        }
        val indicatorOffset = if (fraction > 0 && nextTab != null) {
            lerp(currentTab.left, nextTab.left, fraction).roundToPx()
        } else if (fraction < 0 && previousTab != null) {
            lerp(currentTab.left, previousTab.left, -fraction).roundToPx()
        } else {
            currentTab.left.roundToPx()
        }
        val placeable = measurable.measure(
            Constraints(
                minWidth = indicatorWidth,
                maxWidth = indicatorWidth,
                minHeight = 0,
                maxHeight = constraints.maxHeight
            )
        )
        layout(constraints.maxWidth, maxOf(placeable.height, constraints.minHeight)) {
            placeable.placeRelative(
                indicatorOffset,
                maxOf(constraints.minHeight - placeable.height, 0)
            )
        }
    }
}

@Preview(showBackground = true, showSystemUi = false)
@Composable
fun HomeScreenPreview() {
    LumolightTheme {
        HomeScreen(
            navController = rememberNavController(),
            homeUiState = HomeUiState(
                showSosDialog = false,
                showAboutDialog = false,
                quickSOSCountingSeconds = 3
            ),
            homeOnEvent = {},
            quickActionUiState = QuickActionUiState(),
            quickActionUiEvent = {},
            screenFlashUiSate = ScreenFlashUiState(),
            screenFlashUiEvent = {},
            flashlightUiState = FlashlightUiState(),
            flashlightUiEvent = {}
        )
    }
}