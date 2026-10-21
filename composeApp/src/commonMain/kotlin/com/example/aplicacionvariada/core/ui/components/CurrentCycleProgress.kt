package com.example.aplicacionvariada.feature.calendar.presentation.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.ui.draw.clip
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.aplicacionvariada.core.ui.components.Muted
import com.example.aplicacionvariada.core.ui.components.Orange

@Composable
fun CurrentCycleProgress(
    todayCycleDay: Int,
    cycleLength: Int,
    regime: String,
    mineDays: Int,
    restDays: Int
) {
    Column {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Text(
                text = "CICLO ACTUAL",
                color = Muted,
                fontWeight = FontWeight.Bold,
                fontSize = 13.sp,
                letterSpacing = 1.4.sp,
                modifier = Modifier.weight(1f)
            )
            Surface(color = Color(0xFFFFEEE6), shape = RoundedCornerShape(20.dp)) {
                Text(
                    text = "0%",
                    modifier = Modifier.padding(horizontal = 14.dp, vertical = 6.dp),
                    color = Orange,
                    fontWeight = FontWeight.SemiBold
                )
            }
        }

        Spacer(Modifier.height(10.dp))

        Text(
            text = "Día $todayCycleDay de $cycleLength",
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold,
            color = Color(0xFF171D28)
        )

        Spacer(Modifier.height(20.dp))

        val progress = if (cycleLength > 0) {
            (todayCycleDay.toFloat() / cycleLength.toFloat()).coerceIn(0f, 1f)
        } else 0f

        Box(
            modifier = Modifier.fillMaxWidth().height(10.dp)
                .clip(CircleShape).background(Color(0xFFE9EDF2))
        ) {
            Box(
                modifier = Modifier.fillMaxWidth(progress).fillMaxHeight()
                    .clip(CircleShape).background(Orange)
            )
        }

        Spacer(Modifier.height(10.dp))

        Text(
            text = "Régimen $regime · $mineDays días en mina, $restDays días libres",
            fontSize = 14.sp,
            color = Muted
        )
    }
}

@Preview(showBackground = true, backgroundColor = 0xFFF5F7FA)
@Composable
private fun CurrentCycleProgressPreview() {
    CurrentCycleProgress(
        todayCycleDay = 8,
        cycleLength = 21,
        regime = "14 / 7",
        mineDays = 14,
        restDays = 7
    )
}
