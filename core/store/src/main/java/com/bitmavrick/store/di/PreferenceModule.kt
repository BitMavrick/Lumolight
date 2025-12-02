package com.bitmavrick.store.di

import android.content.Context
import com.bitmavrick.store.preference.FlashlightPreferenceRepository
import com.bitmavrick.store.preference.ScreenFlashPreferenceRepository
import com.bitmavrick.store.preference.SettingsPreferenceRepository
import com.bitmavrick.store.preference.ShortcutPreferenceRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
class PreferenceModule {

    @Provides
    fun provideSettingsPreferenceRepository(@ApplicationContext context: Context) =
        SettingsPreferenceRepository(context)

    @Provides
    fun provideShortcutPreferenceRepository(@ApplicationContext context: Context) =
        ShortcutPreferenceRepository(context)

    @Provides
    fun provideScreenFlashPreferenceRepository(@ApplicationContext context: Context) =
        ScreenFlashPreferenceRepository(context)

    @Provides
    fun provideFlashlightPreferenceRepository(@ApplicationContext context: Context) =
        FlashlightPreferenceRepository(context)
}