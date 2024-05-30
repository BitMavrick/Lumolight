package com.bitmavrick.lumolight.ui.screen.setting

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bitmavrick.lumolight.data.UserPreferencesRepository
import com.bitmavrick.lumolight.util.toAppearance
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SettingViewModel @Inject constructor (
    private val userPreferencesRepository: UserPreferencesRepository
) : ViewModel(){
    private val _uiState = MutableStateFlow(SettingUiState())
    val uiState : StateFlow<SettingUiState> = _uiState

    init {
        viewModelScope.launch {
            combine(
                userPreferencesRepository.appearance,
                userPreferencesRepository.saveQuickAction,
                userPreferencesRepository.showSosButton
            ){ appearance, saveQuickAction, showSosButton ->
                SettingUiState(
                    appearance = appearance.toAppearance(),
                    saveQuickAction = saveQuickAction,
                    showSosButton = showSosButton
                )
            }.collect{ newState ->
                _uiState.value = newState
            }
        }
    }

    fun onEvent(event: SettingUiEvent){
        when(event){
            is SettingUiEvent.UpdateSaveQuickActionSetting -> {
                updateQuickActionPreference(event.saveQuickAction)
            }

            is SettingUiEvent.UpdateShowSosButtonPreference -> {
                updateShowSosButtonPreference(event.sosButtonPreference)
            }

            is SettingUiEvent.UpdateThemeDialog -> {
                updateThemeDialog(event.visible)
            }

            is SettingUiEvent.UpdateAppearance -> {
                updateAppearance(event.appearance)
            }
        }
    }

    private fun updateThemeDialog(value: Boolean){
        _uiState.update {
            it.copy(
                showThemeDialog = value
            )
        }
    }

    private fun updateAppearance(appearance: Appearance){
        _uiState.update {
            it.copy(
                appearance = appearance
            )
        }
        viewModelScope.launch {
            userPreferencesRepository.updateAppearance(appearance)
        }
    }

    private fun updateQuickActionPreference(value: Boolean){
        viewModelScope.launch {
            userPreferencesRepository.updateQuickActionPreference(value)
            userPreferencesRepository.resetSegmentedButtonValue()
        }
    }

    private fun updateShowSosButtonPreference(value: Boolean){
        viewModelScope.launch {
            userPreferencesRepository.updateShowSosButtonPreference(value)
        }
    }
}

