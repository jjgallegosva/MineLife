package com.example.aplicacionvariada.core.ui.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.material3.MaterialTheme
import androidx.compose.ui.unit.dp

@Composable
fun RegimeTextField(
    value: String,
    onValueChange: (String) -> Unit,
    modifier: Modifier = Modifier,
    placeholder: String = "",
    numeric: Boolean = false,
    enabled: Boolean = true
) {
    OutlinedTextField(
        value = value,
        onValueChange = onValueChange,
        modifier = modifier.fillMaxWidth(),
        enabled = enabled,
        singleLine = true,
        placeholder = { Text(placeholder, color = RegimeColors.Muted) },
        shape = RoundedCornerShape(20.dp),
        keyboardOptions = KeyboardOptions(
            keyboardType = if (numeric) KeyboardType.Number else KeyboardType.Text
        ),
        colors = OutlinedTextFieldDefaults.colors(
            focusedBorderColor = RegimeColors.Orange,
            unfocusedBorderColor = RegimeColors.Border,
            focusedContainerColor = RegimeColors.Background,
            unfocusedContainerColor = RegimeColors.Background
        )
    )
}

@Preview(showBackground = true)
@Composable
private fun RegimeTextFieldPreview() {
    MaterialTheme {
        RegimeTextField(value = "14", onValueChange = {}, placeholder = "Días en mina", numeric = true)
    }
}
