package com.bitmavrick.shortcuts

sealed class ShortcutUiEvent {
    data class UpdateQuickButtonIndex(val index: Int) : ShortcutUiEvent()
}