package com.example.aplicacionvariada.feature.calendar.presentation.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Tune
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.aplicacionvariada.core.ui.components.BorderColor

@Composable
fun RegimeSelector(
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    OutlinedButton(
        onClick = onClick,
        modifier = modifier,
        shape = RoundedCornerShape(28.dp),
        border = BorderStroke(1.dp, BorderColor)
    ) {
        Icon(
            imageVector = Icons.Outlined.Tune,
            contentDescription = null,
            modifier = Modifier.size(20.dp)
        )
        Spacer(Modifier.width(12.dp))
        Text(
            text = "Régimen",
            fontSize = 17.sp,
            color = Color(0xFF171D28),
            fontWeight = FontWeight.Normal
        )
    }
}

@Preview
@Composable
private fun RegimeSelectorPreview() {
    RegimeSelector(onClick = {})
}
