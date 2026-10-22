package com.example.aplicacionvariada

import androidx.compose.ui.window.ComposeUIViewController
import com.example.aplicacionvariada.feature.calendar.data.RegimeDataStore
import com.example.aplicacionvariada.feature.calendar.data.createIOSDataStore

private val regimeDataStore: RegimeDataStore by lazy {
    RegimeDataStore(createIOSDataStore())
}

fun MainViewController() = ComposeUIViewController {
    App(
        regimeDataStore = regimeDataStore
    )
}