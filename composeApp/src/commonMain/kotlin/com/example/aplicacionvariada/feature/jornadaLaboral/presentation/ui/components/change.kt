package com.example.aplicacionvariada.feature.jornadaLaboral.presentation.ui.components

import JornadaDay
import androidx.compose.runtime.Composable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape

import androidx.compose.material3.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import findNextMineAfterRest
import findNextRestDay
import formatShortDate

@Composable
fun ChangesListCard(
    projection: List<JornadaDay>
) {
    val nextRest = findNextRestDay(projection)
    val nextMine = findNextMineAfterRest(projection)

    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(22.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(defaultElevation = 3.dp)
    ) {
        Column(modifier = Modifier.padding(18.dp)) {
            Text(
                text = "Cambios próximos",
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
                color = Color(0xFF111827)
            )

            Spacer(modifier = Modifier.height(12.dp))

            ChangeRow(
                iconText = "🌙",
                title = "Cambio a descanso:",
                value = nextRest?.date?.let { formatShortDate(it) } ?: "-"
            )

            Spacer(modifier = Modifier.height(12.dp))

            ChangeRow(
                iconText = "⛑",
                title = "Reingreso a mina:",
                value = nextMine?.date?.let { formatShortDate(it) } ?: "-"
            )
        }
    }
}

@Composable
fun ChangeRow(
    iconText: String,
    title: String,
    value: String
) {
    Row(
        verticalAlignment = Alignment.CenterVertically
    ) {
        CircleTextIcon(iconText)

        Spacer(modifier = Modifier.width(14.dp))

        Column(modifier = Modifier.weight(1f)) {
            Text(
                text = title,
                color = Color(0xFF6B7280),
                fontSize = 15.sp
            )

            Text(
                text = value,
                color = Color(0xFF007A3D),
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold
            )
        }

        Text(
            text = "›",
            color = Color(0xFF007A3D),
            fontSize = 28.sp
        )
    }
}