package pe.compartamos.designsystem.components.atoms.calendar
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import pe.compartamos.designsystem.components.molecules.calendar.CalendarGrid
import pe.compartamos.designsystem.components.molecules.calendar.CalendarHeader
import pe.compartamos.designsystem.components.molecules.calendar.CalendarUtils
import kotlinx.datetime.LocalDate

/**
 * Configuración de eventos para el calendario
 */

data class CalendarEventsConfig<T>(
    val events: List<T> = emptyList(),
    val markedDates: List<LocalDate> = emptyList(),
    val eventDateExtractor: (T) -> LocalDate? = { null }
)

/**
 * Configuración de callbacks para el calendario
 */
data class CalendarCallbacks(
    val onPrev: () -> Unit = {},
    val onNext: () -> Unit = {},
    val onDayClick: (Int, Int) -> Unit = { _, _ -> },
    val onPageChange: (Int, Int) -> Unit = { _, _ -> }
)

@Composable
fun <T> Calendar(
    year: Int,
    month: Int,
    selectedYear: Int,
    selectedMonth: Int,
    selectedDay: Int,
    eventsConfig: CalendarEventsConfig<T> = CalendarEventsConfig(),
    callbacks: CalendarCallbacks = CalendarCallbacks()
) {
    val eventDaysFromEvents = extractEventDays(
        eventsConfig.events,
        eventsConfig.eventDateExtractor,
        year,
        month
    )
    val markedDaysFromDates = extractMarkedDays(eventsConfig.markedDates, year, month)
    val finalEventDays = (eventDaysFromEvents + markedDaysFromDates).distinct()

    Column(
        modifier = Modifier.background(Color.White)
    ) {
        CalendarHeader(
            month = CalendarUtils.getMonthName(month),
            year = year.toString(),
            onPrev = { handlePrevClick(callbacks.onPrev, callbacks.onPageChange, month, year) },
            onNext = { handleNextClick(callbacks.onNext, callbacks.onPageChange, month, year) }
        )
        CalendarGrid(
            year = year,
            month = month,
            selectedYear = selectedYear,
            selectedMonth = selectedMonth,
            selectedDay = selectedDay,
            eventDays = finalEventDays,
            onDayClick = { day ->
                callbacks.onDayClick(day, month)
            }
        )
    }
}

private fun <T> extractEventDays(
    events: List<T>,
    eventDateExtractor: (T) -> LocalDate?,
    year: Int,
    month: Int
): List<Int> {
    return events.mapNotNull { event ->
        eventDateExtractor(event)?.let { date ->
            if (date.year == year && date.monthNumber == month) {
                date.dayOfMonth
            } else {
                null
            }
        }
    }
}

private fun extractMarkedDays(
    markedDates: List<LocalDate>,
    year: Int,
    month: Int
): List<Int> {
    return markedDates
        .filter { date ->
            date.year == year && date.monthNumber == month
        }
        .map { it.dayOfMonth }
}

private fun handlePrevClick(
    onPrev: () -> Unit,
    onPageChange: (Int, Int) -> Unit,
    month: Int,
    year: Int
) {
    onPrev()
    val newMonth = if (month == 1) 12 else month - 1
    val newYear = if (month == 1) year - 1 else year
    onPageChange(newYear, newMonth)
}

private fun handleNextClick(
    onNext: () -> Unit,
    onPageChange: (Int, Int) -> Unit,
    month: Int,
    year: Int
) {
    onNext()
    val newMonth = if (month == 12) 1 else month + 1
    val newYear = if (month == 12) year + 1 else year
    onPageChange(newYear, newMonth)
}

/*@Preview(showBackground = true)
@Composable
fun CalendarScreenPreview() {
    Calendar<String>(
        year = 2025,
        month = 2,
        selectedDay = 19,
        eventsConfig = CalendarEventsConfig(
            events = emptyList(),
            eventDateExtractor = { null }
        ),
        callbacks = CalendarCallbacks(
            onPrev = { },
            onNext = { },
            onDayClick = { _, _ -> *//* Preview - no action *//* },
            onPageChange = { _, _ -> *//* Preview - no action *//* }
        )
    )

}*/
