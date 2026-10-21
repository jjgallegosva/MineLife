package com.example.aplicacionvariada.feature.calendar.presentation.components

import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.graphics.Color
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.aplicacionvariada.core.ui.components.BorderColor
import com.example.aplicacionvariada.core.ui.components.WhiteCard
import kotlinx.datetime.LocalDate

@Composable
fun CurrentCycleCard(
    todayCycleDay: Int,
    cycleLength: Int,
    regime: String,
    mineDays: Int,
    restDays: Int,
    nextMineDate: LocalDate,
    nextRestDate: LocalDate,
    previousCycles: List<Pair<LocalDate, LocalDate>>
) {
    WhiteCard {
        CurrentCycleProgress(
            todayCycleDay = todayCycleDay,
            cycleLength = cycleLength,
            regime = regime,
            mineDays = mineDays,
            restDays = restDays
        )

        Spacer(Modifier.height(22.dp))

        androidx.compose.material3.HorizontalDivider(color = BorderColor)

        Spacer(Modifier.height(22.dp))

        NextCycleEvents(
            todayCycleDay = todayCycleDay,
            cycleLength = cycleLength,
            mineDays = mineDays,
            nextMineDate = nextMineDate,
            nextRestDate = nextRestDate
        )

        Spacer(Modifier.height(22.dp))

        androidx.compose.material3.HorizontalDivider(color = BorderColor)

        Spacer(Modifier.height(22.dp))

        PreviousCyclesSection(
            regime = regime,
            previousCycles = previousCycles
        )
    }
}

@Preview(showBackground = true, backgroundColor = 0xFFF5F7FA)
@Composable
private fun CurrentCycleCardPreview() {
    CurrentCycleCard(
        todayCycleDay = 8,
        cycleLength = 21,
        regime = "14 / 7",
        mineDays = 14,
        restDays = 7,
        nextMineDate = LocalDate(2026, 11, 3),
        nextRestDate = LocalDate(2026, 11, 17),
        previousCycles = listOf(
            LocalDate(2026, 9, 22) to LocalDate(2026, 10, 5),
            LocalDate(2026, 10, 6) to LocalDate(2026, 10, 19)
        )
    )
}
