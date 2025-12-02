package com.bitmavrick.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.bitmavrick.data.local.dao.ScreenColorDao
import com.bitmavrick.data.local.dao.ScreenDurationDao
import com.bitmavrick.data.local.entity.ScreenColorEntity
import com.bitmavrick.data.local.entity.ScreenDurationEntity

@Database(
    entities = [
        ScreenColorEntity::class,
        ScreenDurationEntity::class
    ],
    version = 1,
    exportSchema = true
)
abstract class AppDatabase : RoomDatabase() {
    abstract fun screenColorDao(): ScreenColorDao
    abstract fun screenDurationDao(): ScreenDurationDao
}