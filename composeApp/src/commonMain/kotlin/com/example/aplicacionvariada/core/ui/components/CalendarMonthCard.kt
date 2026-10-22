package com.example.aplicacionvariada.core.ui.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.ChevronLeft
import androidx.compose.material.icons.outlined.ChevronRight
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedIconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.aplicacionvariada.core.ui.components.Blue
import com.example.aplicacionvariada.core.ui.components.BorderColor
import com.example.aplicacionvariada.core.ui.components.Green
import com.example.aplicacionvariada.core.ui.components.LegendItem
import com.example.aplicacionvariada.core.ui.components.Muted
import com.example.aplicacionvariada.core.ui.components.Navy
import com.example.aplicacionvariada.core.ui.components.Orange
import com.example.aplicacionvariada.core.ui.components.Purple
import com.example.aplicacionvariada.core.ui.components.WhiteCard
import com.example.aplicacionvariada.feature.calendar.domain.CalendarDay
import com.example.aplicacionvariada.feature.calendar.domain.DayType

@Composable
fun CalendarMonthCard(
    monthName: String,
    year: Int,
    days: List<CalendarDay>,
    selectedDay: Int,
    onPreviousMonth: () -> Unit,
    onNextMonth: () -> Unit,
    onGoToToday: () -> Unit,
    onDaySelected: (Int) -> Unit,
) {
    WhiteCard {
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            OutlinedIconButton(
                onClick = onPreviousMonth,
                border = BorderStroke(1.dp, BorderColor)
            ) {
                Icon(
                    imageVector = Icons.Outlined.ChevronLeft,
                    contentDescription = "Mes anterior",
                    tint = Muted
                )
            }

            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                Text(
                    text = "$monthName $year",
                    fontSize = 21.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color(0xFF171D28)
                )
                TextButton(
                    onClick = onGoToToday,
                    contentPadding = androidx.compose.foundation.layout.PaddingValues(
                        horizontal = 8.dp,
                        vertical = 0.dp
                    )
                ) {
                    Text("Ir a hoy", color = Orange)
                }
            }

            OutlinedIconButton(
                onClick = onNextMonth,
                border = BorderStroke(1.dp, BorderColor)
            ) {
                Icon(
                    imageVector = Icons.Outlined.ChevronRight,
                    contentDescription = "Mes siguiente",
                    tint = Muted
                )
            }
        }

        Spacer(Modifier.height(14.dp))

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            listOf("L", "M", "M", "J", "V", "S", "D").forEach { label ->
                Text(
                    text = label,
                    modifier = Modifier.width(42.dp),
                    textAlign = TextAlign.Center,
                    fontSize = 14.sp,
                    color = Muted,
                    fontWeight = FontWeight.Medium
                )
            }
        }

        Spacer(Modifier.height(12.dp))

        LazyVerticalGrid(
            columns = GridCells.Fixed(7),
            modifier = Modifier
                .fillMaxWidth()
                .heightIn(max = 360.dp),
            userScrollEnabled = false,
            horizontalArrangement = Arrangement.spacedBy(4.dp),
            verticalArrangement = Arrangement.spacedBy(7.dp)
        ) {
            items(days) { calendarDay ->
                val selected = calendarDay.inCurrentMonth && calendarDay.day == selectedDay
                val dayColor = when (calendarDay.type) {
                    DayType.MINE -> Orange
                    DayType.REST -> Green
                    DayType.VACATION -> Blue
                    DayType.SHIFT_CHANGE -> Purple
                    DayType.OUTSIDE -> Color(0xFFFFD4C2)
                }

                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .aspectRatio(1f)
                        .clip(RoundedCornerShape(15.dp))
                        .background(dayColor)

                        .then(
                            if (selected) Modifier.border(2.dp, Navy, RoundedCornerShape(15.dp))
                            else Modifier
                        )
                        .clickable {
                            if (calendarDay.inCurrentMonth) onDaySelected(calendarDay.day)
                        },
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = calendarDay.day.toString(),
                        fontSize = 15.sp,
                        color = if (calendarDay.type == DayType.OUTSIDE) {
                            Color.White.copy(alpha = 0.9f)
                        } else Color.White,
                        fontWeight = if (selected) FontWeight.Bold else FontWeight.Medium
                    )
                }
            }
        }

        HorizontalDivider(
            modifier = Modifier.padding(vertical = 22.dp),
            color = BorderColor
        )

        Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                LegendItem(Orange, "En mina")
                LegendItem(Green, "Libre")
                LegendItem(Blue, "Vacaciones / permiso")
            }
            LegendItem(Purple, "Cambio de turno")
        }
    }
}

@Preview
@Composable
private fun CalendarMonthCardPreview() {
    val previewDays = (1..35).map { index ->
        when {
            index <= 4 -> CalendarDay(index + 26, DayType.OUTSIDE, false)
            index <= 18 -> CalendarDay(index - 4, DayType.MINE)
            index <= 25 -> CalendarDay(index - 4, DayType.REST)
            else -> CalendarDay(index - 4, DayType.OUTSIDE, false)
        }
    }
    CalendarMonthCard(
        monthName = "Octubre",
        year = 2026,
        days = previewDays,
        selectedDay = 20,
        onPreviousMonth = {},
        onNextMonth = {},
        onGoToToday = {},
        onDaySelected = {}
    )
}
