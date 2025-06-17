/* *
* Lumolight :: Open-source program under GPL-3.0 :: Copyright - BitMavrick :: https://github.com/BitMavrick
* */


package com.bitmavrick.lumolight.ui.screen.setting.appearance

sealed interface ThemeUiEvent {
    data class UpdateAppearance(val appearance: Appearance) : ThemeUiEvent
    data class UpdateThemeDialog(val visible: Boolean) : ThemeUiEvent
    data class UpdateDynamicTheme(val dynamicTheme: Boolean) : ThemeUiEvent
    data class UpdateOledTheme(val oledTheme: Boolean) : ThemeUiEvent
}