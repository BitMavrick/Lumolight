/* *
* Lumolight :: Open-source program under GPL-3.0 :: Copyright - BitMavrick :: https://github.com/BitMavrick
* */

package com.bitmavrick.lumolight.ui.screen.setting

data class SettingUiState (
    val saveQuickAction: Boolean = false,
    val showSosButton: Boolean = true,
    val showThemeDialog: Boolean = false,
    val appearance: Appearance = Appearance.DEFAULT,
    val dynamicTheme: Boolean = true
)

enum class Appearance{
    DEFAULT,
    LIGHT,
    DARK
}