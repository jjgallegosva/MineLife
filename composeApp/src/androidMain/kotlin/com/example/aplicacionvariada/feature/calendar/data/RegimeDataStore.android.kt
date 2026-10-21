package com.example.aplicacionvariada.feature.calendar.data

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences

fun createAndroidRegimeDataStore(
    context: Context
): DataStore<Preferences> {
    return createRegimeDataStore {
        context.applicationContext.filesDir
            .resolve(REGIME_DATASTORE_FILE)
            .absolutePath
    }
}
