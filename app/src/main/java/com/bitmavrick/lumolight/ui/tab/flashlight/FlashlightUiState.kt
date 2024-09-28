/* *
* Lumolight :: Open-source program under GPL-3.0 :: Copyright - BitMavrick :: https://github.com/BitMavrick
* */

package com.bitmavrick.lumolight.ui.tab.flashlight

data class FlashlightUiState (
    val flashlightDurationIndex : Int = 0,
    val flashlightDurationMin : Int = -1,
    val flashlightBpmIndex : Int = 0,
    val flashlightBpmValue : Int = 0,
    val flashlightMaxStrengthIndex : Int = 1,
    val flashlightStrength: Int = 1,
    val flashlightStatus : Boolean = false
)