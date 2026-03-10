package com.bitmavrick.lumolight.navigation

import androidx.annotation.DrawableRes
import androidx.compose.ui.graphics.vector.ImageVector
import compose.icons.TablerIcons
import compose.icons.tablericons.Bolt
import compose.icons.tablericons.BrightnessUp
import compose.icons.tablericons.Home
import com.bitmavrick.locales.R as localesR
import com.bitmavrick.ui.R as IconR

enum class AppDestinations(
    val label: Int,
    val icon: AppIcon,
    val selectedIcon: AppIcon,
    val contentDescription: String
) {
    SHORTCUTS(localesR.string.home, AppIcon.Vector(TablerIcons.Home), AppIcon.Vector(TablerIcons.Home), "Shortcuts screen"),
    SCREEN(localesR.string.screen, AppIcon.Vector(TablerIcons.BrightnessUp), AppIcon.Vector(TablerIcons.BrightnessUp), "Screen flash screen"),
    BOTH(localesR.string.dual,  AppIcon.Drawable(IconR.drawable.icon_both_flash), AppIcon.Drawable(IconR.drawable.icon_both_flash), "Both flash screen"),
    FLASH(localesR.string.rear, AppIcon.Vector(TablerIcons.Bolt), AppIcon.Vector(TablerIcons.Bolt) , "Flash screen"),
    SETTINGS(localesR.string.settings, AppIcon.Drawable(IconR.drawable.settings_2), AppIcon.Drawable(IconR.drawable.settings_2), "Settings screen"),
}


sealed interface AppIcon {
    data class Vector(val imageVector: ImageVector) : AppIcon
    data class Drawable(@param:DrawableRes val id: Int) : AppIcon
}
