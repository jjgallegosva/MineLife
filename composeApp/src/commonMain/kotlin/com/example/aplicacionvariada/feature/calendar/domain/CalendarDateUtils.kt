package com.example.aplicacionvariada.feature.calendar.domain

import kotlinx.datetime.LocalDate
import kotlinx.datetime.isoDayNumber

fun buildRegimeDays(
    monthIndex: Int,
    year: Int,
    mineDays: Int,
    restDays: Int,
    firstMineDate: String
): List<CalendarDay> {

    fun isLeapYear(y: Int) =
        y % 4 == 0 && (y % 100 != 0 || y % 400 == 0)

    fun daysInMonth(month: Int, y: Int): Int =
        when (month) {
            2 -> if (isLeapYear(y)) 29 else 28
            4, 6, 9, 11 -> 30
            else -> 31
        }

    // Número de día acumulado desde el año 1.
    fun dayNumber(day: Int, month: Int, y: Int): Int {
        var total = 0

        for (currentYear in 1 until y) {
            total += if (isLeapYear(currentYear)) 366 else 365
        }

        for (currentMonth in 1 until month) {
            total += daysInMonth(currentMonth, y)
        }

        return total + day
    }

    val parts = firstMineDate.split("/")
    val startDay = parts.getOrNull(0)?.toIntOrNull()
    val startMonth = parts.getOrNull(1)?.toIntOrNull()
    val startYear = parts.getOrNull(2)?.toIntOrNull()

    val validDate = startDay != null &&
            startMonth != null &&
            startYear != null &&
            startYear in 1..9999 &&
            startMonth in 1..12 &&
            startDay in 1..daysInMonth(startMonth, startYear)

    val validRegime = mineDays > 0 && restDays > 0
    val cycleLength = mineDays + restDays


    // Lunes = 0 ... domingo = 6.
    fun weekdayOffset(month: Int, y: Int): Int {
        return LocalDate(y, month, 1)
            .dayOfWeek
            .isoDayNumber - 1
    }

    val month = monthIndex + 1
    val totalDays = daysInMonth(month, year)
    val offset = weekdayOffset(month, year)
    val result = mutableListOf<CalendarDay>()

    val previousMonth = if (monthIndex == 0) 12 else monthIndex
    val previousYear = if (monthIndex == 0) year - 1 else year
    val previousCount = daysInMonth(previousMonth, previousYear)

    // Días del mes anterior.
    repeat(offset) { index ->
        result.add(
            CalendarDay(
                day = previousCount - offset + index + 1,
                type = DayType.OUTSIDE,
                inCurrentMonth = false
            )
        )
    }

    val startNumber = if (validDate) {
        dayNumber(startDay!!, startMonth!!, startYear!!)
    } else {
        0
    }

    for (day in 1..totalDays) {
        val type = if (!validDate || !validRegime) {
            DayType.OUTSIDE
        } else {
            val currentNumber = dayNumber(day, month, year)
            val elapsed = currentNumber - startNumber

            // El ciclo también se extiende antes de la fecha inicial.
            val position = ((elapsed % cycleLength) + cycleLength) % cycleLength

            if (position < mineDays) {
                DayType.MINE
            } else {
                DayType.REST
            }
        }

        result.add(CalendarDay(day = day, type = type))
    }

    // Completa la última semana.
    var nextDay = 1
    while (result.size % 7 != 0) {
        result.add(
            CalendarDay(
                day = nextDay++,
                type = DayType.OUTSIDE,
                inCurrentMonth = false
            )
        )
    }

    return result
}
