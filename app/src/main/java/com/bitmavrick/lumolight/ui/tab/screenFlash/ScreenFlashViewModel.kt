/* *
* Lumolight :: Open-source program under GPL-3.0 :: Copyright - BitMavrick :: https://github.com/BitMavrick
* */

package com.bitmavrick.lumolight.ui.tab.screenFlash

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bitmavrick.lumolight.data.UserPreferencesRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ScreenFlashViewModel @Inject constructor(
    private val userPreferencesRepository: UserPreferencesRepository
) : ViewModel() {
    private val _uiState = MutableStateFlow(ScreenFlashUiState())
    val uiState : StateFlow<ScreenFlashUiState> = _uiState

    init {
        viewModelScope.launch {
            // * Code from here
        }
    }

    fun onEvent(event: ScreenFlashUiEvent){
        when(event){
            is ScreenFlashUiEvent.UpdateScreenFlashDuration -> {
                // updateScreenFlashDuration(event.index)
            }

            is ScreenFlashUiEvent.UpdateScreenFlashColor -> {
                // updateScreenFlashColor(event.index)
            }

            is ScreenFlashUiEvent.UpdateScreenFlashBrightness -> {
                //
            }
        }

    }


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