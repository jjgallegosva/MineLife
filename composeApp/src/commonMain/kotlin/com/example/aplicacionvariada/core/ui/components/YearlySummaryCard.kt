package com.example.aplicacionvariada.feature.calendar.presentation.components

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.aplicacionvariada.core.ui.components.BorderColor
import com.example.aplicacionvariada.core.ui.components.Muted
import com.example.aplicacionvariada.core.ui.components.SummaryLine
import com.example.aplicacionvariada.core.ui.components.WhiteCard

@Composable
fun YearlySummaryCard(
    year: Int,
    mineDays: Int,
    restDays: Int,
    exceptions: Int = 0
) {
    WhiteCard {
        Text(
            text = "RESUMEN $year",
            color = Muted,
            fontSize = 13.sp,
            fontWeight = FontWeight.Bold,
            letterSpacing = 1.4.sp
        )

        Spacer(Modifier.height(18.dp))

        SummaryLine("Días en mina", mineDays.toString())
        SummaryLine("Días fuera de mina", restDays.toString())

        HorizontalDivider(
            modifier = Modifier.padding(vertical = 12.dp),
            color = BorderColor
        )

        SummaryLine("Excepciones", exceptions.toString())
    }
}

@Preview(showBackground = true, backgroundColor = 0xFFF5F7FA)
@Composable
private fun YearlySummaryCardPreview() {
    YearlySummaryCard(
        year = 2026,
        mineDays = 243,
        restDays = 122,
        exceptions = 0
    )
}
