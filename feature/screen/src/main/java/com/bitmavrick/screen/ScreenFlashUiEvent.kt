package com.bitmavrick.screen

sealed class ScreenFlashUiEvent {
    data class UpdateScreenColor(val hue : Float, val saturation : Float, val value : Float) : ScreenFlashUiEvent()
    data class UpdateScreenColorPreset(val index: Int) : ScreenFlashUiEvent()
    data class UpdateBrightness(val brightness: Int) : ScreenFlashUiEvent()
    data class UpdateDuration(val duration: Int) : ScreenFlashUiEvent()
}