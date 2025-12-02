package com.bitmavrick.store.preference

import android.content.Context
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.map

private const val SHORTCUT_PREFERENCE_NAME = "shortcut_preference"

val Context.shortcutPreferenceDataStore by preferencesDataStore(name = SHORTCUT_PREFERENCE_NAME)

class ShortcutPreferenceRepository(
    context: Context
) {
    companion object {
        private val QUICK_BUTTON_INDEX = intPreferencesKey("quick_button_index")
    }

    private val dataStore = context.shortcutPreferenceDataStore

    val shortcutPreferenceFlow = dataStore.data
        .map { preferences ->
            ShortcutPreference(
                quickButtonIndex = preferences[QUICK_BUTTON_INDEX] ?: 1
            )
        }

    suspend fun updateQuickButtonIndex(index: Int){
        dataStore.edit { preferences ->
            preferences[QUICK_BUTTON_INDEX] = index
        }
    }
}

data class ShortcutPreference(
    val quickButtonIndex: Int = 0
)