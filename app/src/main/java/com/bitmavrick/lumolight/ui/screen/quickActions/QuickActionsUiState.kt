package com.bitmavrick.lumolight.ui.screen.quickActions


data class QuickActionsUiState (
    val startButtonStatus: Boolean = false,
    val quickSOSButtonStatus: QuickSOSButtonStatus = QuickSOSButtonStatus.NONE,
    val segmentedButtonIndex: Int = 1
)

enum class QuickSOSButtonStatus {
    NONE,
    RUNNING,
    ACTIVE
}