package com.example.aplicacionvariada.feature.jornadaLaboral.presentation.ui

import JornadaPattern
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.aplicacionvariada.feature.jornadaLaboral.presentation.ui.components.ClickableInfoCard
import com.example.aplicacionvariada.feature.jornadaLaboral.presentation.ui.components.MiniSummaryCard
import com.example.aplicacionvariada.feature.jornadaLaboral.presentation.ui.components.ProjectionCard
import com.example.aplicacionvariada.feature.jornadaLaboral.presentation.ui.components.SectionCard
import com.example.aplicacionvariada.feature.jornadaLaboral.presentation.ui.components.SelectChip
import com.example.aplicacionvariada.getCurrentDate
import findNextMineAfterRest
import findNextRestDay
import formatDate
import formatShortDate
import generateJornadaProjection
import jornadaPatterns
import kotlinx.datetime.LocalDate
import pe.compartamos.designsystem.components.atoms.calendar.Calendar
import pe.compartamos.designsystem.components.atoms.calendar.CalendarCallbacks
import pe.compartamos.designsystem.components.atoms.calendar.CalendarEventsConfig

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ConsultarJornadaScreen(
    modifier: Modifier = Modifier,
    onOpenCalendar: (
        startDate: LocalDate,
        pattern: JornadaPattern,
        range: Int
    ) -> Unit,
    onBack: () -> Unit
) {
    var selectedPattern by remember {
        mutableStateOf(jornadaPatterns.first())
    }

    var showCalendarDialog by remember {
        mutableStateOf(false)
    }

    var selectedRange by remember {
        mutableStateOf<Int?>(60)
    }

    var startDate by remember {
        mutableStateOf(LocalDate(2025, 5, 26))
    }

    val today = remember {
        getCurrentDate()
    }

    var year by remember {
        mutableStateOf(today.year)
    }

    var month by remember {
        mutableStateOf(today.month)
    }

    var showCustomRange by remember {
        mutableStateOf(false)
    }

    var customRange by remember {
        mutableStateOf("120")
    }

    var selectedYear by remember {
        mutableStateOf(today.year)
    }

    var selectedMonth by remember {
        mutableStateOf(today.month)
    }

    var selectedDay by remember {
        mutableStateOf(today.day)
    }

    val keyboardController = LocalSoftwareKeyboardController.current
    val focusManager = LocalFocusManager.current

    val totalDaysToUse = selectedRange ?: customRange.toIntOrNull() ?: 60

    val projection = remember(selectedPattern, totalDaysToUse, startDate) {
        generateJornadaProjection(
            startDate = startDate,
            pattern = selectedPattern,
            totalDays = totalDaysToUse
        )
    }

    val nextRest = findNextRestDay(projection)
    val nextMine = findNextMineAfterRest(projection)
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(
                Color(0xFFF7F3EE)

            )
    ) {
        Scaffold(
            modifier = modifier.fillMaxSize(),
            containerColor = Color.Transparent,
            topBar = {
                CenterAlignedTopAppBar(
                    title = {
                        Text(
                            text = "Consultar jornada",
                            fontSize = 24.sp,
                            fontWeight = FontWeight.SemiBold,
                            color = Color(0xFF191C22),
                            lineHeight = 28.8.sp // 24sp * 120% * 120%
                        )
                    },
                    navigationIcon = {
                        IconButton(
                            onClick = {
                                onBack()
                            }
                        ) {
                            Text(
                                text = "‹",
                                fontSize = 38.sp,
                                color = Color(0xFF8C7A6B)
                            )
                        }
                    },
                    colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
                        containerColor = Color(0xFFF7F3EE)
                    )
                )
            },
            bottomBar = {
                Button(
                    onClick = {
                        onOpenCalendar(
                            startDate,
                            selectedPattern,
                            totalDaysToUse
                        )
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .navigationBarsPadding()
                        .padding(horizontal = 20.dp, vertical = 12.dp)
                        .height(56.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color(0xFF2A2E35)
                    ),
                    shape = RoundedCornerShape(18.dp)
                ) {
                    Text(
                        text = "Ver calendario",
                        color = Color.White,
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold
                    )
                }
            }
        ) { innerPadding ->

            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(innerPadding)
                    .padding(horizontal = 20.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                item {

                    Text(
                        text = "Selecciona tu jornada y fecha de inicio para proyectar tus días en mina y descanso.",
                        modifier = Modifier.fillMaxWidth(),
                        textAlign = TextAlign.Center,
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Normal,
                        color = Color(0xFF6E7480),
                        lineHeight = 23.2.sp // 16sp * 145%
                    )
                }

                item {
                    SectionCard(title = "Mi jornada") {
                        FlowRow(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.spacedBy(12.dp),
                            verticalArrangement = Arrangement.spacedBy(12.dp),
                            maxItemsInEachRow = 3
                        ) {
                            jornadaPatterns.forEach { pattern ->
                                SelectChip(
                                    text = pattern.name,
                                    selected = selectedPattern.name == pattern.name,
                                    modifier = Modifier.weight(1f),
                                    onClick = {
                                        selectedPattern = pattern
                                    }
                                )
                            }
                        }
                    }
                }

                item {
                    ClickableInfoCard(
                        iconText = "📅",
                        title = "Inicio del ciclo",

                        value = formatDate(startDate),
                        onClick = {
                            showCalendarDialog = true
                        }
                    )
                }

                item {
                    SectionCard(title = "Horizonte de consulta") {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.spacedBy(8.dp)
                        ) {
                            listOf(60, 90).forEach { range ->
                                SelectChip(
                                    text = "$range días",
                                    selected = selectedRange == range,
                                    modifier = Modifier.weight(1f),
                                    onClick = {
                                        selectedRange = range
                                        showCustomRange = false
                                    }
                                )
                            }

                            SelectChip(
                                text = "Personalizado",
                                selected = selectedRange == null,
                                modifier = Modifier.weight(1f),
                                onClick = {
                                    selectedRange = null
                                    showCustomRange = true
                                }
                            )
                        }

                        if (showCustomRange) {
                            Column(
                                modifier = Modifier.fillMaxWidth()
                            ) {
                                Spacer(modifier = Modifier.height(12.dp))

                                Text(
                                    text = "Horizonte personalizado",
                                    style = MaterialTheme.typography.bodyMedium,
                                    color = Color.Gray
                                )

                                Spacer(modifier = Modifier.height(8.dp))

                                OutlinedTextField(
                                    value = customRange,
                                    onValueChange = {
                                        val onlyNumbers = it.filter { char ->
                                            char.isDigit()
                                        }
                                        customRange = onlyNumbers
                                    },
                                    modifier = Modifier.fillMaxWidth(),
                                    singleLine = true,
                                    suffix = {
                                        Text("días")
                                    },
                                    keyboardOptions = KeyboardOptions(
                                        keyboardType = KeyboardType.Number,
                                        imeAction = ImeAction.Done
                                    ),
                                    keyboardActions = KeyboardActions(
                                        onDone = {
                                            focusManager.clearFocus()
                                            keyboardController?.hide()
                                        }
                                    )
                                )

                                Spacer(modifier = Modifier.height(12.dp))
                            }
                        }
                    }
                }

                item {
                    ProjectionCard(
                        pattern = selectedPattern,
                        startDate = startDate,
                        range = totalDaysToUse
                    )
                }

                item {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(12.dp)
                    ) {
                        MiniSummaryCard(
                            iconText = "🌙",
                            title = "Próximo descanso",
                            value = nextRest?.date?.let {
                                formatShortDate(it)
                            } ?: "-",
                            modifier = Modifier.weight(1f)
                        )

                        MiniSummaryCard(
                            iconText = "\uD83E\uDE96",
                            title = "Próximo reingreso",
                            value = nextMine?.date?.let {
                                formatShortDate(it)
                            } ?: "-",
                            modifier = Modifier.weight(1f)
                        )
                    }
                }

                item {
                    Spacer(modifier = Modifier.height(24.dp))
                }
            }
        }
    }


    if (showCalendarDialog) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.Black.copy(alpha = 0.45f))
                .clickable {
                    showCalendarDialog = false
                },
            contentAlignment = Alignment.Center
        ) {
            Surface(
                shape = RoundedCornerShape(24.dp),
                tonalElevation = 8.dp,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(20.dp)
                    .clickable(enabled = false) {}
            ) {
                Column(
                    modifier = Modifier
                        .background(Color.White)
                        .padding(16.dp)
                ) {
                    Text(
                        text = "Selecciona una fecha",
                        style = MaterialTheme.typography.titleMedium
                    )

                    Spacer(modifier = Modifier.height(12.dp))

                    Calendar<String>(
                        year = year,
                        month = month,
                        selectedYear = selectedYear,
                        selectedMonth = selectedMonth,
                        selectedDay = selectedDay,
                        eventsConfig = CalendarEventsConfig(
                            events = emptyList(),
                            markedDates = emptyList(),
                            eventDateExtractor = {
                                null
                            }
                        ),
                        callbacks = CalendarCallbacks(
                            onPrev = {},
                            onNext = {},
                            onDayClick = { day, monthClicked ->
                                selectedYear = year
                                selectedMonth = monthClicked
                                selectedDay = day

                                startDate = LocalDate(
                                    year = selectedYear,
                                    monthNumber = selectedMonth,
                                    dayOfMonth = selectedDay
                                )

                                showCalendarDialog = false
                            },
                            onPageChange = { newYear, newMonth ->
                                year = newYear
                                month = newMonth
                            }
                        )
                    )

                    Spacer(modifier = Modifier.height(12.dp))

                    Button(
                        onClick = {
                            showCalendarDialog = false
                        },
                        modifier = Modifier.align(Alignment.End)
                    ) {
                        Text("Cerrar")
                    }
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun ConsultarJornadaScreenPreview() {
    ConsultarJornadaScreen(
        onOpenCalendar = { startDate, pattern, range ->
            // Preview: no hace nada
        },
        onBack = {
            // Preview: no hace nada
        }
    )
}