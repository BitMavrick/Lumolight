package com.bitmavrick.lumolight.ui.screen.quickActions


data class QuickActionsUiState (
    val quickActionSegmentedButtonStatus: QuickActionSegmentedButtonStatus = QuickActionSegmentedButtonStatus.FRONT,
    val quickSOSButtonStatus: QuickSOSButtonStatus = QuickSOSButtonStatus.NONE,
    val startButtonStatus: Boolean = false
)


enum class QuickActionSegmentedButtonStatus {
    FRONT,
    BOTH,
    BACK
}

enum class QuickSOSButtonStatus {
    NONE,
    RUNNING,
    ACTIVE
}