package com.bitmavrick.data.domain.repository

import com.bitmavrick.data.domain.model.ScreenColor
import kotlinx.coroutines.flow.Flow

interface ScreenColorRepository {
    fun getAllScreenColors(): Flow<List<ScreenColor>>
    suspend fun insertColor(color: ScreenColor)
    suspend fun updateColor(color: ScreenColor)
    suspend fun reorder(newList: List<ScreenColor>)
    suspend fun deleteColor(color: ScreenColor)
}