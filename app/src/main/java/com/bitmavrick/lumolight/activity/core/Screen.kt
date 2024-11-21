package com.bitmavrick.lumolight.activity.core

sealed class Screen(val route: String){
    data object HomeScreen : Screen("home_screen")
    data object QuickFlashScreen : Screen("quick_flash_screen")
    data object FlashScreen : Screen("flash_screen")
    data object SosScreen : Screen("sos_screen")
    data object SettingScreen : Screen("setting_screen")
    data object AppearanceScreen : Screen("appearance_screen")
    data object AboutScreen : Screen("about_screen")
}