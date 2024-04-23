package com.bitmavrick.lumolight.ui.tab.quickAction

data class QuickActionsUiState (
    val startButtonStatus: Boolean = false,
    val startButtonLittleLoading: Boolean = false,
    val segmentedButtonSelectedIndex: Int = 0,
    val segmentedButtonDisable: Boolean = false,

    val quickSOSButtonStatus: QuickSOSButtonStatus = QuickSOSButtonStatus.NONE,
    val quickSOSRunningSeconds: Int? = null,
)

enum class QuickSOSButtonStatus {
    NONE,
    RUNNING,
    ACTIVE
}