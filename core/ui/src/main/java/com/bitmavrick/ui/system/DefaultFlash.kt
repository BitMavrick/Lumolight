package com.bitmavrick.ui.system

import com.bitmavrick.data.domain.model.LumoFlash

class DefaultFlash {
    companion object {
        val screenFlash = LumoFlash(
            title = "Screen Flash",
            flashType = 0,
            isPinned = false,
            duration = 60,
            infiniteDuration = true,
            screenColor = "#FFFFFF",
            screenBrightness = 90f,
            flashBpmRate = 0,
            flashIntensity = 1,
            pinnedOrderIndex = null
        )

        val dualFlash = LumoFlash(
            title = "Dual Flash",
            flashType = 1,
            isPinned = false,
            duration = 60,
            infiniteDuration = true,
            screenColor = "#FFFFFF",
            screenBrightness = 90f,
            flashBpmRate = 0,
            flashIntensity = 1,
            pinnedOrderIndex = null
        )

        val rearFlash = LumoFlash(
            title = "Rear Flash",
            flashType = 2,
            isPinned = false,
            duration = 60,
            infiniteDuration = true,
            screenColor = "#FFFFFF",
            screenBrightness = 10f,
            flashBpmRate = 0,
            flashIntensity = 1,
            pinnedOrderIndex = null
        )
    }
}