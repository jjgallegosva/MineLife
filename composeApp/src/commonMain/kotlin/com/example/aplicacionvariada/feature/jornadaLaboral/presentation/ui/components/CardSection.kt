package com.example.aplicacionvariada.feature.jornadaLaboral.presentation.ui.components
import JornadaPattern
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape

import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import formatDate
import kotlinx.datetime.LocalDate

@Composable
fun SectionCard(
    title: String,
    content: @Composable ColumnScope.() -> Unit
) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(22.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(defaultElevation = 3.dp)
    ) {
        Column(
            modifier = Modifier.padding(18.dp),
            verticalArrangement = Arrangement.spacedBy(14.dp)
        ) {
            Text(
                text = title,
                fontSize = 20.sp,
                fontWeight = FontWeight.SemiBold,
                color = Color(0xFF191C22),
                lineHeight = 24.sp // 20sp * 120%
            )

            content()
        }
    }
}

@Composable
fun SelectChip(
    text: String,
    selected: Boolean,
    modifier: Modifier = Modifier,
    onClick: () -> Unit
) {
    val background = if (selected) Color(0xFF2A2E35) else Color.White
    val textColor = if (selected) Color.White else Color(0xFF111827)
    val borderColor = if (selected) Color(0xFF007A3D) else Color(0xFFD1D5DB)

    Box(
        modifier = modifier
            .height(48.dp)
            .clip(RoundedCornerShape(14.dp))
            .background(background)
            .border(
                width = 1.dp,
                color = borderColor,
                shape = RoundedCornerShape(14.dp)
            )
            .clickable { onClick() },
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = text,
            color = textColor,
            fontSize = 15.sp,
            fontWeight = if (selected) FontWeight.Bold else FontWeight.Normal

        )
    }
}

@Composable
fun ClickableInfoCard(
    iconText: String,
    title: String,
    value: String,
    onClick: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onClick() },
        shape = RoundedCornerShape(22.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(defaultElevation = 3.dp)
    ) {
        Row(
            modifier = Modifier.padding(18.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            CircleTextIcon(iconText, background = Color(0xFFE7DED2))

            Spacer(modifier = Modifier.width(16.dp))

            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = title,
                    color = Color(0xFF6B7280),
                    fontSize = 16.sp
                )

                Text(
                    text = value,
                    color = Color(0xFF111827),
                    fontSize = 24.sp,
                    fontWeight = FontWeight.Bold
                )
            }

            Text(
                text = "›",
                fontSize = 36.sp,
                color = Color(0xFF8C7A6B)
            )
        }
    }
}

@Composable
fun CircleTextIcon(
    text: String,
    background: Color = Color(0xFFDADDE3),
    textColor: Color = Color(0xFF007A3D)
) {
    Box(
        modifier = Modifier
            .size(56.dp)
            .background(background, CircleShape),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = text,
            fontSize = 24.sp,
            color = textColor
        )
    }
}

@Composable
fun ProjectionCard(
    pattern: JornadaPattern,
    startDate: LocalDate,
    range: Int
) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(22.dp),
        colors = CardDefaults.cardColors(
            containerColor = Color(0xFFF2ECE4)
        )
    ) {
        Row(
            modifier = Modifier.padding(18.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            CircleTextIcon(
                text = "i",
                background = Color(0xFFC4A574),
                textColor = Color.White
            )

            Spacer(modifier = Modifier.width(16.dp))

            Column {
                Text(
                    text = "Proyección activa: ${pattern.name}",
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Medium,
                    color = Color(0xFFC4A574)
                )

                Spacer(modifier = Modifier.height(4.dp))

                Text(
                    text = "Desde el ${formatDate(startDate)} verás tus días en mina y descanso para los próximos $range días.",
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Normal,
                    color = Color(0xFF6E7480),
                    lineHeight = 23.2.sp // 16sp * 145%
                )
            }
        }
    }
}

@Composable
fun MiniSummaryCard(
    iconText: String,
    title: String,
    value: String,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier,
        shape = RoundedCornerShape(22.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(defaultElevation = 3.dp)
    ) {
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            CircleTextIcon(iconText)

            Spacer(modifier = Modifier.height(12.dp))

            Text(
                text = title,
                color = Color(0xFF6B7280),
                fontSize = 14.sp
            )

            Text(
                text = value,
                color = Color(0xFF8C7A6B),
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold
            )
        }
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun JornadaComponentsPreview() {
    MaterialTheme {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(Color(0xFFF3F4F6))
                .padding(20.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            SectionCard(title = "Tipo de jornada") {
                FlowRow(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(12.dp),
                    verticalArrangement = Arrangement.spacedBy(12.dp),
                    maxItemsInEachRow = 3
                ) {
                    SelectChip(
                        text = "14 x 7",
                        selected = true,
                        modifier = Modifier.weight(1f),
                        onClick = {}
                    )

                    SelectChip(
                        text = "20 x 10",
                        selected = false,
                        modifier = Modifier.weight(1f),
                        onClick = {}
                    )
                    SelectChip(
                        text = "20 x 10",
                        selected = false,
                        modifier = Modifier.weight(1f),
                        onClick = {}
                    )
                    SelectChip(
                        text = "20 x 10",
                        selected = false,
                        modifier = Modifier.weight(1f),
                        onClick = {}
                    )
                    SelectChip(
                        text = "20 x 10",
                        selected = false,
                        modifier = Modifier.weight(1f),
                        onClick = {}
                    )
                }
            }

            ClickableInfoCard(
                iconText = "📅",
                title = "Fecha de inicio",
                value = "07/05/2026",
                onClick = {}
            )

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                MiniSummaryCard(
                    iconText = "⛏",
                    title = "Días mina",
                    value = "14",
                    modifier = Modifier.weight(1f)
                )

                MiniSummaryCard(
                    iconText = "🏠",
                    title = "Descanso",
                    value = "7",
                    modifier = Modifier.weight(1f)
                )
            }
        }
    }
}