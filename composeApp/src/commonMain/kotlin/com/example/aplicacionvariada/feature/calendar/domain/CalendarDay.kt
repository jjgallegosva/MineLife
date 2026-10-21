package com.example.aplicacionvariada.feature.calendar.domain

/** Tipo de día según el régimen configurado. */
enum class DayType {
    MINE, REST, VACATION, SHIFT_CHANGE, OUTSIDE
}

/** Día mostrado en la cuadrícula mensual. */
data class CalendarDay(
    val day: Int,
    val type: DayType,
    val inCurrentMonth: Boolean = true
)
