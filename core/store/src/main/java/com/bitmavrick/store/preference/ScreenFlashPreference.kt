package com.bitmavrick.store.preference

import android.content.Context
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.floatPreferencesKey
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.map

private const val SCREEN_PREFERENCES_NAME = "screen_preferences"

val Context.screenFlashDataStore by preferencesDataStore(name = SCREEN_PREFERENCES_NAME)

class ScreenFlashPreferenceRepository(
    context: Context
) {
    companion object {
        private val SCREEN_COLOR_HUE = floatPreferencesKey("screen_color_hue")
        private val SCREEN_COLOR_SATURATION = floatPreferencesKey("screen_color_saturation")
        private val SCREEN_COLOR_VALUE = floatPreferencesKey("screen_color_value")
        private val SCREEN_COLOR_PRESET_SELECTION = booleanPreferencesKey("screen_color_preset_selection")
        private val SCREEN_COLOR_PRESET_INDEX = intPreferencesKey("screen_color_preset_index")
        private val SCREEN_BRIGHTNESS = intPreferencesKey("screen_brightness")
        private val SCREEN_DURATION = intPreferencesKey("screen_duration")
    }

    private val dataStore = context.screenFlashDataStore

    val screenFlashPreferencesFlow = dataStore.data
        .map { preferences ->
            ScreenFlashPreferences(
                screenColorHue = preferences[SCREEN_COLOR_HUE] ?: 0f,
                screenColorSat = preferences[SCREEN_COLOR_SATURATION] ?: 0f,
                screenColorVal = preferences[SCREEN_COLOR_VALUE] ?: 0f,
                screenColorPresetSelection = preferences[SCREEN_COLOR_PRESET_SELECTION] ?: true,
                screenColorPresetIndex = preferences[SCREEN_COLOR_PRESET_INDEX] ?: 0,
                brightness = preferences[SCREEN_BRIGHTNESS] ?: 1,
                duration = preferences[SCREEN_DURATION] ?: 1
            )
        }

    suspend fun updateScreenColor(
        hue: Float,
        saturation: Float,
        value: Float
    ){
        dataStore.edit { preferences ->
            preferences[SCREEN_COLOR_HUE] = hue
            preferences[SCREEN_COLOR_SATURATION] = saturation
            preferences[SCREEN_COLOR_VALUE] = value
            preferences[SCREEN_COLOR_PRESET_SELECTION] = false
        }
    }

    suspend fun updateScreenColorPresetIndex(index: Int) {
        dataStore.edit { preferences ->
            preferences[SCREEN_COLOR_PRESET_INDEX] = index
            preferences[SCREEN_COLOR_PRESET_SELECTION] = true
        }
    }

    suspend fun updateBrightness(value: Int){
        dataStore.edit { preferences ->
            preferences[SCREEN_BRIGHTNESS] = value
        }
    }

    suspend fun updateDuration(value: Int){
        dataStore.edit { preferences ->
            preferences[SCREEN_DURATION] = value
        }
    }
}

data class ScreenFlashPreferences(
    val screenColorHue: Float = 0f,
    val screenColorSat: Float = 0f,
    val screenColorVal: Float = 0f,
    val screenColorPresetSelection: Boolean = true,
    val screenColorPresetIndex: Int = 0,
    val brightness: Int = 1,
    val duration: Int = 1
)