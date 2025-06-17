/* *
* Lumolight :: Open-source program under GPL-3.0 :: Copyright - BitMavrick :: https://github.com/BitMavrick
* */

package com.bitmavrick.lumolight.ui.tab.screenFlash

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bitmavrick.lumolight.data.UserPreferencesRepository
import com.bitmavrick.lumolight.util.BrightnessValue
import com.bitmavrick.lumolight.util.ColorValue
import com.bitmavrick.lumolight.util.TimeDuration
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
                updateScreenFlashDuration(event.index)
            }

            is ScreenFlashUiEvent.UpdateScreenFlashColor -> {
                updateScreenFlashColor(event.index)
            }

            is ScreenFlashUiEvent.UpdateScreenFlashBrightness -> {
                updateScreenFlashBrightness(event.index)
            }
        }

    }


    private fun updateScreenFlashDuration(index: Int){
        _uiState.update {
            it.copy(
                screenFlashDurationIndex = index,
                //screenFlashDurationMin = TimeDuration.list[index].time
            )
        }
    }

    private fun updateScreenFlashColor(index : Int){
        _uiState.update {
            it.copy(
                screenFlashColorIndex = index,
                //screenFlashColorValue = ColorValue.list[index].code
            )
        }
    }

    private fun updateScreenFlashBrightness(index: Int){
        _uiState.update {
            it.copy(
                screenFlashBrightnessIndex = index,
                // screenFlashBrightnessValue = BrightnessValue.list[index].value
            )
        }
    }
}