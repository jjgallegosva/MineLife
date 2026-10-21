package com.example.aplicacionvariada.feature.calendar.domain

import kotlinx.datetime.LocalDate

data class MonthlyRegimeSummary(
    val mineDays: Int,
    val restDays: Int
)

object RegimeCalculator {

    private fun isLeapYear(year: Int): Boolean =
        year % 4 == 0 &&
                (year % 100 != 0 || year % 400 == 0)

    private fun daysInMonth(month: Int, year: Int): Int =
        when (month) {
            2 -> if (isLeapYear(year)) 29 else 28
            4, 6, 9, 11 -> 30
            else -> 31
        }

    private fun dayNumber(
        day: Int,
        month: Int,
        year: Int
    ): Int {
        var total = 0

        for (y in 1 until year) {
            total += if (isLeapYear(y)) 366 else 365
        }

        for (m in 1 until month) {
            total += daysInMonth(m, year)
        }

        return total + day
    }


    fun calculateMonthly(
        month: Int,
        year: Int,
        mineDays: Int,
        restDays: Int,
        firstMineDate: String
    ): MonthlyRegimeSummary {
        if (month !in 1..12 || year !in 1..9999 ||
            mineDays <= 0 || restDays <= 0
        ) {
            return MonthlyRegimeSummary(0, 0)
        }

        val parts = firstMineDate.split("/")
        if (parts.size != 3) return MonthlyRegimeSummary(0, 0)

        val startDay = parts[0].toIntOrNull()
            ?: return MonthlyRegimeSummary(0, 0)
        val startMonth = parts[1].toIntOrNull()
            ?: return MonthlyRegimeSummary(0, 0)
        val startYear = parts[2].toIntOrNull()
            ?: return MonthlyRegimeSummary(0, 0)

        if (
            startYear !in 1..9999 ||
            startMonth !in 1..12 ||
            startDay !in 1..daysInMonth(startMonth, startYear)
        ) {
            return MonthlyRegimeSummary(0, 0)
        }

        val cycleLength = mineDays + restDays
        val start = dayNumber(startDay, startMonth, startYear)

        var mine = 0
        var rest = 0

        for (day in 1..daysInMonth(month, year)) {
            val current = dayNumber(day, month, year)
            val elapsed = current - start
            val position = ((elapsed % cycleLength) + cycleLength) % cycleLength

            if (position < mineDays) {
                mine++
            } else {
                rest++
            }
        }

        return MonthlyRegimeSummary(
            mineDays = mine,
            restDays = rest
        )
    }


    fun calculateYearly(
        year: Int,
        mineDays: Int,
        restDays: Int,
        firstMineDate: String
    ): MonthlyRegimeSummary {
        val parts = firstMineDate.split("/")
        if (parts.size != 3) return MonthlyRegimeSummary(0, 0)

        val startDay = parts[0].toIntOrNull()
            ?: return MonthlyRegimeSummary(0, 0)
        val startMonth = parts[1].toIntOrNull()
            ?: return MonthlyRegimeSummary(0, 0)
        val startYear = parts[2].toIntOrNull()
            ?: return MonthlyRegimeSummary(0, 0)

        if (
            year !in 1..9999 ||
            startYear !in 1..9999 ||
            startMonth !in 1..12 ||
            startDay !in 1..daysInMonth(startMonth, startYear) ||
            mineDays <= 0 ||
            restDays <= 0
        ) {
            return MonthlyRegimeSummary(0, 0)
        }

        val cycleLength = mineDays + restDays
        val start = dayNumber(startDay, startMonth, startYear)

        var totalMine = 0
        var totalRest = 0

        for (month in 1..12) {
            for (day in 1..daysInMonth(month, year)) {
                val current = dayNumber(day, month, year)
                val elapsed = current - start

                // También calcula los días anteriores a la fecha de referencia.
                val position = ((elapsed % cycleLength) + cycleLength) % cycleLength

                if (position < mineDays) {
                    totalMine++
                } else {
                    totalRest++
                }
            }
        }

        return MonthlyRegimeSummary(
            mineDays = totalMine,
            restDays = totalRest
        )
    }

    fun getPreviousCycles(
        firstMineDate: LocalDate,
        mineDays: Int,
        restDays: Int,
        today: LocalDate,
        limit: Int = 3
    ): List<Pair<LocalDate, LocalDate>> {
        val cycleLength = mineDays + restDays

        if (cycleLength <= 0 || today <= firstMineDate || limit <= 0) {
            return emptyList()
        }

        val elapsed = today.toEpochDays() - firstMineDate.toEpochDays()
        val position = elapsed % cycleLength

        val currentCycleStart = LocalDate.fromEpochDays(
            today.toEpochDays() - position
        )

        return buildList {
            for (i in 1..limit) {
                val endDate = LocalDate.fromEpochDays(
                    currentCycleStart.toEpochDays() - (i - 1) * cycleLength - 1
                )

                val startDate = LocalDate.fromEpochDays(
                    endDate.toEpochDays() - cycleLength + 1
                )

                // No mostrar ciclos anteriores al inicio configurado.
                if (startDate >= firstMineDate) {
                    add(startDate to endDate)
                }
            }
        }
    }

}