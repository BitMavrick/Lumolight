package com.bitmavrick.screen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bitmavrick.store.preference.ScreenFlashPreferenceRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ScreenFlashViewModel @Inject constructor(
    private val screenFlashPreferenceRepository: ScreenFlashPreferenceRepository
): ViewModel() {
    val scope = viewModelScope
    private val _uiState = MutableStateFlow(ScreenFlashUiState())
    val uiState: StateFlow<ScreenFlashUiState> = _uiState.asStateFlow()

    fun onEvent(event: ScreenFlashUiEvent){
        when(event){
            is ScreenFlashUiEvent.UpdateScreenColor -> {
                updateScreenColor(event.hue, event.saturation, event.value)
            }
            is ScreenFlashUiEvent.UpdateScreenColorPreset -> {
                updateScreenColorPreset(event.index)
            }
            is ScreenFlashUiEvent.UpdateBrightness -> {
                updateBrightness(event.brightness)
            }
            is ScreenFlashUiEvent.UpdateDuration -> {
                updateDuration(event.duration)
            }
        }
    }

    private fun updateScreenColor(hue: Float, sat: Float, value: Float){
        scope.launch {
            screenFlashPreferenceRepository.updateScreenColor(
                hue = hue,
                saturation = sat,
                value = value
            )
        }
    }

    private fun updateScreenColorPreset(index: Int){
        scope.launch {
            screenFlashPreferenceRepository.updateScreenColorPresetIndex(index)
        }
    }

    private fun updateBrightness(value: Int){
        scope.launch {
            screenFlashPreferenceRepository.updateBrightness(value)
        }
    }

    private fun updateDuration(value: Int){
        scope.launch {
            screenFlashPreferenceRepository.updateDuration(value)
        }
    }

    init {
        scope.launch {
            screenFlashPreferenceRepository.screenFlashPreferencesFlow.collect { preferences ->
                _uiState.value = ScreenFlashUiState(
                    screenColorHue = preferences.screenColorHue,
                    screenColorSat = preferences.screenColorSat,
                    screenColorVal = preferences.screenColorVal,
                    screenColorPresetSelection = preferences.screenColorPresetSelection,
                    screenColorPresetIndex = preferences.screenColorPresetIndex,
                    brightness = preferences.brightness,
                    duration = preferences.duration
                )
            }
        }
    }
}