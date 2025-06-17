package com.bitmavrick.lumolight.ui.tab.screenFlash

sealed interface ScreenFlashUiEvent {
    data class UpdateScreenFlashDuration(val index: Int) : ScreenFlashUiEvent
    data class UpdateScreenFlashColor(val index: Int) : ScreenFlashUiEvent
    data class UpdateScreenFlashBrightness(val index: Int) : ScreenFlashUiEvent
}