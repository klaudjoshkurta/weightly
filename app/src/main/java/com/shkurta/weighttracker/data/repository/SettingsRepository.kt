package com.shkurta.weighttracker.data.repository

import android.content.Context
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import com.shkurta.weighttracker.ui.AppTheme
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import javax.inject.Singleton

/**  Create the actual DataStore instance on Context */
private val Context.settingsDataStore by preferencesDataStore(name = "user_settings")

@Singleton
class SettingsRepository @Inject constructor(
    private val context: Context
) {
    private object Keys {
        val THEME = stringPreferencesKey("theme")
    }

    /** Expose current theme as a Flow<AppTheme> */
    val themeFlow: Flow<AppTheme> = context.settingsDataStore.data.map { prefs: Preferences ->
        val raw = prefs[Keys.THEME]
        AppTheme.fromStorageKey(raw)
    }

    // Setters

    suspend fun setTheme(theme: AppTheme) {
        context.settingsDataStore.edit { prefs ->
            prefs[Keys.THEME] = theme.storageKey
        }
    }
}