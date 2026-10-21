package com.example.aplicacionvariada.feature.inicio.presentation.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape

import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import aplicacionvariada.composeapp.generated.resources.Res
import aplicacionvariada.composeapp.generated.resources.check
import aplicacionvariada.composeapp.generated.resources.star
import org.jetbrains.compose.resources.painterResource

@Composable
fun HeroSection() {
    Surface(
        shape = RoundedCornerShape(28.dp),
        tonalElevation = 4.dp,
        shadowElevation = 6.dp,
        modifier = Modifier.fillMaxWidth()
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .background(
                    Color(0xFFF2ECE4)
                )
                .padding(20.dp)
        ) {
            Column(verticalArrangement = Arrangement.spacedBy(10.dp)) {
                Text(
                    text = "Gestión inteligente para servicios variados",
                    fontSize = 28.sp,
                    fontWeight = FontWeight.SemiBold,
                    color = Color(0xFF191C22),
                    lineHeight = 33.6.sp // 28sp * 120%
                )

                Text(
                    text = "Una experiencia moderna para administrar peluquería, minería y otros servicios desde un solo lugar.",
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Normal,
                    color = Color(0xFF6E7480),
                    lineHeight = 23.2.sp // 16sp * 145%
                )

                Row(
                    horizontalArrangement = Arrangement.spacedBy(10.dp)
                ) {
                    AssistChip(
                        onClick = { },
                        label = {
                            Text(
                                text = "Diseño elegante",
                                fontSize = 15.sp,
                                fontWeight = FontWeight.SemiBold,
                                color = Color(0xFF191C22)// o Color(0xFF6E7480)

                            )
                        },
                        leadingIcon = {
                            Icon(painterResource(Res.drawable.star),
                                modifier = Modifier.size(16.dp),tint = Color(0xFFC4A574),
                                contentDescription = null)
                        },
                        colors = AssistChipDefaults.assistChipColors(
                            containerColor = Color.White,
                            labelColor = Color.Black
                        ),

                        shape = RoundedCornerShape(50.dp),
                        border = null
                    )

                    AssistChip(
                        onClick = { },
                        label = {  Text(
                            text = "Todo en uno",
                            fontSize = 15.sp,
                            fontWeight = FontWeight.SemiBold,
                            color = Color(0xFF191C22))// o Color(0xFF6E7480)
                        },
                        leadingIcon = {
                            Icon(painterResource(Res.drawable.check),
                                modifier = Modifier.size(16.dp),tint = Color.Unspecified, contentDescription = null)
                        },
                        colors = AssistChipDefaults.assistChipColors(
                            containerColor = Color.White,
                            labelColor = Color.Black
                        ),
                        shape = RoundedCornerShape(50.dp),
                        border = null

                    )
                }
            }
        }
    }
}
@Preview(showBackground = true)
@Composable
fun HeroSectionPreview() {
    MaterialTheme {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            contentAlignment = Alignment.Center
        ) {
            HeroSection()
        }
    }
}