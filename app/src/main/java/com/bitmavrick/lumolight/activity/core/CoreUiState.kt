/* *
* Lumolight :: Open-source program under GPL-3.0 :: Copyright - BitMavrick :: https://github.com/BitMavrick
* */

package com.bitmavrick.lumolight.activity.core

import com.bitmavrick.lumolight.ui.screen.setting.appearance.Appearance


data class CoreUiState (
    val appLoadingStatus: Boolean = true,
    val appearance: Appearance = Appearance.DEFAULT,
    val dynamicTheme: Boolean = true,
    val oledTheme: Boolean = false
)