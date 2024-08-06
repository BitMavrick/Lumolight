/* *
* Lumolight :: Open-source program under GPL-3.0 :: Copyright - BitMavrick :: https://github.com/BitMavrick
* */

package com.bitmavrick.lumolight.activity.core

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bitmavrick.lumolight.data.UserPreferencesRepository
import com.bitmavrick.lumolight.ui.screen.setting.Appearance
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CoreViewModel @Inject constructor (
    private val userPreferencesRepository: UserPreferencesRepository
) : ViewModel(){

    private val _uiState = MutableStateFlow(CoreUiState())
    val uiState : StateFlow<CoreUiState> = _uiState.asStateFlow()

    init {
        viewModelScope.launch {
            combine(
                userPreferencesRepository.appLoading,
                userPreferencesRepository.appearance,
                userPreferencesRepository.dynamicTheme,
                userPreferencesRepository.oledTheme
            ){ appLoading, appearance, dynamicTheme, oledTheme ->
                CoreUiState(
                    appLoadingStatus = !appLoading,
                    appearance = getAppearance(appearance),
                    dynamicTheme = dynamicTheme,
                    oledTheme = oledTheme
                )
            }.collect{ newState ->
                _uiState.value = newState
            }
        }
    }

    private fun getAppearance(value : String) : Appearance {
        return when(value){
            Appearance.DEFAULT.name -> {
                Appearance.DEFAULT
            }

            Appearance.LIGHT.name -> {
                Appearance.LIGHT
            }

            Appearance.DARK.name -> {
                Appearance.DARK
            }

            else -> {
                Appearance.DEFAULT
            }
        }
    }
}