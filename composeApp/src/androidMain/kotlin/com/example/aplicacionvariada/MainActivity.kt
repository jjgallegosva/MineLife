package com.example.aplicacionvariada

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import androidx.datastore.preferences.core.PreferenceDataStoreFactory
import com.example.aplicacionvariada.feature.calendar.data.RegimeDataStore
import com.example.aplicacionvariada.feature.calendar.data.REGIME_DATASTORE_FILE
import okio.Path.Companion.toOkioPath
import java.io.File

class MainActivity : ComponentActivity() {

    private val regimeDataStore by lazy {
        RegimeDataStore(
            PreferenceDataStoreFactory.createWithPath(
                produceFile = {
                    File(filesDir, REGIME_DATASTORE_FILE).toOkioPath()
                }
            )
        )
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        setContent {
            App(regimeDataStore = regimeDataStore)
        }
    }
}

@Preview
@Composable
fun AppAndroidPreview() {
    // La preview requiere una instancia real de DataStore.
}
