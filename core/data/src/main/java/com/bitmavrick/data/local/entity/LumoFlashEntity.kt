package com.bitmavrick.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "LumoFlash")
data class LumoFlashEntity (
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val title: String,
    val flashType: Int, // * 0 = SCREEN, 1 = DUAL, 2 = REAR
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