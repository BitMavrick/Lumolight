package com.bitmavrick.shortcuts

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bitmavrick.data.domain.model.LumoFlash
import com.bitmavrick.data.domain.repository.LumoFlashRepository
import com.bitmavrick.store.preference.ShortcutPreferenceRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ShortcutViewModel @Inject constructor(
    private val lumoFlashRepository: LumoFlashRepository,
    private val shortcutPreferenceRepository: ShortcutPreferenceRepository
) : ViewModel() {
    private val _isLoading = MutableStateFlow(false)
    private val _userMessage = MutableStateFlow<String?>(null)

    val uiState: StateFlow<ShortcutUiState> = combine(
        _isLoading,
        lumoFlashRepository.getAllPinnedFlash(),
        shortcutPreferenceRepository.shortcutPreferenceFlow,
        _userMessage
    ){ isLoading, list, index, userMessage ->
        ShortcutUiState(
            isLoading = isLoading,
            pinnedFlashList = list,
            quickButtonIndex = index.quickButtonIndex,
            userMessage = userMessage
        )
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5_000),
        initialValue = ShortcutUiState(isLoading = true)
    )

    fun onEvent(event: ShortcutUiEvent){
        when(event){
            is ShortcutUiEvent.UpdateQuickButtonIndex -> {
                updateQuickButtonIndex(event.index)
            }
            is ShortcutUiEvent.UpdateFlash -> updateFlash(event.flash)
            is ShortcutUiEvent.DeleteFlash -> deleteFlash(event.flash)
            is ShortcutUiEvent.AddToHome -> addToHome(event.flash)
            is ShortcutUiEvent.RemoveFromHome -> removeFromHome(event.flash)
            is ShortcutUiEvent.ReorderPinnedFlash -> reorder(event.newList)
            is ShortcutUiEvent.Refresh -> refresh()
            is ShortcutUiEvent.UserMessageShown -> userMessageShown()
        }
    }

    private fun updateQuickButtonIndex(index: Int){
        viewModelScope.launch {
            shortcutPreferenceRepository.updateQuickButtonIndex(index)
        }
    }

    private fun updateFlash(flash: LumoFlash){
        viewModelScope.launch {
            _isLoading.update { true }

            try {
                lumoFlashRepository.updateFlash(flash)
                // _userMessage.update { "Flash updated successfully" }
            }catch (e: Exception){
                _userMessage.update { "Error while updating this flash: ${e.message}" }
            } finally {
                _isLoading.update { false }
            }
        }
    }

    private fun deleteFlash(flash: LumoFlash){
        viewModelScope.launch {
            _isLoading.update { true }

            try {
                lumoFlashRepository.deleteFlash(flash)
               // _userMessage.update { "Flash deleted successfully" }
            } catch (e: Exception){
                _userMessage.update { "Error while deleting this flash: ${e.message}" }
            } finally {
                _isLoading.update { false }
            }
        }
    }

    private fun addToHome(flash: LumoFlash){
        viewModelScope.launch {
            _isLoading.update { true }

            try{
                lumoFlashRepository.pinnedFlash(flash)
               //  _userMessage.update { "Flash pinned successfully" }
            } catch (e: Exception){
                _userMessage.update { "Error while adding to home: ${e.message}" }
            } finally {
                _isLoading.update { false }
            }
        }
    }

    private fun removeFromHome(flash: LumoFlash){
        viewModelScope.launch {
            _isLoading.update { true }

            try{
                lumoFlashRepository.unPinnedFlash(flash)
               //  _userMessage.update { "Flash unPinned successfully" }
            } catch (e: Exception){
                _userMessage.update { "Error while removing from home: ${e.message}" }
            } finally {
                _isLoading.update { false }
            }
        }
    }

    private fun reorder(newOrder: List<LumoFlash>){
        viewModelScope.launch {
            lumoFlashRepository.reorderPinnedFlash(newOrder)
        }
    }

    private fun refresh(){
        viewModelScope.launch {
            _isLoading.update { true }
            try {
                delay(1000)
               // _userMessage.update { "Refreshed successfully" }
            } catch (e: Exception) {
                _userMessage.update { "Error refreshing: ${e.message}" }
            } finally {
                _isLoading.update { false }
            }
        }
    }

    private fun userMessageShown() {
        _userMessage.update { null }
    }
}