package com.bitmavrick.flash

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bitmavrick.store.preference.FlashlightPreferenceRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FlashlightViewModel @Inject constructor(
    private val flashlightPreferenceRepository: FlashlightPreferenceRepository
): ViewModel() {
    val scope = viewModelScope
    private val _uiState = MutableStateFlow(FlashlightUiState())
    val uiState: StateFlow<FlashlightUiState> = _uiState.asStateFlow()

    fun onEvent(event: FlashlightUiEvent){
        when(event){
            is FlashlightUiEvent.UpdateFlashlightBpmRate -> {
                updateFlashlightBpmRate(event.bpm)
            }
            is FlashlightUiEvent.UpdateFlashlightDuration -> {
                updateFlashlightDuration(event.duration)
            }
            is FlashlightUiEvent.UpdateFlashlightIntensity -> {
                updateFlashlightIntensity(event.intensity)
            }
        }
    }

    private fun updateFlashlightBpmRate(bpm: Int){
        scope.launch {
            flashlightPreferenceRepository.updateFlashlightBpmRate(bpm)
        }
    }

    private fun updateFlashlightDuration(duration: Int){
        scope.launch {
            flashlightPreferenceRepository.updateFlashlightDuration(duration)
        }
    }

    private fun updateFlashlightIntensity(intensity: Int){
        scope.launch {
            flashlightPreferenceRepository.updateFlashlightIntensity(intensity)
        }
    }

    init {
        scope.launch {
            flashlightPreferenceRepository.flashLightPreferenceFlow.collect { preferences ->
                _uiState.value = FlashlightUiState(
                    bpm = preferences.bpm,
                    duration = preferences.duration,
                    intensity = preferences.intensity
                )
            }
        }
    }
}