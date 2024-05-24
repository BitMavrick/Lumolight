package com.bitmavrick.lumolight.ui.screen.setting

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bitmavrick.lumolight.data.UserPreferencesRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SettingViewModel @Inject constructor (
    private val userPreferencesRepository: UserPreferencesRepository
) : ViewModel(){
    private val _uiState = MutableStateFlow(SettingUiState())
    val uiState : StateFlow<SettingUiState> = _uiState

    fun onEvent(event: SettingUiEvent){
        when(event){
            is SettingUiEvent.UpdateSaveQuickActionSetting -> {
                updateQuickActionPreference(event.saveQuickAction)
            }
        }
    }

    private fun updateQuickActionPreference(value: Boolean){
        viewModelScope.launch {
            userPreferencesRepository.updateQuickActionPreference(value)
        }
    }

    init {
        viewModelScope.launch {
            userPreferencesRepository.saveQuickAction.collect{ value ->
                _uiState.value = _uiState.value.copy(
                    saveQuickAction = value
                )
            }
        }
    }
}