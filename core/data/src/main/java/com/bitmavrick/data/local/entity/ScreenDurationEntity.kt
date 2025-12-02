package com.bitmavrick.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "ScreenDuration")
data class ScreenDurationEntity (
    @PrimaryKey(autoGenerate = true)
    val id : Int = 0,
    val duration : Int,
    val orderIndex : Int = 0,
)