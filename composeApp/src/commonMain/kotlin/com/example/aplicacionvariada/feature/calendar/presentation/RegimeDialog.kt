package com.example.aplicacionvariada.feature.calendar.presentation

import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import com.example.aplicacionvariada.convertMillisToDate

private val RegimeOrange = Color(0xFFFF650D)
private val RegimeNavy = Color(0xFF10233B)
private val RegimeMuted = Color(0xFF64748B)
private val RegimeBorder = Color(0xFFDCE2E9)
private val RegimeBackground = Color(0xFFF5F7F9)

private data class RegimePreset(
    val name: String,
    val mine: Int,
    val rest: Int
)

@Composable
fun RegimeDialog(
    initialMineDays: Int = 14,
    initialRestDays: Int = 7,
    initialDate: String = "20/10/2026",
    initialName: String = "Régimen 14 × 7",
    initialNotes: String = "",
    onDismiss: () -> Unit,
    onSave: (
        mineDays: Int,
        restDays: Int,
        firstDate: String,
        name: String,
        notes: String
    ) -> Unit
) {
    val presets = listOf(
        RegimePreset("14 × 7", 14, 7),
        RegimePreset("14 × 14", 14, 14),
        RegimePreset("20 × 10", 20, 10),
        RegimePreset("10 × 5", 10, 5)
    )

    var mineDays by remember { mutableStateOf(initialMineDays.toString()) }
    var restDays by remember { mutableStateOf(initialRestDays.toString()) }
    var firstDate by remember { mutableStateOf(initialDate) }
    var regimeName by remember { mutableStateOf(initialName) }
    var notes by remember { mutableStateOf(initialNotes) }
    var customSelected by remember { mutableStateOf(false) }
    var error by remember { mutableStateOf<String?>(null) }
    var showDatePicker by remember { mutableStateOf(false) }

    val selectedPreset = presets.firstOrNull {
        it.mine.toString() == mineDays &&
                it.rest.toString() == restDays
    }

    Dialog(
        onDismissRequest = onDismiss,
        properties = DialogProperties(
            usePlatformDefaultWidth = false
        )
    ) {
        Surface(
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight(0.94f)
                .padding(horizontal = 2.dp),
            shape = RoundedCornerShape(30.dp),
            color = RegimeBackground
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .verticalScroll(rememberScrollState())
                    .padding(horizontal = 32.dp, vertical = 10.dp)
            ) {
                // Encabezado
                Box(
                    modifier = Modifier.fillMaxWidth(),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        "Configura tu régimen",
                        fontSize = 24.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color(0xFF171D28)
                    )

                    Text(
                        "×",
                        modifier = Modifier
                            .align(Alignment.CenterEnd)
                            .clickable { onDismiss() }
                            .padding(start = 12.dp),
                        color = RegimeMuted,
                        fontSize = 28.sp
                    )
                }

                Spacer(Modifier.height(16.dp))

                Text(
                    "Régimen habitual",
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Medium
                )

                Spacer(Modifier.height(10.dp))

                // Primera fila de regímenes
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    presets.take(4).forEach { preset ->
                        PresetChip(
                            label = preset.name,
                            selected = selectedPreset == preset &&
                                    !customSelected,
                            modifier = Modifier.weight(1f)
                        ) {
                            mineDays = preset.mine.toString()
                            restDays = preset.rest.toString()
                            regimeName = "Régimen ${preset.name}"
                            customSelected = false
                            error = null
                        }
                    }
                }

                Spacer(Modifier.height(8.dp))

                PresetChip(
                    label = "Personalizado",
                    selected = customSelected,
                    modifier = Modifier
                ) {
                    customSelected = true
                    error = null
                }

                Spacer(Modifier.height(28.dp))

                // Días de mina y días libres
                Row(horizontalArrangement = Arrangement.spacedBy(16.dp)) {
                    Column(modifier = Modifier.weight(1f)) {
                        FieldLabel("Días en mina")
                        Spacer(Modifier.height(12.dp))

                        RegimeTextField(
                            value = mineDays,
                            onValueChange = {
                                mineDays = it.filter(Char::isDigit).take(3)
                                customSelected = true
                                error = null
                            },
                            numeric = true
                        )
                    }

                    Column(modifier = Modifier.weight(1f)) {
                        FieldLabel("Días libres")
                        Spacer(Modifier.height(12.dp))

                        RegimeTextField(
                            value = restDays,
                            onValueChange = {
                                restDays = it.filter(Char::isDigit).take(3)
                                customSelected = true
                                error = null
                            },
                            numeric = true
                        )
                    }
                }

                Spacer(Modifier.height(30.dp))

                FieldLabel("Primer día del turno en mina")

                Spacer(Modifier.height(14.dp))

                RegimeTextFieldCalendar(
                    value = firstDate,
                    onValueChange = {
                        firstDate = it.take(10)
                        error = null
                    },
                    placeholder = "DD/MM/AAAA",
                    trailingContent = {
                        Text(
                            "▦",
                            color = Color(0xFF171D28),
                            fontSize = 23.sp
                        )
                    }
                )

                Spacer(Modifier.height(10.dp))

                Text(
                    "Desde esta fecha se repite el ciclo automáticamente, incluso entre meses y años.",
                    fontSize = 14.sp,
                    lineHeight = 21.sp,
                    color = RegimeMuted
                )

                Spacer(Modifier.height(30.dp))

                FieldLabel("Nombre del régimen")

                Spacer(Modifier.height(12.dp))

                RegimeTextField(
                    value = regimeName,
                    onValueChange = { regimeName = it },
                    placeholder = "Nombre del régimen"
                )

                Spacer(Modifier.height(30.dp))

                FieldLabel("Notas (opcional)")

                Spacer(Modifier.height(12.dp))

                RegimeTextField(
                    value = notes,
                    onValueChange = { notes = it },
                    placeholder = "Añade una nota"
                )

                if (error != null) {
                    Spacer(Modifier.height(12.dp))
                    Text(
                        error!!,
                        color = MaterialTheme.colorScheme.error,
                        fontSize = 13.sp
                    )
                }

                Spacer(Modifier.height(34.dp))

                Button(
                    onClick = {
                        val mine = mineDays.toIntOrNull()
                        val rest = restDays.toIntOrNull()

                        when {
                            mine == null || mine !in 1..365 ->
                                error = "Ingresa días en mina entre 1 y 365."

                            rest == null || rest !in 1..365 ->
                                error = "Ingresa días libres entre 1 y 365."

                            !isValidDate(firstDate) ->
                                error = "Ingresa una fecha válida DD/MM/AAAA."

                            regimeName.isBlank() ->
                                error = "Ingresa el nombre del régimen."

                            else -> onSave(
                                mine,
                                rest,
                                firstDate,
                                regimeName.trim(),
                                notes.trim()
                            )
                        }
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(50.dp),
                    shape = RoundedCornerShape(19.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = RegimeNavy
                    )
                ) {
                    Text(
                        "Guardar régimen",
                        fontSize = 17.sp,
                        fontWeight = FontWeight.Medium
                    )
                }

                Spacer(Modifier.height(16.dp))
            }
        }
    }
}

