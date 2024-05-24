package com.bitmavrick.lumolight.ui.screen.setting

sealed interface SettingUiEvent {
    data class UpdateSaveQuickActionSetting(val saveQuickAction : Boolean) : SettingUiEvent
}