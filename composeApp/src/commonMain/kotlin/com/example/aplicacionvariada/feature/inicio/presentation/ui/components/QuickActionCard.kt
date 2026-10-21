package com.example.aplicacionvariada.feature.inicio.presentation.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import aplicacionvariada.composeapp.generated.resources.Res
import aplicacionvariada.composeapp.generated.resources.star
import org.jetbrains.compose.resources.painterResource

@Composable
fun QuickActionCard(
    title: String,
    description: String,
    icon: Painter
) {
    Surface(
        shape = RoundedCornerShape(22.dp),
        color = Color(0xFFFFFFFF),
        tonalElevation = 2.dp,
        shadowElevation = 3.dp,
        modifier = Modifier.fillMaxWidth()
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(18.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(14.dp)
        ) {
            Box(
                modifier = Modifier
                    .size(50.dp)
                    .clip(CircleShape),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    painter = icon,
                    contentDescription = null,
                    tint = Color.Unspecified,
                )
            }

            Column(
                modifier = Modifier.weight(1f)
            ) {
                Text(
                    text = title,
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Medium,
                    color = Color(0xFF191C22)
                )
                Text(
                    text = description,
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Normal,
                    color = Color(0xFF6E7480),
                    lineHeight = 19.6.sp // 14sp * 140%
                )
            }

            /*Icon(
                imageVector = Icons.Default.KeyboardArrowRight,
                contentDescription = null,
                tint = Color.Gray
            )*/
        }
    }
}

@Preview(showBackground = true)
@Composable
fun QuickActionCardPreview() {
    MaterialTheme {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(Color(0xFFF2ECE4))
                .padding(16.dp),
            contentAlignment = Alignment.Center
        ) {
            QuickActionCard(
                title = "Peluquería",
                description = "Gestiona citas, clientes y servicios",
                icon = painterResource(Res.drawable.star)
            )
        }
    }
}