@Composable
private fun PresetChip(
    label: String,
    selected: Boolean,
    modifier: Modifier = Modifier,
    onClick: () -> Unit
) {
    Surface(
        modifier = modifier
            .heightIn(min = 40.dp)
            .clip(RoundedCornerShape(24.dp))
            .clickable { onClick() }
            .then(
                if (selected) {
                    Modifier.border(
                        1.5.dp,
                        RegimeOrange,
                        RoundedCornerShape(24.dp)
                    )
                } else {
                    Modifier.border(
                        1.dp,
                        RegimeBorder,
                        RoundedCornerShape(24.dp)
                    )
                }
            ),
        color = if (selected) Color(0xFFFFEEE6)
        else RegimeBackground,
        shape = RoundedCornerShape(24.dp)
    ) {
        Box(
            modifier = Modifier.padding(
                horizontal = 12.dp,
                vertical = 9.dp
            ),
            contentAlignment = Alignment.Center
        ) {
            Text(
                label,
                color = if (selected) RegimeOrange else RegimeMuted,
                fontSize = 14.sp,
                fontWeight = FontWeight.SemiBold,
                maxLines = 1
            )
        }
    }
}

@Composable
private fun FieldLabel(text: String) {
    Text(
        text,
        color = Color(0xFF171D28),
        fontSize = 17.sp,
        fontWeight = FontWeight.Medium
    )
}

