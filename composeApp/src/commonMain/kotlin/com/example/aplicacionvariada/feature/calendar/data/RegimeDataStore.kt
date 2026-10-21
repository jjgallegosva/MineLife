package com.example.aplicacionvariada.feature.calendar.data

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.datastore.preferences.core.stringPreferencesKey
import com.example.aplicacionvariada.feature.calendar.domain.RegimeSettings
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class RegimeDataStore(
    private val dataStore: DataStore<Preferences>
) {
    private companion object {
        val MINE_DAYS = intPreferencesKey("mine_days")
        val REST_DAYS = intPreferencesKey("rest_days")
        val FIRST_MINE_DATE = stringPreferencesKey("first_mine_date")
        val REGIME_NAME = stringPreferencesKey("regime_name")
        val REGIME_NOTES = stringPreferencesKey("regime_notes")
    }

    val settings: Flow<RegimeSettings> = dataStore.data.map { preferences ->
        RegimeSettings(
            mineDays = preferences[MINE_DAYS] ?: 14,
            restDays = preferences[REST_DAYS] ?: 7,
            firstMineDate = preferences[FIRST_MINE_DATE] ?: "20/10/2026",
            regimeName = preferences[REGIME_NAME] ?: "Régimen 14 × 7",
            regimeNotes = preferences[REGIME_NOTES] ?: ""
        )
    }

    suspend fun save(settings: RegimeSettings) {
        dataStore.edit { preferences ->
            preferences[MINE_DAYS] = settings.mineDays
            preferences[REST_DAYS] = settings.restDays
            preferences[FIRST_MINE_DATE] = settings.firstMineDate
            preferences[REGIME_NAME] = settings.regimeName
            preferences[REGIME_NOTES] = settings.regimeNotes
        }
    }
}
