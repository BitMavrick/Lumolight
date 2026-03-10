package com.bitmavrick.data.domain.repository

import com.bitmavrick.data.domain.model.LumoFlash
import kotlinx.coroutines.flow.Flow

interface LumoFlashRepository {
    suspend fun getFlashById(id: Int): LumoFlash?
    fun getAllScreenFlash(): Flow<List<LumoFlash>>
    fun getAllFlash(): Flow<List<LumoFlash>>
    fun getAllBothFlash(): Flow<List<LumoFlash>>
    fun getAllPinnedFlash(): Flow<List<LumoFlash>>

    suspend fun addNewFlash(flash: LumoFlash)
    suspend fun updateFlash(flash: LumoFlash)
    suspend fun pinnedFlash(flash: LumoFlash)
    suspend fun unPinnedFlash(flash: LumoFlash)

    suspend fun reorderFlash(newList: List<LumoFlash>)
    suspend fun reorderPinnedFlash(newList: List<LumoFlash>)
    suspend fun deleteFlash(flash: LumoFlash)
}