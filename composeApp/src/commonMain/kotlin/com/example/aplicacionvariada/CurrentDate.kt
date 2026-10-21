package com.example.aplicacionvariada

data class CurrentDate(
    val year: Int,
    val month: Int,
    val day: Int
)

expect fun getCurrentDate(): CurrentDate