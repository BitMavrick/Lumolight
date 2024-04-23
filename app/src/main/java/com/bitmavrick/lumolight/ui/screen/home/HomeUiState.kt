package com.bitmavrick.lumolight.ui.screen.home

data class HomeUiState (
    val selectedTabIndex: Int = 0,
    val topSOSButtonStatus: TopSOSButtonStatus = TopSOSButtonStatus.IDLE
)


enum class TopSOSButtonStatus {
    IDLE,
    COUNTING,
    RUNNING
}