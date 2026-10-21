package com.example.aplicacionvariada.feature.home.domain

import kotlinx.datetime.LocalDate

data class MineRegimeStatus(
    val isBeforeStart: Boolean,
    val isInMine: Boolean,
    val currentDay: Int,
    val cycleLength: Int,
    val mineDays: Int,
    val restDays: Int,
    val daysRemaining: Int,
    val relevantDate: LocalDate,
    val progress: Float
)
