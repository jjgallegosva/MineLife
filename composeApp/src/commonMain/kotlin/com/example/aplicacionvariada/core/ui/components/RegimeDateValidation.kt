package com.example.aplicacionvariada.core.ui.components

fun isValidRegimeDate(value: String): Boolean {
    val parts = value.split("/")
    if (parts.size != 3) return false

    val day = parts[0].toIntOrNull() ?: return false
    val month = parts[1].toIntOrNull() ?: return false
    val year = parts[2].toIntOrNull() ?: return false

    if (year !in 1900..2200 || month !in 1..12) return false

    val leapYear = year % 4 == 0 && (year % 100 != 0 || year % 400 == 0)
    val daysInMonth = when (month) {
        2 -> if (leapYear) 29 else 28
        4, 6, 9, 11 -> 30
        else -> 31
    }
    return day in 1..daysInMonth
}
