package com.bitmavrick.settings

sealed class SettingsUiEvent {
    data class UpdateDarkTheme(val theme : Int) : SettingsUiEvent()
    data class UpdateDynamicTheme(val dynamicTheme : Boolean) : SettingsUiEvent()
    data class UpdateAmoledTheme(val amoled : Boolean) : SettingsUiEvent()
}