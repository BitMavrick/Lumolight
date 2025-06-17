/* *
* Lumolight :: Open-source program under GPL-3.0 :: Copyright - BitMavrick :: https://github.com/BitMavrick
* */


package com.bitmavrick.lumolight.ui.screen.setting.appearance

data class ThemeUiState (
    val appearance: Appearance = Appearance.DEFAULT,
    val showThemeDialog: Boolean = false,
    val dynamicTheme: Boolean = true,
    val oledTheme: Boolean = false
)

enum class Appearance{
    DEFAULT,
    LIGHT,
    DARK
}