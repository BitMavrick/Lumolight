package com.bitmavrick.flash

sealed class FlashlightUiEvent {
    data class UpdateFlashlightBpmRate(val bpm: Int) : FlashlightUiEvent()
    data class UpdateFlashlightDuration(val duration: Int) : FlashlightUiEvent()
    data class UpdateFlashlightIntensity(val intensity: Int) : FlashlightUiEvent()
}