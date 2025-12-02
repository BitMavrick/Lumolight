package com.bitmavrick.data.domain.repository

import com.bitmavrick.data.domain.model.ScreenDuration
import kotlinx.coroutines.flow.Flow

interface ScreenDurationRepository {
    fun getAllScreenDurations(): Flow<List<ScreenDuration>>
    suspend fun insertScreenDuration(screenDuration: ScreenDuration)
    suspend fun updateScreenDuration(screenDuration: ScreenDuration)
    suspend fun reorder(newList: List<ScreenDuration>)
    suspend fun deleteScreenDuration(screenDuration: ScreenDuration)
}