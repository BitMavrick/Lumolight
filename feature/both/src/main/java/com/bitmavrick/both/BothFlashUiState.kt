package com.bitmavrick.both

import com.bitmavrick.data.domain.model.LumoFlash

data class BothFlashUiState (
    val isLoading: Boolean = false,
    val dualFlashList: List<LumoFlash> = emptyList(),
    val userMessage: String? = null
)