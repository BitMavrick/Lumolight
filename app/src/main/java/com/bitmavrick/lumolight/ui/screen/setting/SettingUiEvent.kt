/* *
* Lumolight :: Open-source program under GPL-3.0 :: Copyright - BitMavrick :: https://github.com/BitMavrick
* */

package com.bitmavrick.lumolight.ui.screen.setting

sealed interface SettingUiEvent {
    data class UpdateSaveQuickActionSetting(val saveQuickAction : Boolean) : SettingUiEvent
    data class UpdateShowSosButtonPreference(val sosButtonPreference : Boolean) : SettingUiEvent
    data class UpdateHapticStatus(val status: Boolean) : SettingUiEvent
}