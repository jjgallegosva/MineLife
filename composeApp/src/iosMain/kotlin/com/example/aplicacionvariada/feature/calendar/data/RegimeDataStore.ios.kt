package com.example.aplicacionvariada.feature.calendar.data

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.PreferenceDataStoreFactory
import androidx.datastore.preferences.core.Preferences
import kotlinx.cinterop.ExperimentalForeignApi
import okio.Path.Companion.toPath
import platform.Foundation.NSDocumentDirectory
import platform.Foundation.NSFileManager
import platform.Foundation.NSUserDomainMask

private const val REGIME_DATASTORE_FILE = "regime.preferences_pb"

@OptIn(ExperimentalForeignApi::class)
fun createIOSDataStore(): DataStore<Preferences> {
    return PreferenceDataStoreFactory.createWithPath(
        produceFile = {
            val documentsDirectory =
                NSFileManager.defaultManager.URLForDirectory(
                    directory = NSDocumentDirectory,
                    inDomain = NSUserDomainMask,
                    appropriateForURL = null,
                    create = true,
                    error = null
                ) ?: error("No se pudo obtener el directorio de documentos de iOS")

            "${documentsDirectory.path}/$REGIME_DATASTORE_FILE".toPath()
        }
    )
}
