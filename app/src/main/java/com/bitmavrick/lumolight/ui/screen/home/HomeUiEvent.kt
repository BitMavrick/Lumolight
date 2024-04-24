package com.bitmavrick.lumolight.ui.screen.home

sealed interface HomeUiEvent {
    data class updateTab(val tabIndex : Int) : HomeUiEvent
}