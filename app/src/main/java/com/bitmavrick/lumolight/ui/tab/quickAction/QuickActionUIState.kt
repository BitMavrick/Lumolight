/* *
* Lumolight :: Open-source program under GPL-3.0 :: Copyright - BitMavrick :: https://github.com/BitMavrick
* */

package com.bitmavrick.lumolight.ui.tab.quickAction

data class QuickActionUiState (
    val startButtonStatus: Boolean = false,
    val startButtonLittleLoading: Boolean = false,
    val segmentedButtonSelectedIndex: Int = 0,
    val segmentedButtonDisable: Boolean = false,
    val shouldSaveSegmentedButtonIndex: Boolean = false,
    val hapticStatus: Boolean = false
)