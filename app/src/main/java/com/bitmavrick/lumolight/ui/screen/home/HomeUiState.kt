package com.bitmavrick.lumolight.ui.screen.home

data class HomeUiState (
    val selectedTabIndex: Int = 0,
    val topSOSButtonStatus: TopSOSButtonStatus = TopSOSButtonStatus.IDLE,
    val quickSOSCountingSeconds: Int? = null,
)


enum class TopSOSButtonStatus {
    IDLE,
    COUNTING,
    RUNNING
}