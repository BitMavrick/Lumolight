package com.bitmavrick.lumolight.activity.core

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bitmavrick.lumolight.data.UserPreferencesRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CoreViewModel @Inject constructor (
    private val userPreferencesRepository: UserPreferencesRepository
) : ViewModel(){

    private val _uiState = MutableStateFlow(CoreUiState())
    val uiState : StateFlow<CoreUiState> = _uiState

    init {
        viewModelScope.launch {
            delay(300)
            userPreferencesRepository.appLoading.collect{value ->
                _uiState.update {
                    it.copy(
                        appLoading = value
                    )
                }
            }
        }
    }
}