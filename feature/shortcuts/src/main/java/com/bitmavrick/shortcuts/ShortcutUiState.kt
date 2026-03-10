package com.bitmavrick.shortcuts

import com.bitmavrick.data.domain.model.LumoFlash

data class ShortcutUiState (
    val isLoading: Boolean = false,
    val pinnedFlashList: List<LumoFlash> = emptyList(),
    val quickButtonIndex: Int = 0,
    val userMessage: String? = null
)