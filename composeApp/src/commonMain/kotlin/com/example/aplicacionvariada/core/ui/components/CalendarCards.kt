package com.example.aplicacionvariada.core.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun StatCard(
    title: String,
    value: String,
    detail: String,
    valueColor: Color,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .heightIn(min = 138.dp)
            .clip(RoundedCornerShape(30.dp))
            .background(Color.White)
            .border(1.dp, BorderColor, RoundedCornerShape(30.dp))
            .padding(18.dp),
        verticalArrangement = Arrangement.spacedBy(5.dp)
    ) {
        Text(title, color = Muted, fontSize = 11.sp, lineHeight = 17.sp,
            fontWeight = FontWeight.Bold, letterSpacing = 1.sp)
        Text(value, color = valueColor, fontSize = 32.sp, fontWeight = FontWeight.Bold)
        Text(detail, color = Muted, fontSize = 13.sp, lineHeight = 18.sp)
    }
}

@Preview(showBackground = true, name = "StatCard")
@Composable
private fun StatCardPreview() {
    Row(Modifier.padding(12.dp)) {
        StatCard("DÍAS EN MINA", "14", "este mes", Orange, Modifier.weight(1f))
    }
}

@Composable
fun WhiteCard(content: @Composable ColumnScope.() -> Unit) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(32.dp))
            .background(Color.White)
            .border(1.dp, BorderColor, RoundedCornerShape(32.dp))
            .padding(22.dp),
        verticalArrangement = Arrangement.spacedBy(0.dp),
        content = content
    )
}

@Preview(showBackground = true, name = "WhiteCard")
@Composable
private fun WhiteCardPreview() {
    WhiteCard {
        Text("Contenido de ejemplo", color = Navy)
        Text("Puedes colocar cualquier componente aquí.", color = Muted)
    }
}

@Composable
fun PreviousCycle(dates: String, regime: String) {
    Row(
        modifier = Modifier.fillMaxWidth().padding(vertical = 6.dp),
        verticalAlignment = androidx.compose.ui.Alignment.CenterVertically
    ) {
        Text(dates, modifier = Modifier.weight(1f), color = Muted, fontSize = 15.sp)
        Text(regime, color = Color(0xFF171D28), fontSize = 15.sp,
            fontWeight = FontWeight.SemiBold)
    }
}

@Preview(showBackground = true, name = "PreviousCycle")
@Composable
private fun PreviousCyclePreview() {
    PreviousCycle(dates = "1 oct → 14 oct", regime = "14 / 7")
}

@Composable
fun SummaryLine(label: String, value: String) {
    Row(
        modifier = Modifier.fillMaxWidth().padding(vertical = 6.dp),
        verticalAlignment = androidx.compose.ui.Alignment.CenterVertically
    ) {
        Text(label, modifier = Modifier.weight(1f), color = Muted, fontSize = 17.sp)
        Text(value, color = Color(0xFF171D28), fontSize = 18.sp,
            fontWeight = FontWeight.SemiBold)
    }
}

@Preview(showBackground = true, name = "SummaryLine")
@Composable
private fun SummaryLinePreview() {
    SummaryLine("Días en mina", "168")
}
