package com.example.aplicacionvariada.feature.calendar.presentation.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Construction
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.tooling.preview.Preview
import com.example.aplicacionvariada.core.ui.components.Background
import com.example.aplicacionvariada.core.ui.components.BorderColor
import com.example.aplicacionvariada.core.ui.components.Navy
import com.example.aplicacionvariada.core.ui.components.Orange

@Composable
fun CalendarHeader(userName: String) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(Background)
            .border(width = 0.5.dp, color = BorderColor)
            .padding(horizontal = 20.dp, vertical = 14.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Box(
            modifier = Modifier
                .size(48.dp)
                .clip(RoundedCornerShape(16.dp))
                .background(Orange),
            contentAlignment = Alignment.Center
        ) {
            Icon(
                imageVector = Icons.Outlined.Construction,
                contentDescription = "MineLife",
                tint = Color.White,
                modifier = Modifier.size(27.dp)
            )
        }

        Spacer(Modifier.width(14.dp))

        Text(
            text = "MineLife",
            fontSize = 21.sp,
            fontWeight = FontWeight.Bold,
            color = Color(0xFF171D28)
        )

        Spacer(Modifier.weight(1f))

        Box(
            modifier = Modifier
                .size(50.dp)
                .clip(CircleShape)
                .background(Navy),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = userName.take(2).uppercase(),
                color = Color.White,
                fontWeight = FontWeight.SemiBold
            )
        }
    }
}

@Preview
@Composable
private fun CalendarHeaderPreview() {
    CalendarHeader(userName = "Juan")
}