@Composable
private fun RegimeTextField(
    value: String,
    onValueChange: (String) -> Unit,
    placeholder: String = "",
    numeric: Boolean = false,
    trailingContent: @Composable (() -> Unit)? = null
) {
    OutlinedTextField(
        value = value,
        onValueChange = onValueChange,
        modifier = Modifier.fillMaxWidth(),
        singleLine = true,
        placeholder = {
            Text(placeholder, color = RegimeMuted)
        },
        trailingIcon = trailingContent,
        shape = RoundedCornerShape(20.dp),
        keyboardOptions = KeyboardOptions(
            keyboardType = if (numeric) {
                KeyboardType.Number
            } else {
                KeyboardType.Text
            }
        ),
        colors = OutlinedTextFieldDefaults.colors(
            focusedBorderColor = RegimeOrange,
            unfocusedBorderColor = RegimeBorder,
            focusedContainerColor = RegimeBackground,
            unfocusedContainerColor = RegimeBackground
        )
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun RegimeTextFieldCalendar(
    value: String,
    onValueChange: (String) -> Unit,
    placeholder: String = "",
    numeric: Boolean = false,
    trailingContent: @Composable (() -> Unit)? = null
) {
    var showDatePicker by remember { mutableStateOf(false) }

    OutlinedTextField(
        value = value,
        onValueChange = {},
        readOnly = true,
        singleLine = true,
        placeholder = {
            Text(placeholder, color = RegimeMuted)
        },
        trailingIcon = {
            IconButton(
                onClick = { showDatePicker = true }
            ) {
                Icon(
                    imageVector = Icons.Default.DateRange,
                    contentDescription = "Seleccionar fecha"
                )
            }
        },
        shape = RoundedCornerShape(20.dp),
        colors = OutlinedTextFieldDefaults.colors(
            focusedBorderColor = RegimeOrange,
            unfocusedBorderColor = RegimeBorder,
            focusedContainerColor = RegimeBackground,
            unfocusedContainerColor = RegimeBackground
        ),
        modifier = Modifier
            .fillMaxWidth()
            .clickable { showDatePicker = true }
    )

    if (showDatePicker) {
        MineDatePicker(
            onDateSelected = { millis ->
                onValueChange(convertMillisToDate(millis))
            },
            onDismiss = {
                showDatePicker = false
            }
        )
    }
}
private fun isValidDate(value: String): Boolean {
    val parts = value.split("/")
    if (parts.size != 3) return false

    val day = parts[0].toIntOrNull() ?: return false
    val month = parts[1].toIntOrNull() ?: return false
    val year = parts[2].toIntOrNull() ?: return false

    if (year !in 1900..2200 || month !in 1..12) return false

    val leapYear = year % 4 == 0 &&
            (year % 100 != 0 || year % 400 == 0)

    val daysInMonth = when (month) {
        2 -> if (leapYear) 29 else 28
        4, 6, 9, 11 -> 30
        else -> 31
    }

    return day in 1..daysInMonth
}
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MineDatePicker(
    initialDateMillis: Long? = null,
    onDateSelected: (Long) -> Unit,
    onDismiss: () -> Unit
) {
    val datePickerState = rememberDatePickerState(
        initialSelectedDateMillis = initialDateMillis
    )

    DatePickerDialog(
        onDismissRequest = onDismiss,
        confirmButton = {
            TextButton(
                onClick = {
                    datePickerState.selectedDateMillis?.let(onDateSelected)
                    onDismiss()
                }
            ) {
                Text("Aceptar")
            }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) {
                Text("Cancelar")
            }
        }
    ) {
        DatePicker(state = datePickerState)
    }
}