package com.bitmavrick.lumolight.ui.tab.flashlight

import android.content.Context

sealed interface FlashlightUiEvent {
    data class UpdateFlashlightDuration(val index: Int, val time: Int) : FlashlightUiEvent
    data class UpdateFlashlightBPM(val index: Int, val value: Int) : FlashlightUiEvent
    data class UpdateFlashlightStrength(val index: Int) : FlashlightUiEvent
    data class UpdateMaxFlashlightStrengthIndex(val value: Context) : FlashlightUiEvent
    data class UpdateFlashlightStatus(val status: Boolean) : FlashlightUiEvent
    data class ToggleFlashlight(val value : Context) : FlashlightUiEvent
}