package com.bitmavrick.data.domain.model

data class LumoFlash(
    val id: Int = 0,
    val title: String,
    val flashType: Int, // * 0 = SCREEN, 1 = BOTH, 2 = FLASH
    val isPinned: Boolean,
    val primaryOrderIndex: Int = 0,
    val pinnedOrderIndex: Int?,
    val duration: Int,
    val infiniteDuration: Boolean,
    val screenColor: String?,
    val screenBrightness: Float?,
    val flashBpmRate: Int?,
    val flashIntensity: Int?
)