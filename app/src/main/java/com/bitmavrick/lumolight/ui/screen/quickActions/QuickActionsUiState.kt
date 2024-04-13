package com.bitmavrick.lumolight.ui.screen.quickActions


data class QuickActionsUiState (
    val startButtonStatus: Boolean = false,
    val startButtonLittleLoading: Boolean = false,
    val quickSOSButtonStatus: QuickSOSButtonStatus = QuickSOSButtonStatus.NONE,
    val quickSOSRunningSeconds: Int? = null,
    val segmentedButtonIndex: Int = 1
)

enum class QuickSOSButtonStatus {
    NONE,
    RUNNING,
    ACTIVE
}