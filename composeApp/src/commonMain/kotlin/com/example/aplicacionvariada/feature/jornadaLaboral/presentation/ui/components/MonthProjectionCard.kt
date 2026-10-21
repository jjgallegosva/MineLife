package com.example.aplicacionvariada.feature.jornadaLaboral.presentation.ui.components
import JornadaDay
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape

import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlinx.datetime.LocalDate
import kotlinx.datetime.isoDayNumber

@Composable
fun MonthProjectionCard(
    title: String,
    days: List<JornadaDay>
) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(22.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(defaultElevation = 3.dp)
    ) {
        Column(
            modifier = Modifier.padding(18.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = title,
                fontSize = 22.sp,
                fontWeight = FontWeight.Bold,
                color = Color(0xFF111827)
            )

            Spacer(modifier = Modifier.height(16.dp))

            Row(modifier = Modifier.fillMaxWidth()) {
                listOf("LUN", "MAR", "MIÉ", "JUE", "VIE", "SÁB", "DOM").forEach { day ->
                    Text(
                        text = day,
                        modifier = Modifier.weight(1f),
                        textAlign = TextAlign.Center,
                        color = Color(0xFF6B7280),
                        fontSize = 13.sp
                    )
                }
            }

            Spacer(modifier = Modifier.height(12.dp))
            val firstDayOffset = days
                .firstOrNull()
                ?.date
                ?.dayOfWeek
                ?.isoDayNumber
                ?.minus(1)
                ?: 0

            val calendarItems = buildList<JornadaDay?> {
                repeat(firstDayOffset) {
                    add(null)
                }

                addAll(days)
            }

            calendarItems.chunked(7).forEach { week ->
                Row(modifier = Modifier.fillMaxWidth()) {
                    week.forEach { day ->
                        if (day == null) {
                            Spacer(modifier = Modifier.weight(1f))
                        } else {
                            CalendarDayCircle(
                                text = day.date.dayOfMonth.toString(),
                                isInMine = day.isInMine,
                                modifier = Modifier.weight(1f)
                            )
                        }
                    }

                    repeat(7 - week.size) {
                        Spacer(modifier = Modifier.weight(1f))
                    }
                }

                Spacer(modifier = Modifier.height(10.dp))
            }
        }
    }
}

@Composable
fun CalendarDayCircle(
    text: String,
    isInMine: Boolean,
    modifier: Modifier = Modifier
) {
    val background = if (isInMine) Color(0xFF007A3D) else Color(0xFFE5E7EB)
    val textColor = if (isInMine) Color.White else Color(0xFF111827)

    Box(
        modifier = modifier,
        contentAlignment = Alignment.Center
    ) {
        Box(
            modifier = Modifier
                .size(42.dp)
                .background(background, CircleShape),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = text,
                color = textColor,
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold
            )
        }
    }
}

@Composable
fun LegendRow() {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.Center
    ) {
        LegendItem(
            color = Color(0xFF007A3D),
            text = "En mina"
        )

        Spacer(modifier = Modifier.width(24.dp))

        LegendItem(
            color = Color(0xFFE5E7EB),
            text = "Descanso"
        )
    }
}

@Composable
fun LegendItem(
    color: Color,
    text: String
) {
    Row(verticalAlignment = Alignment.CenterVertically) {
        Box(
            modifier = Modifier
                .size(12.dp)
                .background(color, CircleShape)
        )

        Spacer(modifier = Modifier.width(8.dp))

        Text(
            text = text,
            color = Color(0xFF6B7280),
            fontSize = 14.sp
        )
    }
}

@Preview(showBackground = true)
@Composable
fun MonthProjectionCardPreview() {
    MonthProjectionCard(
        title = "Mayo 2025",
        days = listOf(
            JornadaDay(date = LocalDate(2025, 5, 1), isInMine = true, cycleDay = 1),
            JornadaDay(date = LocalDate(2025, 5, 2), isInMine = true, cycleDay = 2),
            JornadaDay(date = LocalDate(2025, 5, 3), isInMine = true, cycleDay = 3),
            JornadaDay(date = LocalDate(2025, 5, 4), isInMine = true, cycleDay = 4),
            JornadaDay(date = LocalDate(2025, 5, 5), isInMine = false, cycleDay = 5),
            JornadaDay(date = LocalDate(2025, 5, 6), isInMine = false, cycleDay = 6),
            JornadaDay(date = LocalDate(2025, 5, 7), isInMine = false, cycleDay = 7),

            JornadaDay(date = LocalDate(2025, 5, 8), isInMine = true, cycleDay = 1),
            JornadaDay(date = LocalDate(2025, 5, 9), isInMine = true, cycleDay = 2),
            JornadaDay(date = LocalDate(2025, 5, 10), isInMine = true, cycleDay = 3),
            JornadaDay(date = LocalDate(2025, 5, 11), isInMine = true, cycleDay = 4),
            JornadaDay(date = LocalDate(2025, 5, 12), isInMine = false, cycleDay = 5),
            JornadaDay(date = LocalDate(2025, 5, 13), isInMine = false, cycleDay = 6),
            JornadaDay(date = LocalDate(2025, 5, 14), isInMine = false, cycleDay = 7)
        )
    )
}