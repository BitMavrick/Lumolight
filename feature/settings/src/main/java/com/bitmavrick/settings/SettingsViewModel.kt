package com.bitmavrick.settings

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bitmavrick.store.preference.SettingsPreferenceRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SettingsViewModel @Inject constructor(
    private val settingsPreferenceRepository: SettingsPreferenceRepository
) : ViewModel() {
    val scope = viewModelScope

    private val _uiState = MutableStateFlow(SettingsUiState())
    val uiState: StateFlow<SettingsUiState> = _uiState

    fun onEvent(event: SettingsUiEvent){
        when(event){
            is SettingsUiEvent.UpdateDarkTheme -> {
                updateDarkTheme(event.theme)
            }

            is SettingsUiEvent.UpdateDynamicTheme -> {
                updateDynamicTheme(event.dynamicTheme)
            }

            is SettingsUiEvent.UpdateAmoledTheme -> {
                updateAmoledTheme(event.amoled)
            }
        }
    }

    private fun updateDarkTheme(darkTheme: Int){
        scope.launch {
            settingsPreferenceRepository.updateDarkTheme(darkTheme)
        }
    }

    private fun updateDynamicTheme(dynamicTheme: Boolean){
        scope.launch {
            settingsPreferenceRepository.updateDynamicTheme(dynamicTheme)
        }
    }

    private fun updateAmoledTheme(amoledTheme: Boolean){
        scope.launch {
            settingsPreferenceRepository.updateAmoledTheme(amoledTheme)
        }
    }

    init {
        scope.launch {
            settingsPreferenceRepository.settingsPreferenceFlow.collect {  preference ->
                _uiState.value = SettingsUiState(
                    isLoaded = preference.loaded,
                    darkTheme = preference.darkTheme,
                    dynamicTheme = preference.dynamicTheme,
                    amoledTheme = preference.amoledTheme
                )
            }
        }
    }
}