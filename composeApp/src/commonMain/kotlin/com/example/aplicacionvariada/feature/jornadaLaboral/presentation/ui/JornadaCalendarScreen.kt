package com.example.aplicacionvariada.feature.jornadaLaboral.presentation.ui

import JornadaPattern
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.aplicacionvariada.feature.jornadaLaboral.presentation.ui.components.ChangesListCard
import com.example.aplicacionvariada.feature.jornadaLaboral.presentation.ui.components.CircleTextIcon
import com.example.aplicacionvariada.feature.jornadaLaboral.presentation.ui.components.LegendRow
import com.example.aplicacionvariada.feature.jornadaLaboral.presentation.ui.components.MonthProjectionCard
import com.example.aplicacionvariada.feature.jornadaLaboral.presentation.mappers.monthName
import formatDate
import generateJornadaProjection
import jornadaPatterns
import kotlinx.datetime.LocalDate

@Composable
fun JornadaCalendarScreen(
    startDate: LocalDate,
    pattern: JornadaPattern,
    range: Int,
    onBack: () -> Unit
) {
    val projection = remember(startDate, pattern, range) {
        generateJornadaProjection(startDate, pattern, range)
    }

    val groupedByMonth = projection.groupBy {
        "${monthName(it.date.monthNumber)} ${it.date.year}"
    }

    Scaffold(
        containerColor = Color(0xFFF8F9FA),
        topBar = {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(Color(0xFFF8F9FA))
                    .statusBarsPadding()
                    .padding(horizontal = 20.dp, vertical = 12.dp)
            ) {
                Text(
                    text = "‹",
                    modifier = Modifier
                        .align(Alignment.CenterStart)
                        .clickable { onBack() },
                    fontSize = 38.sp,
                    color = Color(0xFF007A3D)
                )

                Text(
                    text = "Mi calendario",
                    modifier = Modifier.align(Alignment.Center),
                    fontSize = 28.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color(0xFF111827)
                )
            }
        }
    ) { innerPadding ->

        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(horizontal = 20.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            item {
                CalendarTopSummary(
                    pattern = pattern,
                    startDate = startDate,
                    range = range
                )
            }

            groupedByMonth.forEach { (monthTitle, days) ->
                item {
                    MonthProjectionCard(
                        title = monthTitle,
                        days = days
                    )
                }
            }

            item {
                LegendRow()
            }

            item {
                ChangesListCard(projection)
            }

            item {
                Spacer(modifier = Modifier.height(24.dp))
            }
        }
    }
}

@Composable
fun CalendarTopSummary(
    pattern: JornadaPattern,
    startDate: LocalDate,
    range: Int
) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(22.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(defaultElevation = 3.dp)
    ) {
        Column(modifier = Modifier.padding(18.dp)) {
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                CircleTextIcon("📅")

                Spacer(modifier = Modifier.width(12.dp))

                Text(
                    text = "Ciclo consultado: ",
                    fontSize = 16.sp,
                    color = Color(0xFF111827)
                )

                Text(
                    text = pattern.name,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color(0xFF007A3D)
                )
            }

            Spacer(modifier = Modifier.height(16.dp))

            Row {
                Column(modifier = Modifier.weight(1f)) {
                    Text("Inicio:", color = Color(0xFF6B7280))
                    Text(
                        text = formatDate(startDate),
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold
                    )
                }

                Column(modifier = Modifier.weight(1f)) {
                    Text("Rango:", color = Color(0xFF6B7280))
                    Text(
                        text = "$range días",
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold
                    )
                }
            }
        }
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun JornadaCalendarScreenPreview() {
    JornadaCalendarScreen(
        startDate = LocalDate(2025, 5, 26),
        pattern = jornadaPatterns.first(),
        range = 60,
        onBack = {}
    )
}