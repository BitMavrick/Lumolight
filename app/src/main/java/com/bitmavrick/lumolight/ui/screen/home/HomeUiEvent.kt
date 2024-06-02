/* *
* Lumolight :: Open-source program under GPL-3.0 :: Copyright - BitMavrick :: https://github.com/BitMavrick
* */

package com.bitmavrick.lumolight.ui.screen.home

sealed interface HomeUiEvent {
    data class UpdateTab(val tabIndex : Int) : HomeUiEvent
    data class UpdateShowAboutDialog(val status : Boolean) : HomeUiEvent
    data object InitializeSosTimer : HomeUiEvent
    data object CeaseSosTimer : HomeUiEvent
    data object StopSos : HomeUiEvent
}