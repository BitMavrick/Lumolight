package com.bitmavrick.shortcuts

import com.bitmavrick.data.domain.model.LumoFlash

sealed class ShortcutUiEvent {
    data class UpdateQuickButtonIndex(val index: Int) : ShortcutUiEvent()
    data class UpdateFlash(val flash: LumoFlash) : ShortcutUiEvent()
    data class DeleteFlash(val flash: LumoFlash) : ShortcutUiEvent()
    data class AddToHome(val flash: LumoFlash) : ShortcutUiEvent()
    data class RemoveFromHome(val flash: LumoFlash) : ShortcutUiEvent()
    data class ReorderPinnedFlash(val newList: List<LumoFlash>) : ShortcutUiEvent()
    object Refresh : ShortcutUiEvent()
    object UserMessageShown : ShortcutUiEvent()
}