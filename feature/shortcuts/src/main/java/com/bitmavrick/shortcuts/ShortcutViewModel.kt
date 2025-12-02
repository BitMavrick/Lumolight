package com.bitmavrick.shortcuts

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bitmavrick.store.preference.SettingsPreferenceRepository
import com.bitmavrick.store.preference.ShortcutPreferenceRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ShortcutViewModel @Inject constructor(
    private val shortcutPreferenceRepository: ShortcutPreferenceRepository,
    private val settingsPreferenceRepository: SettingsPreferenceRepository
) : ViewModel() {
    val scope = viewModelScope

    private val _uiState = MutableStateFlow(ShortcutUiState())
    val uiState: StateFlow<ShortcutUiState> = _uiState.asStateFlow()

    fun onEvent(event: ShortcutUiEvent){
        when(event){
            is ShortcutUiEvent.UpdateQuickButtonIndex -> {
                updateQuickButtonIndex(event.index)
            }
        }
    }

    private fun updateQuickButtonIndex(index: Int){
        scope.launch {
            shortcutPreferenceRepository.updateQuickButtonIndex(index)
        }
    }

    init {
        scope.launch {
            combine(
                settingsPreferenceRepository.settingsPreferenceFlow,
                shortcutPreferenceRepository.shortcutPreferenceFlow
            ) { settings, shortcut ->
                ShortcutUiState(
                    quickButtonIndex = shortcut.quickButtonIndex
                )
            }.collect { state ->
                _uiState.value = state
            }
        }
    }
}