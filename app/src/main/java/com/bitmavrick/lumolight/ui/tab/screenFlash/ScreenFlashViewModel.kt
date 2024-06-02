/* *
* Lumolight :: Open-source program under GPL-3.0 :: Copyright - BitMavrick :: https://github.com/BitMavrick
* */

package com.bitmavrick.lumolight.ui.tab.screenFlash

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update

class ScreenFlashViewModel : ViewModel() {
    private val _uiState = MutableStateFlow(ScreenFlashUiState())
    val uiState : StateFlow<ScreenFlashUiState> = _uiState

    fun updateScreenFlashDuration(index: Int, time: Int){
        _uiState.update {
            it.copy(
                screenFlashDurationIndex = index,
                screenFlashDurationMin = time
            )
        }
    }

    fun updateScreenFlashColor(index : Int, color : String){
        _uiState.update {
            it.copy(
                screenFlashColorIndex = index,
                screenFlashColorValue = color
            )
        }
    }

    fun updateScreenFlashBrightness(index: Int, brightness: Float){
        _uiState.update {
            it.copy(
                screenFlashBrightnessIndex = index,
                screenFlashBrightnessValue = brightness
            )
        }
    }
}