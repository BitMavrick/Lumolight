/* *
* Lumolight :: Open-source program under GPL-3.0 :: Copyright - BitMavrick :: https://github.com/BitMavrick
* */

package com.bitmavrick.lumolight.ui.screen.setting

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bitmavrick.lumolight.data.UserPreferencesRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SettingViewModel @Inject constructor (
    private val userPreferencesRepository: UserPreferencesRepository
) : ViewModel(){
    private val _uiState = MutableStateFlow(SettingUiState())
    val uiState : StateFlow<SettingUiState> = _uiState.asStateFlow()

    init {
        viewModelScope.launch {
            combine(
                userPreferencesRepository.saveQuickAction,
                userPreferencesRepository.showSosButton,
                userPreferencesRepository.enableHapticStatus,
            ){saveQuickAction, showSosButton, haptic->
                SettingUiState(
                    saveQuickAction = saveQuickAction,
                    showSosButton = showSosButton,
                    hapticStatus = haptic
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

            is SettingUiEvent.UpdateHapticStatus -> {
                updateHapticStatus(event.status)
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

    private fun updateHapticStatus(value: Boolean){
        viewModelScope.launch {
            userPreferencesRepository.updateHapticStatus(value)
        }
    }
}

