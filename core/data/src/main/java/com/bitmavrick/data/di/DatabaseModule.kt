package com.bitmavrick.data.di

import android.content.Context
import androidx.room.Room
import com.bitmavrick.data.domain.repository.LumoFlashRepository
import com.bitmavrick.data.local.AppDatabase
import com.bitmavrick.data.local.dao.LumoFlashDao
import com.bitmavrick.data.repository.LumoFlashRepositoryImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {
    @Singleton
    @Provides
    fun provideAppDatabase(@ApplicationContext context: Context): AppDatabase {
        return Room.databaseBuilder(
                context,
                AppDatabase::class.java,
                "lumolight_database"
            ).fallbackToDestructiveMigration(true).build()
    }

    @Provides
    fun provideLumoFlashDao(db: AppDatabase): LumoFlashDao = db.lumoFlashDao()

    @Provides
    fun lumoFlashRepository(db: AppDatabase): LumoFlashRepository {
        return LumoFlashRepositoryImpl(db.lumoFlashDao())
    }
}