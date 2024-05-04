package com.bitmavrick.lumolight.ui.tab.flashlight

data class FlashlightUiState (
    val flashlightDurationIndex : Int = 0,
    val flashlightDurationMin : Int = -1,
    val flashlightBpmIndex : Int = 0,
    val flashlightBpmValue : Int = 0,
    val flashlightIntensityIndex : Int = 0,
    val flashlightIntensityValue : String = "LOW",
    val flashlightAlertDialog : Boolean = false
)