/* *
* Lumolight :: Open-source program under GPL-3.0 :: Copyright - BitMavrick :: https://github.com/BitMavrick
* */

package com.bitmavrick.lumolight.ui.screen.setting

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bitmavrick.lumolight.data.UserPreferencesRepository
import com.bitmavrick.lumolight.util.toAppearance
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.update
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
                userPreferencesRepository.appearance,
                userPreferencesRepository.dynamicTheme,
                userPreferencesRepository.oledTheme,
                userPreferencesRepository.saveQuickAction,
                userPreferencesRepository.showSosButton
            ){ appearance, dynamicTheme, oledTheme, saveQuickAction, showSosButton ->
                SettingUiState(
                    appearance = appearance.toAppearance(),
                    dynamicTheme = dynamicTheme,
                    oledTheme = oledTheme,
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

            is SettingUiEvent.UpdateDynamicTheme -> {
                updateDynamicTheme(event.dynamicTheme)
            }

            is SettingUiEvent.UpdateOledTheme -> {
                updateOledTheme(event.oledTheme)
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

    private fun updateDynamicTheme(value: Boolean){
        _uiState.update {
            it.copy(
                dynamicTheme = value
            )
        }
        viewModelScope.launch {
            userPreferencesRepository.updateDynamicTheme(value)
        }
    }

    private fun updateOledTheme(value: Boolean){
        _uiState.update {
            it.copy(
                oledTheme = value
            )
        }
        viewModelScope.launch {
            userPreferencesRepository.updateOledTheme(value)
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

