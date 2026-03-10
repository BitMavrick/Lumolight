package com.bitmavrick.screen

import com.bitmavrick.data.domain.model.LumoFlash

data class ScreenFlashUiState (
    val isLoading: Boolean = false,
    val screenFlashList: List<LumoFlash> = emptyList(),
    val userMessage: String? = null
)