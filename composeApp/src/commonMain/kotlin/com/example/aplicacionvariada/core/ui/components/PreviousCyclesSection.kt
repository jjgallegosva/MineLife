package com.example.aplicacionvariada.core.ui.components

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.History
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.aplicacionvariada.core.ui.components.Muted
import com.example.aplicacionvariada.core.ui.components.PreviousCycle
import kotlinx.datetime.LocalDate

@Composable
fun PreviousCyclesSection(
    regime: String,
    previousCycles: List<Pair<LocalDate, LocalDate>>
) {
    Row(verticalAlignment = Alignment.CenterVertically) {
        Icon(Icons.Outlined.History, contentDescription = null, tint = Muted)
        Spacer(Modifier.width(8.dp))
        Text(
            text = "CICLOS ANTERIORES",
            color = Muted,
            fontSize = 13.sp,
            fontWeight = FontWeight.Bold,
            letterSpacing = 1.2.sp
        )
    }

    Spacer(Modifier.height(14.dp))

    if (previousCycles.isEmpty()) {
        Text(
            text = "Todavía no hay ciclos anteriores completos.",
            color = Muted,
            fontSize = 14.sp
        )
    } else {
        previousCycles.forEach { (startDate, endDate) ->
            PreviousCycle(
                dates = "${startDate.dayOfMonth} ${startDate.month.name.lowercase().take(3)} → " +
                    "${endDate.dayOfMonth} ${endDate.month.name.lowercase().take(3)}",
                regime = regime
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun PreviousCyclesSectionPreview() {
    PreviousCyclesSection(
        regime = "14 / 7",
        previousCycles = listOf(
            LocalDate(2026, 9, 22) to LocalDate(2026, 10, 5),
            LocalDate(2026, 10, 6) to LocalDate(2026, 10, 19)
        )
    )
}
