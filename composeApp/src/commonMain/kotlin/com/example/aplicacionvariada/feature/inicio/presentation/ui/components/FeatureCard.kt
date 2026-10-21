package com.example.aplicacionvariada.feature.inicio.presentation.ui.components

import androidx.compose.foundation.clickable
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
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import aplicacionvariada.composeapp.generated.resources.Res
import aplicacionvariada.composeapp.generated.resources.icon_peluqueria
import org.jetbrains.compose.resources.painterResource

@Composable
fun FeatureCard(
    modifier: Modifier = Modifier,
    title: String,
    subtitle: String,
    icon: Painter,
    iconBg: Color,
    onClick: () -> Unit = {}
) {
    Surface(
        modifier = modifier.clickable {
            onClick()
        },
        shape = RoundedCornerShape(24.dp),
        color = Color(0xFDFDFDFD),
        tonalElevation = 3.dp,
        shadowElevation = 4.dp
    ) {
        Column(
            modifier = Modifier.padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Box(
                modifier = Modifier
                    .size(52.dp)
                    .clip(CircleShape),
                   // .background(iconBg),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    painter = icon,
                    contentDescription = null,
                   // tint = Color(0xFF4A4A4A)
                    tint = Color.Unspecified,
                )
            }

            Text(
                text = title,
                fontSize = 18.sp,
                fontWeight = FontWeight.Medium,
                color = Color(0xFF191C22)
            )

            Text(
                text = subtitle,
                style = MaterialTheme.typography.bodySmall,
                textAlign = TextAlign.Center,
                color = Color.Gray
            )
        }
    }
}
@Preview(
    name = "Feature Card Preview",
    showBackground = true,
    backgroundColor = 0xFFF5F5F5
)
@Composable
fun FeatureCardPreview() {
    MaterialTheme {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(24.dp),
            contentAlignment = Alignment.Center
        ) {
            FeatureCard(
                modifier = Modifier.fillMaxWidth(),
                title = "Aplicación variada",
                subtitle = "Explora diferentes funciones disponibles dsdasdasda dasdasd",
                icon = painterResource(Res.drawable.icon_peluqueria),
                iconBg = Color(0xFFE3F2FD)
            )
        }
    }
}