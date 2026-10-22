package com.example.aplicacionvariada.core.ui.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.material3.MaterialTheme
import androidx.compose.ui.unit.dp
import com.example.aplicacionvariada.convertMillisToDate

@Composable
fun RegimeDateField(
    value: String,
    onValueChange: (String) -> Unit,
    modifier: Modifier = Modifier,
    placeholder: String = "DD/MM/AAAA"
) {
    var showDatePicker by remember { mutableStateOf(false) }

    OutlinedTextField(
        value = value,
        onValueChange = {},
        modifier = modifier.fillMaxWidth().clickable { showDatePicker = true },
        readOnly = true,
        singleLine = true,
        placeholder = { Text(placeholder, color = RegimeColors.Muted) },
        trailingIcon = {
            IconButton(onClick = { showDatePicker = true }) {
                Icon(Icons.Default.DateRange, contentDescription = "Seleccionar fecha")
            }
        },
        shape = RoundedCornerShape(20.dp),
        colors = OutlinedTextFieldDefaults.colors(
            focusedBorderColor = RegimeColors.Orange,
            unfocusedBorderColor = RegimeColors.Border,
            focusedContainerColor = RegimeColors.Background,
            unfocusedContainerColor = RegimeColors.Background
        )
    )

    if (showDatePicker) {
        MineDatePicker(
            onDateSelected = { millis -> onValueChange(convertMillisToDate(millis)) },
            onDismiss = { showDatePicker = false }
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun RegimeDateFieldPreview() {
    MaterialTheme {
        RegimeDateField(value = "20/10/2026", onValueChange = {})
    }
}
