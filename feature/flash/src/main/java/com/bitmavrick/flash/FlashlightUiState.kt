package com.bitmavrick.flash

import com.bitmavrick.data.domain.model.LumoFlash

data class FlashlightUiState (
    val isLoading: Boolean = false,
    val rearFlashList: List<LumoFlash> = emptyList(),
    val userMessage: String? = null
)