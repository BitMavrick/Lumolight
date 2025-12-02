package com.bitmavrick.store.preference

import android.content.Context
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.map

private const val FLASHLIGHT_PREFERENCE_NAME = "flashlight_preference"

val Context.flashlightDataStore by preferencesDataStore(name = FLASHLIGHT_PREFERENCE_NAME)

class FlashlightPreferenceRepository(
    context: Context
) {
    companion object {
        private val FLASHLIGHT_BPM_RATE = intPreferencesKey("flashlight_bpm_rate")
        private val FLASHLIGHT_DURATION = intPreferencesKey("flashlight_duration")
        private val FLASHLIGHT_INTENSITY = intPreferencesKey("flashlight_intensity")
    }

    private val dataStore = context.flashlightDataStore

    val flashLightPreferenceFlow = dataStore.data.map { preferences ->
        FlashlightPreferences(
            bpm = preferences[FLASHLIGHT_BPM_RATE] ?: 0,
            duration = preferences[FLASHLIGHT_DURATION] ?: 1,
            intensity = preferences[FLASHLIGHT_INTENSITY] ?: 1
        )
    }

    suspend fun updateFlashlightBpmRate(bpm: Int){
        dataStore.edit { preferences ->
            preferences[FLASHLIGHT_BPM_RATE] = bpm
        }
    }

    suspend fun updateFlashlightDuration(duration: Int){
        dataStore.edit { preferences ->
            preferences[FLASHLIGHT_DURATION] = duration
        }
    }

    suspend fun updateFlashlightIntensity(intensity: Int){
        dataStore.edit { preferences ->
            preferences[FLASHLIGHT_INTENSITY] = intensity
        }
    }
}

data class FlashlightPreferences(
    val bpm: Int = 0,
    val duration: Int = 1,
    val intensity: Int = 1
)