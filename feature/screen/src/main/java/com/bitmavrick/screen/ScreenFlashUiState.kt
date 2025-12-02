package com.bitmavrick.screen

data class ScreenFlashUiState (
    val screenColorHue: Float = 0f,
    val screenColorSat: Float = 0f,
    val screenColorVal: Float = 0f,
    val screenColorPresetSelection: Boolean = false,
    val screenColorPresetIndex: Int = 0,
    val brightness: Int = 1,
    val duration: Int = 1
)