package com.example.aplicacionvariada.feature.calendar.presentation

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.aplicacionvariada.core.ui.components.Background
import com.example.aplicacionvariada.core.ui.components.CalendarMonthCard
import com.example.aplicacionvariada.core.ui.components.CalendarStats
import com.example.aplicacionvariada.core.ui.components.CurrentCycleCard
import com.example.aplicacionvariada.core.ui.components.MineBottomNavigation
import com.example.aplicacionvariada.core.ui.components.Muted
import com.example.aplicacionvariada.core.ui.components.RegimeSelector
import com.example.aplicacionvariada.core.ui.components.YearlySummaryCard
import com.example.aplicacionvariada.feature.calendar.data.RegimeDataStore
import com.example.aplicacionvariada.feature.calendar.presentation.components.CalendarHeader
import com.example.aplicacionvariada.feature.calendar.presentation.components.CalendarIntro
import com.example.aplicacionvariada.feature.calendar.presentation.components.*
import kotlinx.datetime.TimeZone
import kotlinx.datetime.todayIn
import kotlin.time.Clock

@Composable
fun CalendarScreen(
    regimeDataStore: RegimeDataStore,
    userName: String = "Juan",
    onNavigate: (String) -> Unit = {}
) {
    val today = Clock.System.todayIn(TimeZone.currentSystemDefault())
    var monthIndex by remember { mutableIntStateOf(today.monthNumber - 1) }
    var year by remember { mutableIntStateOf(today.year) }
    var selectedDay by remember { mutableIntStateOf(today.dayOfMonth) }
    var showRegimeDialog by remember { mutableStateOf(false) }

    val calendarViewModel = remember(regimeDataStore) { CalendarViewModel(regimeDataStore) }
    val uiState by calendarViewModel.uiState.collectAsState()

    val monthNames = listOf(
        "Enero", "Febrero", "Marzo", "Abril", "Mayo", "Junio",
        "Julio", "Agosto", "Septiembre", "Octubre", "Noviembre", "Diciembre"
    )

    LaunchedEffect(monthIndex, year, today, uiState.mineDays, uiState.restDays, uiState.firstMineDate) {
        calendarViewModel.updateCalendar(monthIndex = monthIndex, year = year, today = today)
    }

    Scaffold(
        modifier = Modifier.statusBarsPadding(),
        containerColor = Background,
        topBar = { CalendarHeader(userName = userName) },
        bottomBar = {
            MineBottomNavigation(selected = "Calendario", onSelected = onNavigate)
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .verticalScroll(rememberScrollState())
                .padding(horizontal = 22.dp)
                .padding(top = 26.dp, bottom = 28.dp),
            verticalArrangement = Arrangement.spacedBy(22.dp)
        ) {
            CalendarIntro()
            RegimeSelector(onClick = { showRegimeDialog = true })

            CalendarStats(
                mineDaysThisMonth = uiState.monthlySummary.mineDays,
                restDaysThisMonth = uiState.monthlySummary.restDays,
                year = year,
                mineDaysThisYear = uiState.yearlySummary.mineDays
            )

            CalendarMonthCard(
                monthName = monthNames[monthIndex],
                year = year,
                days = uiState.days,
                selectedDay = selectedDay,
                onPreviousMonth = {
                    if (monthIndex == 0) {
                        monthIndex = 11
                        year = (year - 1).coerceAtLeast(1)
                    } else {
                        monthIndex--
                    }
                },
                onNextMonth = {
                    if (monthIndex == 11) {
                        monthIndex = 0
                        year = (year + 1).coerceAtMost(9999)
                    } else {
                        monthIndex++
                    }
                },
                onGoToToday = {
                    monthIndex = today.monthNumber - 1
                    year = today.year
                    selectedDay = today.dayOfMonth
                },
                onDaySelected = { selectedDay = it }
            )

            CurrentCycleCard(
                todayCycleDay = uiState.todayCycleDay,
                cycleLength = uiState.cycleLength,
                regime = "${uiState.mineDays} / ${uiState.restDays}",
                mineDays = uiState.mineDays,
                restDays = uiState.restDays,
                nextMineDate = uiState.nextMineDate ?: today,
                nextRestDate = uiState.nextRestDate ?: today,
                previousCycles = uiState.previousCycles
            )

            YearlySummaryCard(
                year = year,
                mineDays = uiState.yearlySummary.mineDays,
                restDays = uiState.yearlySummary.restDays
            )

            Text(
                text = "Los cálculos se actualizan automáticamente según tu régimen.",
                modifier = Modifier.padding(vertical = 10.dp),
                color = Muted,
                textAlign = TextAlign.Center,
                fontSize = 14.sp
            )
        }

        if (showRegimeDialog) {
            RegimeDialog(
                initialMineDays = uiState.mineDays,
                initialRestDays = uiState.restDays,
                initialDate = uiState.firstMineDate,
                initialName = uiState.regimeName,
                initialNotes = uiState.regimeNotes,
                onDismiss = { showRegimeDialog = false },
                onSave = { mine, rest, date, name, notes ->
                    calendarViewModel.saveRegime(
                        mineDays = mine,
                        restDays = rest,
                        firstMineDate = date,
                        regimeName = name,
                        regimeNotes = notes
                    )
                    showRegimeDialog = false
                }
            )
        }
    }
}
