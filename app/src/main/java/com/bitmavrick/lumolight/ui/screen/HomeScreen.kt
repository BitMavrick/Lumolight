package com.bitmavrick.lumolight.ui.screen

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Person
import androidx.compose.runtime.Composable
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
            icon = Icons.Default.Person,
            text = "Screen"
        ),
    )
}

private data class NavigationItemContent(
    val screenType: NavigationItem,
    val icon: ImageVector,
    val text: String
)