package com.bitmavrick.lumolight.ui.tab.quickAction

data class QuickActionUiState (
    val startButtonStatus: Boolean = false,
    val startButtonLittleLoading: Boolean = false,
    val segmentedButtonSelectedIndex: Int = -1,
    val segmentedButtonDisable: Boolean = false,
)