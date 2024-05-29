package com.bitmavrick.lumolight.ui.screen.home

data class HomeUiState (
    val showSosButton : Boolean = false,
    val selectedTabIndex: Int = 0,
    val topSOSButtonStatus: TopSOSButtonStatus = TopSOSButtonStatus.IDLE,
    val quickSOSCountingSeconds: Int? = null,
    val showAboutDialog: Boolean = false
)


enum class TopSOSButtonStatus {
    IDLE, // Nothing
    COUNTING, // When the timer is running
    ACTIVE // When sos is active
}