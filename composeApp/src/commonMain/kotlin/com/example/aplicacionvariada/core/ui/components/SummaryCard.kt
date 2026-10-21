package com.minelife.core.ui.components

import androidx.compose.material.icons.outlined.Backpack

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.ArrowForward
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.tooling.preview.Preview

@Composable
fun SummaryCard(
    title: String,
    value: String,
    subtitle: String,
    icon: ImageVector,
    tint: Color,
    accent: Color,
    modifier: Modifier = Modifier,
    onClick: () -> Unit
) {
    var isSelected by remember { mutableStateOf(false) }

    val shape = RoundedCornerShape(30.dp)
    val borderColor = if (isSelected) {
        Color(0xFFFFA078)
    } else {
        Color(0xFFE2E8F0)
    }

    Column(
        modifier = modifier
            .heightIn(min = 220.dp)
            .clip(shape)
            .background(Color.White)
            .border(
                width = if (isSelected) 2.dp else 1.dp,
                color = borderColor,
                shape = shape
            )
            .clickable {
                isSelected = !isSelected
                onClick()
            }
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        // Icono y flecha
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(
                modifier = Modifier
                    .size(44.dp)
                    .clip(RoundedCornerShape(16.dp))
                    .background(tint),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = icon,
                    contentDescription = null,
                    tint = accent,
                    modifier = Modifier.size(23.dp)
                )
            }

            Spacer(Modifier.weight(1f))

            Icon(
                imageVector = Icons.Outlined.ArrowForward,
                contentDescription = "Abrir $title",
                tint = Color(0xFF94A3B8),
                modifier = Modifier.size(20.dp)
            )
        }

        Spacer(Modifier.height(4.dp))

        Text(
            text = title,
            color = Color(0xFF64748B),
            fontSize = 11.sp,
            lineHeight = 16.sp,
            fontWeight = FontWeight.Bold,
            letterSpacing = 1.sp
        )

        Text(
            text = value,
            color = Color(0xFF171D28),
            fontSize = 20.sp,
            lineHeight = 25.sp,
            fontWeight = FontWeight.Bold
        )

        Text(
            text = subtitle,
            color = Color(0xFF64748B),
            fontSize = 13.sp,
            lineHeight = 19.sp
        )
    }
}

@Preview
@Composable
private fun SummaryCardPreview() {
    SummaryCard(
        title = "MOCHILA",
        value = "0% lista",
        subtitle = "turno2",
        icon = Icons.Outlined.Backpack,
        tint = Color(0xFFE7F2ED),
        accent = Color(0xFF168653),
        modifier = Modifier.width(150.dp),
        onClick = {}
    )
}
