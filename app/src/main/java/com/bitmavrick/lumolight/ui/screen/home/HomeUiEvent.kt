package com.bitmavrick.lumolight.ui.screen.home

sealed interface HomeUiEvent {
    data class UpdateTab(val tabIndex : Int) : HomeUiEvent
    data class UpdateShowAboutDialog(val status : Boolean) : HomeUiEvent

    data object InitializeSosTimer : HomeUiEvent
    data object CeaseSosTimer : HomeUiEvent
    data object StopSos : HomeUiEvent
}