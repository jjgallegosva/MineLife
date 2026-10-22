package com.example.aplicacionvariada.core.ui.components

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp

@Composable
fun RegimeFieldLabel(text: String) {
    Text(
        text = text,
        color = RegimeColors.Text,
        fontSize = 17.sp,
        fontWeight = FontWeight.Medium
    )
}

@Preview(showBackground = true)
@Composable
private fun RegimeFieldLabelPreview() {
    MaterialTheme {
        RegimeFieldLabel("Días en mina")
    }
}
