package com.example.aplicacionvariada.core.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.South
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight

@Composable
fun CalendarEvent(icon: ImageVector, title: String, date: String, detail: String) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(14.dp)
    ) {
        Box(
            modifier = Modifier.size(46.dp).clip(RoundedCornerShape(16.dp))
                .background(Color(0xFFEDF0F4)),
            contentAlignment = Alignment.Center
        ) {
            Icon(icon, contentDescription = null, tint = Muted)
        }
        Column {
            Text(title, color = Muted, fontSize = 15.sp)
            Text(date, color = Color(0xFF171D28), fontSize = 18.sp,
                fontWeight = FontWeight.SemiBold)
            Text(detail, color = Muted, fontSize = 14.sp)
        }
    }
}

@Preview(showBackground = true, name = "CalendarEvent")
@Composable
private fun CalendarEventPreview() {
    CalendarEvent(Icons.Outlined.South, "Próximo ingreso a mina", "3 nov", "Faltan 2 días")
}
