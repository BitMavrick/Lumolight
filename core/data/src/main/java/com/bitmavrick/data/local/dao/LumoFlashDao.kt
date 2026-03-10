package com.bitmavrick.data.local.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import androidx.room.Update
import com.bitmavrick.data.local.entity.LumoFlashEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface LumoFlashDao {
    @Query("SELECT * FROM LumoFlash WHERE id = :id")
    suspend fun getFlashById(id: Int): LumoFlashEntity?

    @Query("SELECT * FROM LumoFlash WHERE flashType = 0 ORDER BY primaryOrderIndex ASC, id DESC")
    fun getAllScreenFlash(): Flow<List<LumoFlashEntity>>

    @Query("SELECT * FROM LumoFlash WHERE flashType = 1 ORDER BY primaryOrderIndex ASC, id DESC")
    fun getAllBothFlash(): Flow<List<LumoFlashEntity>>

    @Query("SELECT * FROM LumoFlash WHERE flashType = 2 ORDER BY primaryOrderIndex ASC, id DESC")
    fun getAllFlash(): Flow<List<LumoFlashEntity>>

    @Query("SELECT * FROM LumoFlash WHERE isPinned = 1 ORDER BY pinnedOrderIndex ASC")
    fun getAllPinnedFlash(): Flow<List<LumoFlashEntity>>

    @Query("UPDATE LumoFlash SET pinnedOrderIndex = pinnedOrderIndex + 1 WHERE isPinned = 1")
    suspend fun incrementPinnedIndices()

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addNewFlash(flash: LumoFlashEntity)

    @Update
    suspend fun updateFlash(flash: LumoFlashEntity)

    @Transaction
    suspend fun reorderFlash(newList: List<LumoFlashEntity>) {
        newList.forEachIndexed { index, entity ->
            updateFlash( entity.copy( primaryOrderIndex = index ))
        }
    }

    @Transaction
    suspend fun reorderPinnedFlash(newList: List<LumoFlashEntity>) {
        newList.forEachIndexed { index, entity ->
            updateFlash( entity.copy( pinnedOrderIndex = index ))
        }
    }

    @Delete
    suspend fun deleteFlash(flash: LumoFlashEntity)
}