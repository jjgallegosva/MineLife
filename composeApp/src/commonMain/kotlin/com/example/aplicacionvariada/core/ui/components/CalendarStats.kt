package com.example.aplicacionvariada.core.ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import com.example.aplicacionvariada.core.ui.components.Green
import com.example.aplicacionvariada.core.ui.components.Orange
import com.example.aplicacionvariada.core.ui.components.StatCard

@Composable
fun CalendarStats(
    mineDaysThisMonth: Int,
    restDaysThisMonth: Int,
    year: Int,
    mineDaysThisYear: Int
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        StatCard(
            title = "DÍAS EN MINA",
            value = mineDaysThisMonth.toString(),
            detail = "este mes",
            valueColor = Orange,
            modifier = Modifier.weight(1f)
        )

        StatCard(
            title = "DÍAS LIBRES",
            value = restDaysThisMonth.toString(),
            detail = "este mes",
            valueColor = Green,
            modifier = Modifier.weight(1f)
        )

        StatCard(
            title = "AÑO $year",
            value = mineDaysThisYear.toString(),
            detail = "días en mina",
            valueColor = Color(0xFF171D28),
            modifier = Modifier.weight(1.08f)
        )
    }
}

@Preview
@Composable
private fun CalendarStatsPreview() {
    CalendarStats(
        mineDaysThisMonth = 14,
        restDaysThisMonth = 7,
        year = 2026,
        mineDaysThisYear = 168
    )
}
