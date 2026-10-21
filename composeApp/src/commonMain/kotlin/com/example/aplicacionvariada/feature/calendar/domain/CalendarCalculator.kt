package com.example.aplicacionvariada.feature.calendar.domain

import kotlinx.datetime.LocalDate

/** Resultado completo de los cálculos que necesita la pantalla del calendario. */
data class CalendarCalculations(
    val days: List<CalendarDay> = emptyList(),
    val monthlySummary: MonthlyRegimeSummary = MonthlyRegimeSummary(0, 0),
    val yearlySummary: MonthlyRegimeSummary = MonthlyRegimeSummary(0, 0),
    val cycleLength: Int = 1,
    val todayCycleDay: Int = 1,
    val nextMineDate: LocalDate? = null,
    val nextRestDate: LocalDate? = null,
    val previousCycles: List<Pair<LocalDate, LocalDate>> = emptyList()
)

/** Mantiene la lógica de fechas fuera de Compose y del ViewModel. */
object CalendarCalculator {
    fun calculate(
        monthIndex: Int,
        year: Int,
        mineDays: Int,
        restDays: Int,
        firstMineDate: String,
        today: LocalDate
    ): CalendarCalculations {
        val days = buildRegimeDays(
            monthIndex = monthIndex,
            year = year,
            mineDays = mineDays,
            restDays = restDays,
            firstMineDate = firstMineDate
        )

        val monthly = RegimeCalculator.calculateMonthly(
            month = monthIndex + 1,
            year = year,
            mineDays = mineDays,
            restDays = restDays,
            firstMineDate = firstMineDate
        )
        val yearly = RegimeCalculator.calculateYearly(
            year = year,
            mineDays = mineDays,
            restDays = restDays,
            firstMineDate = firstMineDate
        )

        val cycleLength = (mineDays + restDays).takeIf { mineDays > 0 && restDays > 0 } ?: 1
        val startDate = parseDate(firstMineDate)
        if (startDate == null || mineDays <= 0 || restDays <= 0) {
            return CalendarCalculations(
                days = days,
                monthlySummary = monthly,
                yearlySummary = yearly,
                cycleLength = cycleLength
            )
        }

        val elapsed = today.toEpochDays() - startDate.toEpochDays()
        val todayCycleDay = ((elapsed % cycleLength + cycleLength) % cycleLength) + 1
        val daysUntilNextMine = cycleLength - todayCycleDay + 1
        val nextMineDate = LocalDate.fromEpochDays(today.toEpochDays() + daysUntilNextMine)
        val nextRestDate = LocalDate.fromEpochDays(nextMineDate.toEpochDays() + mineDays)
        val previousCycles = runCatching {
            RegimeCalculator.getPreviousCycles(
                firstMineDate = startDate,
                mineDays = mineDays,
                restDays = restDays,
                today = today
            )
        }.getOrDefault(emptyList())

        return CalendarCalculations(
            days = days,
            monthlySummary = monthly,
            yearlySummary = yearly,
            cycleLength = cycleLength,
            todayCycleDay = todayCycleDay.toInt(),
            nextMineDate = nextMineDate,
            nextRestDate = nextRestDate,
            previousCycles = previousCycles
        )
    }

    private fun parseDate(value: String): LocalDate? {
        val parts = value.split("/")
        if (parts.size != 3) return null
        val day = parts[0].toIntOrNull() ?: return null
        val month = parts[1].toIntOrNull() ?: return null
        val year = parts[2].toIntOrNull() ?: return null
        return runCatching { LocalDate(year, month, day) }.getOrNull()
    }
}
