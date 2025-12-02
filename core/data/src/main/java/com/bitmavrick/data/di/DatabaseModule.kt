package com.bitmavrick.data.di

import android.content.Context
import androidx.room.Room
import com.bitmavrick.data.domain.repository.ScreenColorRepository
import com.bitmavrick.data.domain.repository.ScreenDurationRepository
import com.bitmavrick.data.local.AppDatabase
import com.bitmavrick.data.local.dao.ScreenColorDao
import com.bitmavrick.data.local.dao.ScreenDurationDao
import com.bitmavrick.data.repository.ScreenColorRepositoryImpl
import com.bitmavrick.data.repository.ScreenDurationRepositoryImpl
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
        ).build()
    }

    @Provides
    fun provideScreenColorDao(db: AppDatabase): ScreenColorDao = db.screenColorDao()

    @Provides
    fun provideScreenDurationDao(db: AppDatabase): ScreenDurationDao = db.screenDurationDao()

    @Provides
    fun provideScreenColorRepository(db: AppDatabase): ScreenColorRepository {
        return ScreenColorRepositoryImpl(db.screenColorDao())
    }

    @Provides
    fun provideScreenDurationRepository(db: AppDatabase): ScreenDurationRepository {
        return ScreenDurationRepositoryImpl(db.screenDurationDao())
    }
}