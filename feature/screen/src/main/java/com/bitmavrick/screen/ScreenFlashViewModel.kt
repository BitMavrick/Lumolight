package com.bitmavrick.screen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bitmavrick.store.preference.ScreenFlashPreferenceRepository
import com.bitmavrick.store.preference.SettingsPreferenceRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ScreenFlashViewModel @Inject constructor(
    private val screenFlashPreferenceRepository: ScreenFlashPreferenceRepository,
    private val settingsPreferenceRepository: SettingsPreferenceRepository
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

    private fun syncUpdates(){
        scope.launch {
            combine(
                screenFlashPreferenceRepository.screenFlashPreferencesFlow,
                settingsPreferenceRepository.settingsPreferenceFlow
            ){ screenFlash,settings ->
                ScreenFlashUiState(
                    screenColorHue = screenFlash.screenColorHue,
                    screenColorSat = screenFlash.screenColorSat,
                    screenColorVal = screenFlash.screenColorVal,
                    screenColorPresetSelection = screenFlash.screenColorPresetSelection,
                    screenColorPresetIndex = screenFlash.screenColorPresetIndex,
                    brightness = screenFlash.brightness,
                    duration = screenFlash.duration,
                    volumeButtonControls = settings.volumeButtonControls
                )
            }.collect {
                _uiState.value = it
            }
        }
    }

    init {
        syncUpdates()
    }
}