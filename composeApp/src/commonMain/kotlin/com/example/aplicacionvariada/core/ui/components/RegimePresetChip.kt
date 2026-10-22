package com.example.aplicacionvariada.core.ui.components

import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.Alignment
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun RegimePresetChip(
    label: String,
    selected: Boolean,
    modifier: Modifier = Modifier,
    onClick: () -> Unit
) {
    Surface(
        modifier = modifier
            .heightIn(min = 40.dp)
            .clip(RoundedCornerShape(24.dp))
            .clickable(onClick = onClick)
            .border(
                width = if (selected) 1.5.dp else 1.dp,
                color = if (selected) RegimeColors.Orange else RegimeColors.Border,
                shape = RoundedCornerShape(24.dp)
            ),
        color = if (selected) RegimeColors.SelectedBackground else RegimeColors.Background,
        shape = RoundedCornerShape(24.dp)
    ) {
        Box(
            modifier = Modifier.padding(horizontal = 12.dp, vertical = 9.dp),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = label,
                color = if (selected) RegimeColors.Orange else RegimeColors.Muted,
                fontSize = 14.sp,
                fontWeight = FontWeight.SemiBold,
                maxLines = 1
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun RegimePresetChipPreview() {
    MaterialTheme {
        RegimePresetChip(label = "14 × 7", selected = true, onClick = {})
    }
}
