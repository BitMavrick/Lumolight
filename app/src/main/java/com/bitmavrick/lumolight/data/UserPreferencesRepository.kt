/* *
* Lumolight :: Open-source program under GPL-3.0 :: Copyright - BitMavrick :: https://github.com/BitMavrick
* */

package com.bitmavrick.lumolight.data

import android.content.Context
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import com.bitmavrick.lumolight.ui.screen.setting.appearance.Appearance
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

private const val USER_PREFERENCES_NAME = "user_preferences"
val Context.dataStore by preferencesDataStore(name = USER_PREFERENCES_NAME)

class UserPreferencesRepository(context: Context) {


    companion object {
        private val APPEARANCE_KEY = stringPreferencesKey("appearance")
        private val DYNAMIC_THEME_KEY = booleanPreferencesKey("dynamicTheme")
        private val OLED_THEME_KEY = booleanPreferencesKey("oledTheme")
        private val APP_LOADING_KEY = booleanPreferencesKey("appLoading")
        private val SAVE_QUICK_ACTION_KEY = booleanPreferencesKey("saveQuickAction")
        private val SHOW_SOS_BUTTON_KEY = booleanPreferencesKey("showSosButton")
        private val ENABLE_HAPTIC_STATUS_KEY = booleanPreferencesKey("enableHapticStatus")
        private val SEGMENTED_BUTTON_VALUE_KEY = intPreferencesKey("segmentedButtonValue")

        // * Screen flash
        private val SCREEN_FLASH_DURATION_INDEX_KEY = intPreferencesKey("screenFlashDurationIndex")
        private val SCREEN_FLASH_COLOR_INDEX_KEY = intPreferencesKey("screenFlashColorIndex")
        private val SCREEN_FLASH_BRIGHTNESS_INDEX_KEY = intPreferencesKey("screenFlashBrightnessIndex")

        // * Flashlight
        private val FLASHLIGHT_DURATION_INDEX_KEY = intPreferencesKey("flashlightDurationIndex")
        private val FLASHLIGHT_BPM_INDEX_KEY = intPreferencesKey("flashlightBpmIndex")
    }

    private val dataStore = context.dataStore

    val appearance: Flow<String> = dataStore.data
        .map { preferences ->
            preferences[APPEARANCE_KEY] ?: Appearance.DEFAULT.name
        }

    val dynamicTheme: Flow<Boolean> = dataStore.data
        .map { preferences ->
            preferences[DYNAMIC_THEME_KEY] ?: true
        }

    val oledTheme: Flow<Boolean> = dataStore.data
        .map { preferences ->
            preferences[OLED_THEME_KEY] ?: false
        }

    val appLoading: Flow<Boolean> = dataStore.data
        .map { preferences ->
            preferences[APP_LOADING_KEY] ?: true
        }

    val showSosButton: Flow<Boolean> = dataStore.data
        .map { preferences ->
            preferences[SHOW_SOS_BUTTON_KEY] ?: true
        }

    val enableHapticStatus: Flow<Boolean> = dataStore.data
        .map { preferences ->
            preferences[ENABLE_HAPTIC_STATUS_KEY] ?: false
        }

    val saveQuickAction: Flow<Boolean> = dataStore.data
        .map { preferences ->
            preferences[SAVE_QUICK_ACTION_KEY] ?: true
        }

    val segmentedButtonValue: Flow<Int> = dataStore.data
        .map { preferences ->
            preferences[SEGMENTED_BUTTON_VALUE_KEY] ?: 0
        }

    val screenFlashDurationIndex: Flow<Int> = dataStore.data
        .map { preferences ->
            preferences[SCREEN_FLASH_DURATION_INDEX_KEY] ?: 0
        }

    val screenFlashColorIndex: Flow<Int> = dataStore.data
        .map { preferences ->
            preferences[SCREEN_FLASH_COLOR_INDEX_KEY] ?: 0
        }

    val screenFlashBrightnessIndex: Flow<Int> = dataStore.data
        .map { preferences ->
            preferences[SCREEN_FLASH_BRIGHTNESS_INDEX_KEY] ?: 0
        }

    val flashlightDurationIndex: Flow<Int> = dataStore.data
        .map { preferences ->
            preferences[FLASHLIGHT_DURATION_INDEX_KEY] ?: 0
        }

    val flashlightBpmIndex: Flow<Int> = dataStore.data
        .map { preferences ->
            preferences[FLASHLIGHT_BPM_INDEX_KEY] ?: 0
        }

    suspend fun updateAppearance(value: Appearance){
        dataStore.edit { preferences ->
            preferences[APPEARANCE_KEY] = value.name
        }
    }

    suspend fun updateDynamicTheme(value: Boolean){
        dataStore.edit { preferences ->
            preferences[DYNAMIC_THEME_KEY] = value
        }
    }

    suspend fun updateOledTheme(value: Boolean){
        dataStore.edit { preferences ->
            preferences[OLED_THEME_KEY] = value
        }
    }

    suspend fun updateQuickActionPreference(value: Boolean) {
        dataStore.edit { preferences ->
            preferences[SAVE_QUICK_ACTION_KEY] = value
        }
    }

    suspend fun updateShowSosButtonPreference(value: Boolean) {
        dataStore.edit { preferences ->
            preferences[SHOW_SOS_BUTTON_KEY] = value
        }
    }

    suspend fun saveSegmentedButtonValue(value: Int) {
        dataStore.edit { preferences ->
            preferences[SEGMENTED_BUTTON_VALUE_KEY] = value
        }
    }

    suspend fun updateHapticStatus(value: Boolean){
        dataStore.edit { preferences ->
            preferences[ENABLE_HAPTIC_STATUS_KEY] = value
        }
    }

    suspend fun resetSegmentedButtonValue() {
        dataStore.edit { preferences ->
            preferences[SEGMENTED_BUTTON_VALUE_KEY] = 0
        }
    }

    suspend fun updateScreenFlashDurationIndex(value: Int) {
        dataStore.edit { preferences ->
            preferences[SCREEN_FLASH_DURATION_INDEX_KEY] = value
        }
    }

    suspend fun updateScreenFlashColorIndex(value: Int) {
        dataStore.edit { preferences ->
            preferences[SCREEN_FLASH_COLOR_INDEX_KEY] = value
        }
    }

    suspend fun updateScreenFlashBrightnessIndex(value: Int) {
        dataStore.edit { preferences ->
            preferences[SCREEN_FLASH_BRIGHTNESS_INDEX_KEY] = value
        }
    }

    suspend fun updateFlashlightDurationIndex(value: Int) {
        dataStore.edit { preferences ->
            preferences[FLASHLIGHT_DURATION_INDEX_KEY] = value
        }
    }

    suspend fun updateFlashlightBpmIndex(value: Int) {
        dataStore.edit { preferences ->
            preferences[FLASHLIGHT_BPM_INDEX_KEY] = value
        }
    }
}

