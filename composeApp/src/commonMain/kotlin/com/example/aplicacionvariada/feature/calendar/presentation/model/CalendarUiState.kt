package com.example.aplicacionvariada.feature.calendar.presentation.model

import com.example.aplicacionvariada.feature.calendar.domain.CalendarDay
import com.example.aplicacionvariada.feature.calendar.domain.MonthlyRegimeSummary
import kotlinx.datetime.LocalDate

/** Estado que consume la interfaz: configuración persistida y resultados calculados. */
data class CalendarUiState(
    val mineDays: Int = 14,
    val restDays: Int = 7,
    val firstMineDate: String = "20/10/2026",
    val regimeName: String = "Régimen 14 × 7",
    val regimeNotes: String = "",
    val isSaving: Boolean = false,
    val errorMessage: String? = null,
    val days: List<CalendarDay> = emptyList(),
    val monthlySummary: MonthlyRegimeSummary = MonthlyRegimeSummary(0, 0),
    val yearlySummary: MonthlyRegimeSummary = MonthlyRegimeSummary(0, 0),
    val cycleLength: Int = 21,
    val todayCycleDay: Int = 1,
    val nextMineDate: LocalDate? = null,
    val nextRestDate: LocalDate? = null,
    val previousCycles: List<Pair<LocalDate, LocalDate>> = emptyList()
)
