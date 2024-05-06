package com.bitmavrick.lumolight.ui.screen.home

data class HomeUiState (
    val selectedTabIndex: Int = 0,
    val topSOSButtonStatus: TopSOSButtonStatus = TopSOSButtonStatus.IDLE,
    val quickSOSCountingSeconds: Int? = null,
    val showAboutDialog: Boolean = false
)


enum class TopSOSButtonStatus {
    IDLE,
    COUNTING,
    RUNNING
}