package com.example.aplicacionvariada.feature.calendar.domain

data class RegimeSettings(
    val mineDays: Int = 14,
    val restDays: Int = 7,
    val firstMineDate: String = "20/10/2026",
    val regimeName: String = "Régimen 14 × 7",
    val regimeNotes: String = ""
)
