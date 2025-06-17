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
import kotlinx.coroutines.flow.combine
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
            combine(
                userPreferencesRepository.screenFlashBrightnessIndex,
                userPreferencesRepository.screenFlashDurationIndex,
                userPreferencesRepository.screenFlashColorIndex
            ) { brightnessIndex, durationIndex, colorIndex ->
                ScreenFlashUiState(
                    screenFlashBrightnessIndex = brightnessIndex,
                    screenFlashDurationIndex = durationIndex,
                    screenFlashColorIndex = colorIndex
                )
            }.collect { newState ->
                _uiState.value = newState
            }
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
            )
        }

        viewModelScope.launch {
            userPreferencesRepository.updateScreenFlashDurationIndex(index)
        }
    }

    private fun updateScreenFlashColor(index : Int){
        _uiState.update {
            it.copy(
                screenFlashColorIndex = index,
            )
        }

        viewModelScope.launch {
            userPreferencesRepository.updateScreenFlashColorIndex(index)
        }
    }

    private fun updateScreenFlashBrightness(index: Int){
        _uiState.update {
            it.copy(
                screenFlashBrightnessIndex = index,
            )
        }

        viewModelScope.launch {
            userPreferencesRepository.updateScreenFlashBrightnessIndex(index)
        }
    }
}