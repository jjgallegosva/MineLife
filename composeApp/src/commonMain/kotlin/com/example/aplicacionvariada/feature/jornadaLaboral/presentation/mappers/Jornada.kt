import kotlinx.datetime.DatePeriod
import kotlinx.datetime.LocalDate
import kotlinx.datetime.plus

data class JornadaPattern(
    val name: String,
    val daysInMine: Int,
    val daysRest: Int
) {
    val totalCycleDays: Int
        get() = daysInMine + daysRest
}

data class JornadaDay(
    val date: LocalDate,
    val isInMine: Boolean,
    val cycleDay: Int
)

val jornadaPatterns = listOf(
    JornadaPattern("14x7", daysInMine = 14, daysRest = 7),
    JornadaPattern("10x10", daysInMine = 10, daysRest = 10),
    JornadaPattern("20x10", daysInMine = 20, daysRest = 10),
    JornadaPattern("8x6", daysInMine = 8, daysRest = 6),
    JornadaPattern("4x3", daysInMine = 4, daysRest = 3),
    JornadaPattern("28x14", daysInMine = 28, daysRest = 14)
)

fun generateJornadaProjection(
    startDate: LocalDate,
    pattern: JornadaPattern,
    totalDays: Int
): List<JornadaDay> {
    return (0 until totalDays).map { index ->
        val cycleIndex = index % pattern.totalCycleDays
        val isInMine = cycleIndex < pattern.daysInMine

        JornadaDay(
            date = startDate.plus(DatePeriod(days = index)),
            isInMine = isInMine,
            cycleDay = cycleIndex + 1
        )
    }
}

fun JornadaDay.statusText(): String {
    return if (isInMine) "En mina" else "Descanso"
}

fun findNextRestDay(days: List<JornadaDay>): JornadaDay? {
    return days.firstOrNull { !it.isInMine }
}

fun findNextMineAfterRest(days: List<JornadaDay>): JornadaDay? {
    val firstRestIndex = days.indexOfFirst { !it.isInMine }
    if (firstRestIndex == -1) return null

    return days
        .drop(firstRestIndex)
        .firstOrNull { it.isInMine }
}

fun formatDate(date: LocalDate): String {
    val day = date.dayOfMonth.toString().padStart(2, '0')
    val month = date.monthNumber.toString().padStart(2, '0')
    return "$day/$month/${date.year}"
}

fun formatShortDate(date: LocalDate): String {
    val day = date.dayOfMonth.toString().padStart(2, '0')
    val month = date.monthNumber.toString().padStart(2, '0')
    return "$day/$month"
}