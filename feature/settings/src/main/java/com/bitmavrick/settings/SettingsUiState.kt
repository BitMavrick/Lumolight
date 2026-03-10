package com.bitmavrick.settings

data class SettingsUiState (
    val isLoaded : Boolean = false, // * Will be true when loaded
    val darkTheme : Int = 0,
    val dynamicTheme : Boolean = true,
    val amoledTheme : Boolean = false,
    val lumolightPremium: Boolean = false,
    val flashTilePreference: Int = 0,
    val volumeKeyFlashControl: Boolean = true
)