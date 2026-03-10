package com.bitmavrick.data.repository

import android.util.Log
import com.bitmavrick.data.domain.model.LumoFlash
import com.bitmavrick.data.domain.repository.LumoFlashRepository
import com.bitmavrick.data.local.dao.LumoFlashDao
import com.bitmavrick.data.local.entity.LumoFlashEntity
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class LumoFlashRepositoryImpl @Inject constructor(
    private val dao: LumoFlashDao
) : LumoFlashRepository {
    override suspend fun getFlashById(id: Int): LumoFlash? {
        return dao.getFlashById(id)?.toLumoFlash()
    }

    override fun getAllScreenFlash(): Flow<List<LumoFlash>> {
        return dao.getAllScreenFlash().map { list ->
            list.map { it.toLumoFlash() }
        }
    }

    override fun getAllFlash(): Flow<List<LumoFlash>> {
        return dao.getAllFlash().map { list ->
            list.map { it.toLumoFlash() }
        }
    }

    override fun getAllBothFlash(): Flow<List<LumoFlash>> {
        return dao.getAllBothFlash().map { list ->
            list.map { it.toLumoFlash() }
        }
    }

    override fun getAllPinnedFlash(): Flow<List<LumoFlash>> {
        return dao.getAllPinnedFlash().map { list ->
            list.map { it.toLumoFlash() }
        }
    }

    override suspend fun addNewFlash(flash: LumoFlash) {
        dao.addNewFlash(flash.toLumoFlashEntity())
    }

    override suspend fun updateFlash(flash: LumoFlash) {
        dao.updateFlash(flash.toLumoFlashEntity())
    }

    override suspend fun pinnedFlash(flash: LumoFlash) {
        dao.incrementPinnedIndices()
        dao.updateFlash(flash.copy(isPinned = true, pinnedOrderIndex = 0).toLumoFlashEntity())
    }

    override suspend fun unPinnedFlash(flash: LumoFlash) {
        dao.updateFlash(flash.copy(isPinned = false, pinnedOrderIndex = null).toLumoFlashEntity())
    }

    override suspend fun reorderFlash(newList: List<LumoFlash>) {
        Log.d("REORDER", "REQ for reorder!")
        val reorderFlash = newList.mapIndexed { index, bothFlash ->
            bothFlash.toLumoFlashEntity(
                id = bothFlash.id,
                primaryOrderIndex = index
            )
        }

        dao.reorderFlash(reorderFlash)
    }

    override suspend fun reorderPinnedFlash(newList: List<LumoFlash>) {
        val reorderPinnedFlash = newList.mapIndexed { index, pinnedFlash ->
            pinnedFlash.toLumoFlashEntity(
                id = pinnedFlash.id,
                pinnedOrderIndex = index
            )
        }

        dao.reorderPinnedFlash(reorderPinnedFlash)
    }

    override suspend fun deleteFlash(flash: LumoFlash) {
        dao.deleteFlash(flash.toLumoFlashEntity())
    }

    private fun LumoFlashEntity.toLumoFlash(): LumoFlash {
        return LumoFlash(
            id = id,
            title = title,
            flashType = flashType,
            isPinned = isPinned,
            primaryOrderIndex = primaryOrderIndex,
            pinnedOrderIndex = pinnedOrderIndex,
            duration = duration,
            infiniteDuration = infiniteDuration,
            screenColor = screenColor,
            screenBrightness = screenBrightness,
            flashBpmRate = flashBpmRate,
            flashIntensity = flashIntensity
        )
    }

    private fun LumoFlash.toLumoFlashEntity(
        id: Int = this.id,
        primaryOrderIndex: Int = this.primaryOrderIndex,
        pinnedOrderIndex: Int? = this.pinnedOrderIndex
    ): LumoFlashEntity {
        return LumoFlashEntity(
            id = id,
            title = title,
            flashType = flashType,
            isPinned = isPinned,
            primaryOrderIndex = primaryOrderIndex,
            pinnedOrderIndex = pinnedOrderIndex,
            duration = duration,
            infiniteDuration = infiniteDuration,
            screenColor = screenColor,
            screenBrightness = screenBrightness,
            flashBpmRate = flashBpmRate,
            flashIntensity = flashIntensity
        )
    }
}