package com.example.aplicacionvariada

import platform.Foundation.NSCalendar
import platform.Foundation.NSCalendarUnitDay
import platform.Foundation.NSCalendarUnitMonth
import platform.Foundation.NSCalendarUnitYear
import platform.Foundation.NSDate

actual fun getCurrentDate(): CurrentDate {
    val calendar = NSCalendar.currentCalendar

    val components = calendar.components(
        NSCalendarUnitYear or NSCalendarUnitMonth or NSCalendarUnitDay,
        fromDate = NSDate()
    )

    return CurrentDate(
        year = components.year.toInt(),
        month = components.month.toInt(),
        day = components.day.toInt()
    )
}