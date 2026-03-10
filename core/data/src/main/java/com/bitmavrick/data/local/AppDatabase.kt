package com.bitmavrick.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.bitmavrick.data.local.dao.LumoFlashDao
import com.bitmavrick.data.local.entity.LumoFlashEntity

@Database(
    entities = [LumoFlashEntity::class],
    version = 2,
    exportSchema = true
)
abstract class AppDatabase : RoomDatabase() {
    abstract fun lumoFlashDao(): LumoFlashDao
}