package pe.compartamos.designsystem.components.molecules.calendar


import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import aplicacionvariada.composeapp.generated.resources.Res
import aplicacionvariada.composeapp.generated.resources.star
import kotlinx.datetime.LocalDate
import kotlinx.datetime.isoDayNumber
import org.jetbrains.compose.resources.painterResource



// Clase de datos para representar un día en el calendario
data class DayItem(val day: Int, val monthOffset: Int)

@Composable
internal fun CalendarGrid(
    year: Int,
    month: Int,
    selectedYear: Int,
    selectedMonth: Int,
    selectedDay: Int,
    eventDays: List<Int> = emptyList(),
    onDayClick: (Int) -> Unit = {}
) {
    val totalItems = calculateCalendarItems(year, month)
    val daysOfWeek = listOf("Lun", "Mar", "Mie", "Jue", "Vie", "Sab", "Dom")

    Column(modifier = Modifier.padding(vertical = 16.dp)) {
        CalendarWeekDaysHeader(daysOfWeek)
        Spacer(modifier = Modifier.height(8.dp))
        CalendarDaysGrid(
            items = totalItems,
            year = year,
            month = month,
            selectedYear = selectedYear,
            selectedMonth = selectedMonth,
            selectedDay = selectedDay,
            eventDays = eventDays,
            onDayClick = onDayClick
        )
    }
}
private fun getDaysInMonth(year: Int, month: Int): Long {
    val firstDayOfMonth = LocalDate(year, month, 1)

    val firstDayOfNextMonth = if (month == 12) {
        LocalDate(year + 1, 1, 1)
    } else {
        LocalDate(year, month + 1, 1)
    }

    return (firstDayOfNextMonth.toEpochDays() - firstDayOfMonth.toEpochDays()).toLong()
}
private fun calculateCalendarItems(year: Int, month: Int): List<DayItem> {
    val currentMonth = LocalDate(year, month, 1)

    val daysInMonth = getDaysInMonth(year, month)

    val previousYear = if (month == 1) year - 1 else year
    val previousMonth = if (month == 1) 12 else month - 1
    val daysInPrevMonth = getDaysInMonth(previousYear, previousMonth)

    val leadingDays = (currentMonth.dayOfWeek.isoDayNumber - 1)

    val daysFromPrevMonth = if (leadingDays > 0) {
        (daysInPrevMonth - leadingDays + 1..daysInPrevMonth)
            .map { day ->
                DayItem(day.toInt(), -1)
            }
    } else {
        emptyList()
    }

    val daysCurrentMonth = (1..daysInMonth)
        .map { day ->
            DayItem(day.toInt(), 0)
        }

    val totalItemsInitial = daysFromPrevMonth + daysCurrentMonth

    val totalCells = ((totalItemsInitial.size + 6) / 7) * 7

    val trailingDays = totalCells - totalItemsInitial.size

    val daysNextMonth = (1..trailingDays)
        .map { day ->
            DayItem(day, 1)
        }

    return totalItemsInitial + daysNextMonth
}

@Composable
private fun CalendarWeekDaysHeader(daysOfWeek: List<String>) {
    Row(Modifier.fillMaxWidth()) {
        daysOfWeek.forEach { dayName ->
            Text(
                text = dayName,
                modifier = Modifier.weight(1f),
                textAlign = TextAlign.Center,
            )
        }
    }
}

@Composable
private fun CalendarDaysGrid(
    items: List<DayItem>,
    year: Int,
    month: Int,
    selectedYear: Int,
    selectedMonth: Int,
    selectedDay: Int,
    eventDays: List<Int>,
    onDayClick: (Int) -> Unit
) {
    LazyVerticalGrid(
        columns = GridCells.Fixed(7),
        userScrollEnabled = false,
        modifier = Modifier.fillMaxWidth()
    ) {
        items(items) { dayItem ->
            CalendarDayCell(
                dayItem = dayItem,
                year = year,
                month = month,
                selectedYear = selectedYear,
                selectedMonth = selectedMonth,
                selectedDay = selectedDay,
                hasEvent = dayItem.monthOffset == 0 && eventDays.contains(dayItem.day),
                onDayClick = onDayClick
            )
        }
    }
}

@Composable
private fun CalendarDayCell(
    dayItem: DayItem,
    year: Int,
    month: Int,
    selectedYear: Int,
    selectedMonth: Int,
    selectedDay: Int,
    hasEvent: Boolean,
    onDayClick: (Int) -> Unit
) {
    val isCurrentMonth = dayItem.monthOffset == 0
    val isSelectedDay =
        isCurrentMonth &&
                year == selectedYear &&
                month == selectedMonth &&
                dayItem.day == selectedDay

    val backgroundColor = when {
        isSelectedDay -> Color(0XFFF5F6F8)
        isCurrentMonth -> Color(0XFFF5F6F8)
        else -> Color(0XFFF5F6F8).copy(alpha = 0.4f)
    }

    val textColor = when {
        isSelectedDay -> Color.Red
        isCurrentMonth -> Color(0XFF535A64)
        else -> Color(0XFF535A64).copy(alpha = 0.4f)
    }


    Box(
        modifier = Modifier
            .aspectRatio(1f)
            .padding(2.dp)
            .background(
                color = backgroundColor,
                shape = RoundedCornerShape(8.dp)
            )
            .clickable(enabled = isCurrentMonth) {
                if (isCurrentMonth) {
                    onDayClick(dayItem.day)
                }
            },
    ) {
        Text(
            text = dayItem.day.toString(),
            color = textColor,
            modifier = Modifier.padding(top =4.dp, start = 4.dp)
        )

        if (hasEvent) {
            EventIndicator(
                modifier = Modifier
                    .align(Alignment.BottomCenter)
                    .padding(bottom = 12.dp)
            )
        }
    }
}

@Composable
private fun EventIndicator(modifier: Modifier = Modifier) {
    Icon(
        painter = painterResource(Res.drawable.star),
        contentDescription = "Evento",
        tint = Color.DarkGray,
        modifier = modifier.size(12.dp)
    )
}

@Preview(showBackground = true)
@Composable
private fun CalendarGridPreview() {
    CalendarGrid(
        year = 2026,
        month = 5,
        selectedYear = 2026,
        selectedMonth = 5,
        selectedDay = 7,
        eventDays = listOf(3, 7, 15, 22),
        onDayClick = {}
    )
}

