/* *
* Lumolight :: Open-source program under GPL-3.0 :: Copyright - BitMavrick :: https://github.com/BitMavrick
* */

package com.bitmavrick.lumolight.ui.screen.home

data class HomeUiState (
    val showSosButton : Boolean = false,
    val selectedTabIndex: Int = 0,
    val topSOSButtonStatus: TopSOSButtonStatus = TopSOSButtonStatus.IDLE,
    val quickSOSCountingSeconds: Int? = null,
    val showSosDialog: Boolean = false,
    val showAboutDialog: Boolean = false,
    val hapticStatus: Boolean = false
)


enum class TopSOSButtonStatus {
    IDLE,
    COUNTING,
    ACTIVE
}