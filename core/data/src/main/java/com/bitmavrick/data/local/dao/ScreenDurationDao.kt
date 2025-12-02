package com.bitmavrick.data.local.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import androidx.room.Update
import com.bitmavrick.data.local.entity.ScreenDurationEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface ScreenDurationDao {
    @Query("SELECT * FROM ScreenDuration ORDER BY orderIndex ASC")
    fun getAllScreenDurations() : Flow<List<ScreenDurationEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertScreenDuration(screenDuration: ScreenDurationEntity)

    @Update
    suspend fun updateScreenDuration(screenDuration: ScreenDurationEntity)

    @Delete
    suspend fun deleteScreenDuration(screenDuration: ScreenDurationEntity)

    @Transaction
    suspend fun reorderScreenDurations(newList: List<ScreenDurationEntity>) {
        newList.forEachIndexed { index, duration ->
            updateScreenDuration(duration.copy(orderIndex = index))
        }
    }
}