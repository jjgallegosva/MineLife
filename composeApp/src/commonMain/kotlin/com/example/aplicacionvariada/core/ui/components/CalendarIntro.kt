package com.example.aplicacionvariada.feature.calendar.presentation.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.graphics.Color
import com.example.aplicacionvariada.core.ui.components.Muted
import com.example.aplicacionvariada.core.ui.components.Orange

@Composable
fun CalendarIntro() {
    Column(verticalArrangement = Arrangement.spacedBy(7.dp)) {
        Text(
            text = "CICLO DE TRABAJO",
            color = Orange,
            fontSize = 13.sp,
            fontWeight = FontWeight.Bold,
            letterSpacing = 2.sp
        )

        Text(
            text = "Calendario minero",
            fontSize = 29.sp,
            fontWeight = FontWeight.Bold,
            color = Color(0xFF171D28)
        )

        Text(
            text = "Tus días en mina, tus días libres y cada excepción en un solo lugar.",
            fontSize = 18.sp,
            lineHeight = 27.sp,
            color = Muted
        )
    }
}

@Preview
@Composable
private fun CalendarIntroPreview() {
    CalendarIntro()
}
