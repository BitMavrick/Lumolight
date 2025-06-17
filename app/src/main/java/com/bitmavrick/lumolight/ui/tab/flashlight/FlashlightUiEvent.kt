/* *
* Lumolight :: Open-source program under GPL-3.0 :: Copyright - BitMavrick :: https://github.com/BitMavrick
* */


package com.bitmavrick.lumolight.ui.tab.flashlight

import android.content.Context

sealed interface FlashlightUiEvent {
    data class UpdateFlashlightDuration(val index: Int) : FlashlightUiEvent
    data class UpdateFlashlightBPM(val index: Int) : FlashlightUiEvent
    data class UpdateFlashlightStrength(val index: Int) : FlashlightUiEvent
    data class UpdateMaxFlashlightStrengthIndex(val value: Context) : FlashlightUiEvent
    data class UpdateFlashlightStatus(val status: Boolean) : FlashlightUiEvent
    data class ToggleFlashlight(val value : Context) : FlashlightUiEvent
}