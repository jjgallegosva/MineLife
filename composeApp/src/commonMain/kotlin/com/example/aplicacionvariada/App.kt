package com.example.aplicacionvariada

import androidx.compose.runtime.*
import androidx.compose.ui.tooling.preview.Preview

import com.example.aplicacionvariada.core.presentation.SplashScreen
import com.example.aplicacionvariada.feature.calendar.data.RegimeDataStore

@Composable
fun App(    regimeDataStore: RegimeDataStore
) {
    AdvanceNavigationWrapper( regimeDataStore = regimeDataStore)
}