package com.bitmavrick.store.preference

import android.content.Context
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.map

private const val SETTINGS_PREFERENCE_NAME = "settings_preference"

val Context.settingsDataStore by preferencesDataStore(name = SETTINGS_PREFERENCE_NAME)

class SettingsPreferenceRepository(
    context: Context
) {
    companion object {
        private val LOADED = booleanPreferencesKey("loaded")
        private val DARK_THEME = intPreferencesKey("dark_theme")
        private val DYNAMIC_THEME = booleanPreferencesKey("dynamic_theme")
        private val AMOLED_THEME = booleanPreferencesKey("amoled_theme")
    }

    private val dataStore = context.settingsDataStore

    val settingsPreferenceFlow = dataStore.data.map { preferences ->
        SettingsPreference(
            loaded = preferences[LOADED] ?: true,
            darkTheme = preferences[DARK_THEME] ?: 0,
            dynamicTheme = preferences[DYNAMIC_THEME] ?: true,
            amoledTheme = preferences[AMOLED_THEME] ?: false,
        )
    }

    suspend fun updateDarkTheme(value: Int){
        dataStore.edit { preferences ->
            preferences[DARK_THEME] = value
        }
    }

    suspend fun updateDynamicTheme(value: Boolean) {
        dataStore.edit { preferences ->
            preferences[DYNAMIC_THEME] = value
        }
    }

    suspend fun updateAmoledTheme(value: Boolean){
        dataStore.edit { preferences ->
            preferences[AMOLED_THEME] = value
        }
    }
}

data class SettingsPreference(
    val loaded : Boolean = false,
    val darkTheme : Int = 0,
    val dynamicTheme : Boolean = true,
    val amoledTheme : Boolean = false
)