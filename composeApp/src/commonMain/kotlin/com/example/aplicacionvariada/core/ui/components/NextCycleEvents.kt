package com.example.aplicacionvariada.core.ui.components

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.North
import androidx.compose.material.icons.outlined.South
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.aplicacionvariada.core.ui.components.CalendarEvent
import kotlinx.datetime.LocalDate

@Composable
fun NextCycleEvents(
    todayCycleDay: Int,
    cycleLength: Int,
    mineDays: Int,
    nextMineDate: LocalDate,
    nextRestDate: LocalDate
) {
    CalendarEvent(
        icon = Icons.Outlined.South,
        title = "Próximo ingreso a mina",
        date = "${nextMineDate.dayOfMonth} ${nextMineDate.month.name.lowercase().take(3)}",
        detail = if (todayCycleDay <= mineDays) {
            "Estás en tu ciclo de mina"
        } else {
            "Faltan ${(cycleLength - todayCycleDay + 1).coerceAtLeast(0)} días"
        }
    )

    Spacer(Modifier.height(18.dp))

    CalendarEvent(
        icon = Icons.Outlined.North,
        title = "Próxima salida de mina",
        date = "${nextRestDate.dayOfMonth} ${nextRestDate.month.name.lowercase().take(3)}",
        detail = "Después de $mineDays días en mina"
    )
}

@Preview(showBackground = true)
@Composable
private fun NextCycleEventsPreview() {
    NextCycleEvents(
        todayCycleDay = 8,
        cycleLength = 21,
        mineDays = 14,
        nextMineDate = LocalDate(2026, 11, 3),
        nextRestDate = LocalDate(2026, 11, 17)
    )
}
