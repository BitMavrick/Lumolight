package com.bitmavrick.lumolight.navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Brightness5
import androidx.compose.material.icons.outlined.FlashOn
import androidx.compose.material.icons.outlined.GridView
import androidx.compose.material.icons.outlined.Settings
import androidx.compose.material.icons.rounded.Brightness5
import androidx.compose.material.icons.rounded.FlashOn
import androidx.compose.material.icons.rounded.GridView
import androidx.compose.material.icons.rounded.Settings
import androidx.compose.ui.graphics.vector.ImageVector
import com.bitmavrick.locales.R as localesR

enum class AppDestinations(
    val label: Int,
    val icon: ImageVector,
    val selectedIcon: ImageVector,
    val contentDescription: String
) {
    SHORTCUTS(localesR.string.shortcuts, Icons.Outlined.GridView, Icons.Rounded.GridView, "Shortcuts screen"),
    SCREEN(localesR.string.screen, Icons.Outlined.Brightness5, Icons.Rounded.Brightness5, "Shortcuts screen"),
    FLASH(localesR.string.flash, Icons.Outlined.FlashOn, Icons.Rounded.FlashOn, "Shortcuts screen"),
    SETTINGS(localesR.string.settings, Icons.Outlined.Settings, Icons.Rounded.Settings, "Shortcuts screen"),
}
