package com.bitmavrick.data.local.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import androidx.room.Update
import com.bitmavrick.data.local.entity.ScreenColorEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface ScreenColorDao {
    @Query("SELECT * FROM ScreenColor ORDER BY orderIndex ASC")
    fun getAllScreenColors(): Flow<List<ScreenColorEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertColor(color: ScreenColorEntity)

    @Update
    suspend fun updateColor(color: ScreenColorEntity)

    @Delete
    suspend fun deleteColor(color: ScreenColorEntity)

    @Transaction
    suspend fun reorderColors(newList: List<ScreenColorEntity>) {
        newList.forEachIndexed { index, color ->
            updateColor(color.copy(orderIndex = index))
        }
    }
}