package com.bitmavrick.lumolight.ui.screen

import android.util.Log
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.BrightnessHigh
import androidx.compose.material.icons.filled.Build
import androidx.compose.material.icons.filled.FlashlightOn
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.PhoneIphone
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationRail
import androidx.compose.material3.NavigationRailItem
import androidx.compose.material3.PermanentNavigationDrawer
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import com.bitmavrick.lumolight.ui.LumolightUiState
import com.bitmavrick.lumolight.ui.utils.LumolightNavigationType
import com.bitmavrick.lumolight.ui.utils.NavigationItem

@Composable
fun HomeScreen(
    navigationType: LumolightNavigationType,
    uiState: LumolightUiState,
    onTabPressed: (NavigationItem) -> Unit
) {
    val navigationItemContentList = listOf(
        NavigationItemContent(
            screenType = NavigationItem.SCREEN_FLASH,
            icon = Icons.Default.PhoneIphone,
            text = "Screen Flash"
        ),
        NavigationItemContent(
            screenType = NavigationItem.QUICK_ACTION,
            icon = Icons.Default.BrightnessHigh,
            text = "Quick Actions"
        ),
        NavigationItemContent(
            screenType = NavigationItem.FLASHLIGHT,
            icon = Icons.Default.FlashlightOn,
            text = "Flashlight"
        )
    )

    when(navigationType){
        LumolightNavigationType.BOTTOM_NAVIGATION -> {
            Column(
                Modifier.fillMaxSize()
            ) {
                Box(
                    modifier = Modifier.weight(1f)
                ) {
                    when(uiState.currentNavigationItem){
                        NavigationItem.SCREEN_FLASH -> {
                            ScreenFlashScreen()
                        }

                        NavigationItem.QUICK_ACTION -> {
                            QuickActionScreen()
                        }

                        NavigationItem.FLASHLIGHT -> {
                            FlashlightScreen()
                        }
                    }
                }
                LumolightBottomNavigationBar(
                    currentTab = uiState.currentNavigationItem,
                    navigationItemContentList = navigationItemContentList,
                    onTabPressed =  onTabPressed
                )
            }
        }

        LumolightNavigationType.NAVIGATION_RAIL -> {
            Column(
                Modifier.fillMaxSize()
            ) {
                Box(
                    modifier = Modifier.weight(1f)
                ) {
                    when(uiState.currentNavigationItem){
                        NavigationItem.SCREEN_FLASH -> {
                            Text(text = "This is the screen flash")
                        }

                        NavigationItem.QUICK_ACTION -> {
                            Text(text = "This is the quick action")

                        }

                        NavigationItem.FLASHLIGHT -> {
                            Text(text = "This is the flashlight")
                        }
                    }
                }
                LumolightNavigationRail(
                    currentTab = uiState.currentNavigationItem,
                    navigationItemContentList = navigationItemContentList,
                    onTabPressed = onTabPressed
                )
            }
        }

        LumolightNavigationType.PERMANENT_NAVIGATION_DRAWER -> {
            Column(
                Modifier.fillMaxSize()
            ) {
                Box(
                    modifier = Modifier.weight(1f)
                ) {
                    when(uiState.currentNavigationItem){
                        NavigationItem.SCREEN_FLASH -> {
                            Text(text = "This is the screen flash")
                        }

                        NavigationItem.QUICK_ACTION -> {
                            Text(text = "This is the quick action")

                        }

                        NavigationItem.FLASHLIGHT -> {
                            Text(text = "This is the flashlight")
                        }
                    }
                }
                LumolightNavigationRail(
                    currentTab = uiState.currentNavigationItem,
                    navigationItemContentList = navigationItemContentList,
                    onTabPressed = onTabPressed
                )
            }
        }
    }
}

@Composable
private fun LumolightBottomNavigationBar(
    currentTab: NavigationItem,
    onTabPressed: ((NavigationItem) -> Unit),
    navigationItemContentList: List<NavigationItemContent>,
    modifier: Modifier = Modifier
){
    NavigationBar(modifier = modifier) {
        for (navItem in navigationItemContentList){
            NavigationBarItem(
                selected = currentTab == navItem.screenType,
                onClick = { onTabPressed(navItem.screenType) },
                icon = {
                    Icon(
                        imageVector = navItem.icon,
                        contentDescription = navItem.text
                    )
                },
                label = { Text(text = navItem.text) },
                alwaysShowLabel = false
            )
        }
    }
}

@Composable
private fun LumolightNavigationRail(
    currentTab: NavigationItem,
    onTabPressed: ((NavigationItem) -> Unit),
    navigationItemContentList: List<NavigationItemContent>,
    modifier: Modifier = Modifier
){
    NavigationRail(modifier = modifier) {
        Spacer(Modifier.weight(1f))
        for (navItem in navigationItemContentList){
            NavigationRailItem(
                selected = currentTab == navItem.screenType,
                onClick = { onTabPressed(navItem.screenType) },
                icon = {
                    Icon(
                        imageVector = navItem.icon,
                        contentDescription = navItem.text
                    )
                },
                label = { Text(text = navItem.text) },
                alwaysShowLabel = false
            )
        }
    }

}

/*
@Composable
private fun LumolightNavigationDrawer(
    currentTab: NavigationItem,
    onTabPressed: ((NavigationItem) -> Unit),
    navigationItemContentList: List<NavigationItemContent>,
){


}
*/

private data class NavigationItemContent(
    val screenType: NavigationItem,
    val icon: ImageVector,
    val text: String
)