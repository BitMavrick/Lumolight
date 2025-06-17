/* *
* Lumolight :: Open-source program under GPL-3.0 :: Copyright - BitMavrick :: https://github.com/BitMavrick
* */


package com.bitmavrick.lumolight.ui.screen.setting.appearance

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bitmavrick.lumolight.data.UserPreferencesRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import com.bitmavrick.lumolight.util.toAppearance
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class ThemeViewModel @Inject constructor (
    private val userPreferencesRepository: UserPreferencesRepository
) : ViewModel() {
    private val _uiState = MutableStateFlow(ThemeUiState())
    val uiState : StateFlow<ThemeUiState> = _uiState.asStateFlow()

    init {
        viewModelScope.launch {
            combine(
                userPreferencesRepository.appearance,
                userPreferencesRepository.dynamicTheme,
                userPreferencesRepository.oledTheme,
            ){ appearance, dynamicTheme, oledTheme ->
                ThemeUiState(
                    appearance = appearance.toAppearance(),
                    dynamicTheme = dynamicTheme,
                    oledTheme = oledTheme
                )
            }.collect{ newState ->
                _uiState.value = newState
            }
        }
    }

    fun onEvent(event: ThemeUiEvent){
        when(event){
            is ThemeUiEvent.UpdateThemeDialog -> {
                updateThemeDialog(event.visible)
            }

            is ThemeUiEvent.UpdateAppearance -> {
                updateAppearance(event.appearance)
            }

            is ThemeUiEvent.UpdateDynamicTheme -> {
                updateDynamicTheme(event.dynamicTheme)
            }

            is ThemeUiEvent.UpdateOledTheme -> {
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
}