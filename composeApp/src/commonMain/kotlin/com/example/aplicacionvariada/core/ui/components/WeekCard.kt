package com.minelife.core.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.foundation.layout.statusBarsPadding
import com.example.aplicacionvariada.feature.calendar.data.RegimeDataStore
import com.example.aplicacionvariada.feature.calendar.domain.RegimeSettings
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.tooling.preview.Preview
import com.example.aplicacionvariada.feature.home.domain.MineRegimeStatus
import com.example.aplicacionvariada.feature.home.presentation.HomeViewModel
import kotlinx.datetime.todayIn
import kotlin.time.Clock
import kotlinx.datetime.DateTimeUnit
import kotlinx.datetime.LocalDate
import kotlinx.datetime.TimeZone
import kotlinx.datetime.plus
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.outlined.DarkMode
import androidx.compose.material.icons.outlined.Person
import androidx.compose.material.icons.outlined.Logout
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Switch
import com.minelife.core.ui.theme.MineNavy
import com.minelife.core.ui.theme.MineOrange
import com.minelife.core.ui.theme.MineMuted

@Composable
fun WeekCard(onCalendar: () -> Unit) {
    val today = Clock.System.todayIn(TimeZone.currentSystemDefault())

    val days = (0..6).map { offset ->
        today.plus(offset, DateTimeUnit.DAY)
    }

    fun weekdayLabel(date: LocalDate): String {
        return when (date.dayOfWeek.name) {
            "MONDAY" -> "LUN"
            "TUESDAY" -> "MAR"
            "WEDNESDAY" -> "MIÉ"
            "THURSDAY" -> "JUE"
            "FRIDAY" -> "VIE"
            "SATURDAY" -> "SÁB"
            "SUNDAY" -> "DOM"
            else -> ""
        }
    }

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(32.dp))
            .background(Color.White)
            .border(
                1.dp,
                Color(0xFFE2E8F0),
                RoundedCornerShape(32.dp)
            )
            .padding(22.dp)
    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Text(
                text = "Próximos 7 días",
                color = Color(0xFF171D28),
                fontSize = 19.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.weight(1f)
            )

            Text(
                text = "Calendario completo",
                color = MineOrange,
                fontSize = 13.sp,
                modifier = Modifier.clickable {
                    onCalendar()
                }
            )
        }

        Spacer(Modifier.height(20.dp))

        LazyRow(
            horizontalArrangement = Arrangement.spacedBy(6.dp),
            modifier = Modifier.fillMaxWidth()
        ) {
            items(days.size) { index ->
                val date = days[index]
                val isToday = date == today

                Column(
                    modifier = Modifier.width(40.dp),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.spacedBy(5.dp)
                ) {
                    Text(
                        text = weekdayLabel(date),
                        fontSize = 12.sp,
                        color = MineMuted
                    )

                    Box(
                        modifier = Modifier.size(48.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        // Borde exterior oscuro
                        if (isToday) {
                            Box(
                                modifier = Modifier
                                    .fillMaxSize()
                                    .border(
                                        width = 2.dp,
                                        color = MineNavy,
                                        shape = RoundedCornerShape(15.dp)
                                    )
                            )
                        }

                        // Cuadrado naranja interior
                        Box(
                            modifier = Modifier
                                .padding(if (isToday) 4.dp else 0.dp)
                                .size(43.dp)
                                .clip(RoundedCornerShape(15.dp))
                                .background(MineOrange),
                            contentAlignment = Alignment.Center
                        ) {
                            Text(
                                text = date.dayOfMonth.toString(),
                                color = Color.White,
                                fontWeight = FontWeight.Bold
                            )
                        }
                    }


                }
            }
        }
    }
}

@Preview(showBackground = true, backgroundColor = 0xFFF5F7F9)
@Composable
private fun WeekCardPreview() {
    MaterialTheme {
        Box(Modifier.padding(16.dp)) { WeekCard(onCalendar = {}) }
    }
}
