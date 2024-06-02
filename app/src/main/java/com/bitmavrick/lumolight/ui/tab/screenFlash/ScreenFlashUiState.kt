/* *
* Lumolight :: Open-source program under GPL-3.0 :: Copyright - BitMavrick :: https://github.com/BitMavrick
* */

package com.bitmavrick.lumolight.ui.tab.screenFlash

data class ScreenFlashUiState (
    val screenFlashDurationIndex : Int = 0,
    val screenFlashDurationMin : Int = -1,
    val screenFlashColorIndex : Int = 0,
    val screenFlashColorValue : String = "#FFFFFF",
    val screenFlashBrightnessIndex: Int = 0,
    val screenFlashBrightnessValue: Float = 1f,
)