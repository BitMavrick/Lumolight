package com.bitmavrick.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "ScreenColor")
data class ScreenColorEntity (
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val title: String,
    val colorCode: String,
    val orderIndex: Int = 0
)