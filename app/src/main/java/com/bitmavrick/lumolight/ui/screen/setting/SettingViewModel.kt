package com.bitmavrick.lumolight.ui.screen.setting

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bitmavrick.lumolight.data.UserPreferencesRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
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
                userPreferencesRepository.saveQuickAction,
                userPreferencesRepository.showSosButton
            ){ saveQuickAction, showSosButton ->
                SettingUiState(
                    saveQuickAction = saveQuickAction,
                    showSosButton = showSosButton
                )
            }.collect{newState ->
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