package com.example.aplicacionvariada.feature.calendar.presentation

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import androidx.compose.ui.tooling.preview.Preview
import com.example.aplicacionvariada.core.ui.components.RegimeColors
import com.example.aplicacionvariada.core.ui.components.*


private data class RegimePreset(
    val name: String,
    val mine: Int,
    val rest: Int
)

/**
 * Diálogo configurable y reutilizable para editar un régimen de trabajo/descanso.
 */
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
    val presets = remember {
        listOf(
            RegimePreset("14 × 7", 14, 7),
            RegimePreset("14 × 14", 14, 14),
            RegimePreset("20 × 10", 20, 10),
            RegimePreset("10 × 5", 10, 5)
        )
    }

    var mineDays by remember { mutableStateOf(initialMineDays.toString()) }
    var restDays by remember { mutableStateOf(initialRestDays.toString()) }
    var firstDate by remember { mutableStateOf(initialDate) }
    var regimeName by remember { mutableStateOf(initialName) }
    var notes by remember { mutableStateOf(initialNotes) }
    var customSelected by remember { mutableStateOf(false) }
    var error by remember { mutableStateOf<String?>(null) }

    val selectedPreset = presets.firstOrNull {
        it.mine.toString() == mineDays && it.rest.toString() == restDays
    }

    Dialog(
        onDismissRequest = onDismiss,
        properties = DialogProperties(usePlatformDefaultWidth = false)
    ) {
        Surface(
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight(0.94f)
                .padding(horizontal = 2.dp),
            shape = RoundedCornerShape(30.dp),
            color = RegimeColors.Background
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .verticalScroll(rememberScrollState())
                    .padding(horizontal = 32.dp, vertical = 10.dp)
            ) {
                Box(
                    modifier = Modifier.fillMaxWidth(),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = "Configura tu régimen",
                        fontSize = 24.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color(0xFF171D28)
                    )
                    TextButton(
                        onClick = onDismiss,
                        modifier = Modifier.align(Alignment.CenterEnd),
                        contentPadding = PaddingValues(horizontal = 4.dp)
                    ) {
                        Text("×", color = RegimeColors.Muted, fontSize = 28.sp)
                    }
                }

                Spacer(Modifier.height(16.dp))
                Text("Régimen habitual", fontSize = 18.sp, fontWeight = FontWeight.Medium)
                Spacer(Modifier.height(10.dp))

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    presets.forEach { preset ->
                        RegimePresetChip(
                            label = preset.name,
                            selected = selectedPreset == preset && !customSelected,
                            modifier = Modifier.weight(1f),
                            onClick = {
                                mineDays = preset.mine.toString()
                                restDays = preset.rest.toString()
                                regimeName = "Régimen ${preset.name}"
                                customSelected = false
                                error = null
                            }
                        )
                    }
                }

                Spacer(Modifier.height(8.dp))
                RegimePresetChip(
                    label = "Personalizado",
                    selected = customSelected,
                    onClick = {
                        customSelected = true
                        error = null
                    }
                )

                Spacer(Modifier.height(28.dp))
                Row(horizontalArrangement = Arrangement.spacedBy(16.dp)) {
                    Column(Modifier.weight(1f)) {
                        RegimeFieldLabel("Días en mina")
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
                    Column(Modifier.weight(1f)) {
                        RegimeFieldLabel("Días libres")
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
                RegimeFieldLabel("Primer día del turno en mina")
                Spacer(Modifier.height(14.dp))
                RegimeDateField(
                    value = firstDate,
                    onValueChange = {
                        firstDate = it.take(10)
                        error = null
                    },
                    placeholder = "DD/MM/AAAA"
                )
                Spacer(Modifier.height(10.dp))
                Text(
                    text = "Desde esta fecha se repite el ciclo automáticamente, incluso entre meses y años.",
                    fontSize = 14.sp,
                    lineHeight = 21.sp,
                    color = RegimeColors.Muted
                )

                Spacer(Modifier.height(30.dp))
                RegimeFieldLabel("Nombre del régimen")
                Spacer(Modifier.height(12.dp))
                RegimeTextField(
                    value = regimeName,
                    onValueChange = { regimeName = it; error = null },
                    placeholder = "Nombre del régimen"
                )

                Spacer(Modifier.height(30.dp))
                RegimeFieldLabel("Notas (opcional)")
                Spacer(Modifier.height(12.dp))
                RegimeTextField(
                    value = notes,
                    onValueChange = { notes = it },
                    placeholder = "Añade una nota"
                )

                error?.let {
                    Spacer(Modifier.height(12.dp))
                    Text(it, color = MaterialTheme.colorScheme.error, fontSize = 13.sp)
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
                            !isValidRegimeDate(firstDate) ->
                                error = "Ingresa una fecha válida DD/MM/AAAA."
                            regimeName.isBlank() ->
                                error = "Ingresa el nombre del régimen."
                            else -> onSave(mine, rest, firstDate, regimeName.trim(), notes.trim())
                        }
                    },
                    modifier = Modifier.fillMaxWidth().height(50.dp),
                    shape = RoundedCornerShape(19.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = RegimeColors.Navy)
                ) {
                    Text("Guardar régimen", fontSize = 17.sp, fontWeight = FontWeight.Medium)
                }
                Spacer(Modifier.height(16.dp))
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun RegimeDialogPreview() {
    MaterialTheme {
        RegimeDialog(onDismiss = {}, onSave = { _, _, _, _, _ -> })
    }
}
