package com.example.aplicacionvariada.feature.calendar.presentation

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.style.TextAlign
import com.example.aplicacionvariada.convertMillisToDate
import com.example.aplicacionvariada.feature.calendar.domain.RegimeCalculator
import kotlinx.datetime.LocalDate
import kotlin.time.Clock
import kotlinx.datetime.TimeZone
import kotlinx.datetime.todayIn
import kotlinx.datetime.isoDayNumber
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.LaunchedEffect
import com.example.aplicacionvariada.feature.calendar.data.RegimeDataStore
import com.example.aplicacionvariada.feature.calendar.domain.RegimeSettings
import kotlinx.coroutines.launch

private val Navy = Color(0xFF10233B)
private val Orange = Color(0xFFFF650D)
private val Green = Color(0xFF27865D)
private val Blue = Color(0xFF1875F5)
private val Purple = Color(0xFF8846D7)
private val Muted = Color(0xFF64748B)
private val Background = Color(0xFFF4F6F8)
private val BorderColor = Color(0xFFE1E7EE)

private enum class DayType {
    MINE, REST, VACATION, SHIFT_CHANGE, OUTSIDE
}

private data class CalendarDay(
    val day: Int,
    val type: DayType,
    val inCurrentMonth: Boolean = true
)

@Composable
fun CalendarScreen(
    regimeDataStore: RegimeDataStore,
    userName: String = "Juan",
    onNavigate: (String) -> Unit = {}
) {
    var monthIndex by remember { mutableIntStateOf(9) }
    var year by remember { mutableIntStateOf(2026) }
    val today = Clock.System.todayIn(TimeZone.currentSystemDefault())
    val scope = rememberCoroutineScope()
    var selectedDay by remember {
        mutableIntStateOf(today.dayOfMonth)
    }

    var showRegimes by remember { mutableStateOf(false) }
    var showRegimeDialog by remember { mutableStateOf(false) }

    var mineDays by remember { mutableIntStateOf(14) }
    var restDays by remember { mutableIntStateOf(7) }
    var firstMineDate by remember { mutableStateOf("20/10/2026") }
    var regimeName by remember { mutableStateOf("Régimen 14 × 7") }
    var regimeNotes by remember { mutableStateOf("") }
    val monthNames = listOf(
        "Enero", "Febrero", "Marzo", "Abril",
        "Mayo", "Junio", "Julio", "Agosto",
        "Septiembre", "Octubre", "Noviembre", "Diciembre"
    )
    val cycleLength = mineDays + restDays
    val previousCycles = remember(
        today,
        mineDays,
        restDays,
        firstMineDate
    ) {
        val parts = firstMineDate.split("/")

        if (parts.size != 3 || parts.any { it.toIntOrNull() == null }) {
            emptyList()
        } else {
            runCatching {
                RegimeCalculator.getPreviousCycles(
                    firstMineDate = LocalDate(
                        parts[2].toInt(),
                        parts[1].toInt(),
                        parts[0].toInt()
                    ),
                    mineDays = mineDays,
                    restDays = restDays,
                    today = today
                )
            }.getOrDefault(emptyList())
        }
    }
    val selectedDate = LocalDate(year, monthIndex + 1, selectedDay)

    val parts = firstMineDate.split("/")
    val startDate = LocalDate(
        parts[2].toInt(),
        parts[1].toInt(),
        parts[0].toInt()
    )

    val todayElapsed = today.toEpochDays() - startDate.toEpochDays()

    val todayCycleDay =
        ((todayElapsed % cycleLength + cycleLength) % cycleLength) + 1

    val daysUntilNextMine = cycleLength - todayCycleDay + 1

    val nextMineDate = LocalDate.fromEpochDays(
        today.toEpochDays() + daysUntilNextMine
    )

    val nextRestDate = LocalDate.fromEpochDays(
        nextMineDate.toEpochDays() + mineDays
    )


    val monthlySummary = remember(
        monthIndex,
        year,
        mineDays,
        restDays,
        firstMineDate
    ) {
        RegimeCalculator.calculateMonthly(
            month = monthIndex + 1,
            year = year,
            mineDays = mineDays,
            restDays = restDays,
            firstMineDate = firstMineDate
        )
    }

    val yearlySummary = remember(
        year,
        mineDays,
        restDays,
        firstMineDate
    ) {
        RegimeCalculator.calculateYearly(
            year = year,
            mineDays = mineDays,
            restDays = restDays,
            firstMineDate = firstMineDate
        )
    }

    val monthName = monthNames[monthIndex]

    val days = remember(
        monthIndex,
        year,
        mineDays,
        restDays,
        firstMineDate
    ) {
        buildRegimeDays(
            monthIndex = monthIndex,
            year = year,
            mineDays = mineDays,
            restDays = restDays,
            firstMineDate = firstMineDate
        )
    }
    var regime by remember {
        mutableStateOf("$mineDays / $restDays")
    }
    val savedSettings by regimeDataStore.settings.collectAsState(
        initial = RegimeSettings()
    )

    LaunchedEffect(savedSettings) {
        mineDays = savedSettings.mineDays
        restDays = savedSettings.restDays
        firstMineDate = savedSettings.firstMineDate
        regimeName = savedSettings.regimeName
        regimeNotes = savedSettings.regimeNotes
        regime = "${savedSettings.mineDays} / ${savedSettings.restDays}"
    }
    Scaffold(
        modifier = Modifier.statusBarsPadding(),
        containerColor = Background,
        topBar = {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(Background)
                    .border(
                        width = 0.5.dp,
                        color = BorderColor
                    )
                    .padding(horizontal = 20.dp, vertical = 14.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Box(
                    modifier = Modifier
                        .size(48.dp)
                        .clip(RoundedCornerShape(16.dp))
                        .background(Orange),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        Icons.Outlined.Construction,
                        contentDescription = "MineLife",
                        tint = Color.White,
                        modifier = Modifier.size(27.dp)
                    )
                }

                Spacer(Modifier.width(14.dp))

                Text(
                    "MineLife",
                    fontSize = 21.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color(0xFF171D28)
                )

                Spacer(Modifier.weight(1f))

                Box(
                    modifier = Modifier
                        .size(50.dp)
                        .clip(CircleShape)
                        .background(Navy),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        userName.take(2).uppercase(),
                        color = Color.White,
                        fontWeight = FontWeight.SemiBold
                    )
                }
            }
        },
        bottomBar = {
            CalendarBottomNavigation(
                selected = "Calendario",
                onSelected = onNavigate
            )
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
            // Encabezado
            Column(verticalArrangement = Arrangement.spacedBy(7.dp)) {
                Text(
                    "CICLO DE TRABAJO",
                    color = Orange,
                    fontSize = 13.sp,
                    fontWeight = FontWeight.Bold,
                    letterSpacing = 2.sp
                )

                Text(
                    "Calendario minero",
                    fontSize = 29.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color(0xFF171D28)
                )

                Text(
                    "Tus días en mina, tus días libres y cada excepción en un solo lugar.",
                    fontSize = 18.sp,
                    lineHeight = 27.sp,
                    color = Muted
                )
            }

            // Selector de régimen
            Box {
                OutlinedButton(
                    onClick = { showRegimeDialog = true },
                    shape = RoundedCornerShape(28.dp),
                    border = BorderStroke(
                        1.dp,
                        BorderColor
                    )
                ) {
                    Icon(
                        Icons.Outlined.Tune,
                        contentDescription = null,
                        modifier = Modifier.size(20.dp)
                    )
                    Spacer(Modifier.width(12.dp))
                    Text(
                        "Régimen",
                        fontSize = 17.sp,
                        color = Color(0xFF171D28)
                    )
                }

                DropdownMenu(
                    expanded = showRegimes,
                    onDismissRequest = { showRegimes = false }
                ) {
                    listOf("14 / 7", "10 / 5", "20 / 10", "Otro").forEach {
                        DropdownMenuItem(
                            text = { Text(it) },
                            onClick = {
                                regime = it
                                showRegimes = false
                            }
                        )
                    }
                }
            }

            // Indicadores
            Row(horizontalArrangement = Arrangement.spacedBy(12.dp)) {
                StatCard(
                    title = "DÍAS EN MINA",
                    value = monthlySummary.mineDays.toString(),
                    detail = "este mes",
                    valueColor = Orange,
                    modifier = Modifier.weight(1f)
                )

                StatCard(
                    title = "DÍAS LIBRES",
                    value = monthlySummary.restDays.toString(),
                    detail = "este mes",
                    valueColor = Green,
                    modifier = Modifier.weight(1f)
                )

                StatCard(
                    title = "AÑO $year",
                    value = yearlySummary.mineDays.toString(),
                    detail = "días en mina",
                    valueColor = Color(0xFF171D28),
                    modifier = Modifier.weight(1.08f)
                )
            }

            // Calendario mensual
            WhiteCard {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    OutlinedIconButton(
                        onClick = {
                            if (monthIndex == 0) {
                                monthIndex = 11
                                year--
                            } else {
                                monthIndex--
                            }
                        },
                        border = BorderStroke(
                            1.dp, BorderColor
                        )
                    ) {
                        Icon(
                            Icons.Outlined.ChevronLeft,
                            contentDescription = "Mes anterior",
                            tint = Muted
                        )
                    }

                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        Text(
                            "$monthName $year",
                            fontSize = 21.sp,
                            fontWeight = FontWeight.Bold,
                            color = Color(0xFF171D28)
                        )

                        TextButton(
                            onClick = {
                                monthIndex = 9
                                year = 2026
                                selectedDay = 20
                            },
                            contentPadding = PaddingValues(
                                horizontal = 8.dp,
                                vertical = 0.dp
                            )
                        ) {
                            Text("Ir a hoy", color = Orange)
                        }
                    }

                    OutlinedIconButton(
                        onClick = {
                            if (monthIndex == 11) {
                                monthIndex = 0
                                year++
                            } else {
                                monthIndex++
                            }
                        },
                        border = BorderStroke(
                            1.dp, BorderColor
                        )
                    ) {
                        Icon(
                            Icons.Outlined.ChevronRight,
                            contentDescription = "Mes siguiente",
                            tint = Muted
                        )
                    }
                }

                Spacer(Modifier.height(14.dp))

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    listOf("L", "M", "M", "J", "V", "S", "D").forEach {
                        Text(
                            it,
                            modifier = Modifier.width(42.dp),
                            textAlign = TextAlign.Center,
                            fontSize = 14.sp,
                            color = Muted,
                            fontWeight = FontWeight.Medium
                        )
                    }
                }

                Spacer(Modifier.height(12.dp))

                LazyVerticalGrid(
                    columns = GridCells.Fixed(7),
                    modifier = Modifier
                        .fillMaxWidth()
                        .heightIn(max = 360.dp),
                    userScrollEnabled = false,
                    horizontalArrangement = Arrangement.spacedBy(4.dp),
                    verticalArrangement = Arrangement.spacedBy(7.dp)
                ) {
                    items(days) { calendarDay ->
                        val selected =
                            calendarDay.inCurrentMonth &&
                                    calendarDay.day == selectedDay

                        val dayColor = when (calendarDay.type) {
                            DayType.MINE -> Orange
                            DayType.REST -> Green
                            DayType.VACATION -> Blue
                            DayType.SHIFT_CHANGE -> Purple
                            DayType.OUTSIDE -> Color(0xFFFFD4C2)
                        }

                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .aspectRatio(1f)
                                .clip(RoundedCornerShape(15.dp))
                                .background(dayColor)
                                .then(
                                    if (selected) Modifier.border(
                                        2.dp,
                                        Navy,
                                        RoundedCornerShape(15.dp)
                                    ) else Modifier
                                )
                                .clickable {
                                    if (calendarDay.inCurrentMonth) {
                                        selectedDay = calendarDay.day
                                    }
                                },
                            contentAlignment = Alignment.Center
                        ) {
                            Text(
                                calendarDay.day.toString(),
                                fontSize = 15.sp,
                                color = if (
                                    calendarDay.type == DayType.OUTSIDE
                                ) Color.White.copy(alpha = 0.9f)
                                else Color.White,
                                fontWeight = if (selected) {
                                    FontWeight.Bold
                                } else {
                                    FontWeight.Medium
                                }
                            )
                        }
                    }
                }

                HorizontalDivider(
                    modifier = Modifier.padding(vertical = 22.dp),
                    color = BorderColor
                )

                Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(12.dp)
                    ) {
                        LegendItem(Orange, "En mina")
                        LegendItem(Green, "Libre")
                        LegendItem(Blue, "Vacaciones / permiso")
                    }

                    LegendItem(Purple, "Cambio de turno")
                }
            }

            // Ciclo actual
            WhiteCard {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Text(
                        "CICLO ACTUAL",
                        color = Muted,
                        fontWeight = FontWeight.Bold,
                        fontSize = 13.sp,
                        letterSpacing = 1.4.sp,
                        modifier = Modifier.weight(1f)
                    )

                    Surface(
                        color = Color(0xFFFFEEE6),
                        shape = RoundedCornerShape(20.dp)
                    ) {
                        Text(
                            "0%",
                            modifier = Modifier.padding(
                                horizontal = 14.dp,
                                vertical = 6.dp
                            ),
                            color = Orange,
                            fontWeight = FontWeight.SemiBold
                        )
                    }
                }

                Spacer(Modifier.height(10.dp))


                Text(
                    text = "Día $todayCycleDay de $cycleLength" ,
                    fontSize = 24.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color(0xFF171D28)
                )

                Spacer(Modifier.height(20.dp))

                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(10.dp)
                        .clip(CircleShape)
                        .background(Color(0xFFE9EDF2))
                ) {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth(
                                todayCycleDay.toFloat() / cycleLength.toFloat())
                            .fillMaxHeight()
                            .clip(CircleShape)
                            .background(Orange)
                    )
                }

                Spacer(Modifier.height(10.dp))

                Text(
                    text = "Régimen $regime · $mineDays días en mina, $restDays días libres",
                    fontSize = 14.sp,
                    color = Muted
                )

                HorizontalDivider(
                    modifier = Modifier.padding(vertical = 22.dp),
                    color = BorderColor
                )

                CalendarEvent(
                    icon = Icons.Outlined.South,
                    title = "Próximo ingreso a mina",
                    date = "${nextMineDate.dayOfMonth} ${nextMineDate.month.name.lowercase().take(3)}",
                    detail = if (todayCycleDay <= mineDays) {
                        "Estás en tu ciclo de mina"
                    } else {
                        "Faltan ${cycleLength - todayCycleDay + 1} días"
                    }
                )

                Spacer(Modifier.height(18.dp))

                CalendarEvent(
                    icon = Icons.Outlined.North,
                    title = "Próxima salida de mina",
                    date = "${nextRestDate.dayOfMonth} ${nextRestDate.month.name.lowercase().take(3)}",
                    detail = "Después de $mineDays días en mina"
                )

                HorizontalDivider(
                    modifier = Modifier.padding(vertical = 22.dp),
                    color = BorderColor
                )

                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(
                        Icons.Outlined.History,
                        contentDescription = null,
                        tint = Muted
                    )
                    Spacer(Modifier.width(8.dp))
                    Text(
                        "CICLOS ANTERIORES",
                        color = Muted,
                        fontSize = 13.sp,
                        fontWeight = FontWeight.Bold,
                        letterSpacing = 1.2.sp
                    )
                }

                Spacer(Modifier.height(14.dp))

                if (previousCycles.isEmpty()) {
                    Text(
                        text = "Todavía no hay ciclos anteriores completos.",
                        color = Muted,
                        fontSize = 14.sp
                    )
                } else {
                    previousCycles.forEach { (startDate, endDate) ->
                        PreviousCycle(
                            dates = "${startDate.dayOfMonth} ${
                                startDate.month.name.lowercase().take(3)
                            } → ${endDate.dayOfMonth} ${
                                endDate.month.name.lowercase().take(3)
                            }",
                            regime = regime
                        )
                    }
                }
            }

            // Resumen anual
            WhiteCard {
                Text(
                    "RESUMEN $year",
                    color = Muted,
                    fontSize = 13.sp,
                    fontWeight = FontWeight.Bold,
                    letterSpacing = 1.4.sp
                )

                Spacer(Modifier.height(18.dp))

                SummaryLine(
                    "Días en mina",
                    yearlySummary.mineDays.toString()
                )

                SummaryLine(
                    "Días fuera de mina",
                    yearlySummary.restDays.toString()
                )

                HorizontalDivider(
                    modifier = Modifier.padding(vertical = 12.dp),
                    color = BorderColor
                )

                SummaryLine("Excepciones", "0")
            }

            Text(
                "Última actualización del cálculo: 2026-10-20",
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 10.dp),
                color = Muted,
                textAlign = TextAlign.Center,
                fontSize = 14.sp
            )
        }
        if (showRegimeDialog) {
            RegimeDialog(
                initialMineDays = mineDays,
                initialRestDays = restDays,
                initialDate = firstMineDate,
                initialName = regimeName,
                initialNotes = regimeNotes,
                onDismiss = {
                    showRegimeDialog = false
                },

                onSave = { mine, rest, date, name, notes ->
                    mineDays = mine
                    restDays = rest
                    firstMineDate = date
                    regimeName = name
                    regimeNotes = notes

                    // Actualizar también el texto del régimen seleccionado.
                    regime = "$mine / $rest"
                    scope.launch {
                        regimeDataStore.save(
                            RegimeSettings(
                                mineDays = mine,
                                restDays = rest,
                                firstMineDate = date,
                                regimeName = name,
                                regimeNotes = notes
                            )
                        )
                    }
                    println("Régimen: $mine x $rest | Inicio: $date")

                    showRegimeDialog = false
                }

            )
        }
    }
}

@Composable
private fun StatCard(
    title: String,
    value: String,
    detail: String,
    valueColor: Color,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .heightIn(min = 142.dp)
            .clip(RoundedCornerShape(30.dp))
            .background(Color.White)
            .border(1.dp, BorderColor, RoundedCornerShape(30.dp))
            .padding(18.dp),
        verticalArrangement = Arrangement.spacedBy(5.dp)
    ) {
        Text(
            title,
            color = Muted,
            fontSize = 11.sp,
            lineHeight = 17.sp,
            fontWeight = FontWeight.Bold,
            letterSpacing = 1.sp
        )

        Text(
            value,
            color = valueColor,
            fontSize = 32.sp,
            fontWeight = FontWeight.Bold
        )

        Text(
            detail,
            color = Muted,
            fontSize = 13.sp,
            lineHeight = 18.sp
        )
    }
}

@Composable
private fun WhiteCard(content: @Composable ColumnScope.() -> Unit) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(32.dp))
            .background(Color.White)
            .border(1.dp, BorderColor, RoundedCornerShape(32.dp))
            .padding(22.dp),
        verticalArrangement = Arrangement.spacedBy(0.dp),
        content = content
    )
}

@Composable
private fun LegendItem(color: Color, label: String) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(7.dp)
    ) {
        Box(
            modifier = Modifier
                .size(13.dp)
                .clip(CircleShape)
                .background(color)
        )

        Text(
            label,
            fontSize = 13.sp,
            color = Muted
        )
    }
}

@Composable
private fun CalendarEvent(
    icon: ImageVector,
    title: String,
    date: String,
    detail: String
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(14.dp)
    ) {
        Box(
            modifier = Modifier
                .size(46.dp)
                .clip(RoundedCornerShape(16.dp))
                .background(Color(0xFFEDF0F4)),
            contentAlignment = Alignment.Center
        ) {
            Icon(icon, contentDescription = null, tint = Muted)
        }

        Column {
            Text(title, color = Muted, fontSize = 15.sp)
            Text(
                date,
                color = Color(0xFF171D28),
                fontSize = 18.sp,
                fontWeight = FontWeight.SemiBold
            )
            Text(detail, color = Muted, fontSize = 14.sp)
        }
    }
}

@Composable
private fun PreviousCycle(
    dates: String,
    regime: String
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 6.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            dates,
            modifier = Modifier.weight(1f),
            color = Muted,
            fontSize = 15.sp
        )

        Text(
            regime,
            color = Color(0xFF171D28),
            fontSize = 15.sp,
            fontWeight = FontWeight.SemiBold
        )
    }
}

@Composable
private fun SummaryLine(
    label: String,
    value: String
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 6.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            label,
            modifier = Modifier.weight(1f),
            color = Muted,
            fontSize = 17.sp
        )

        Text(
            value,
            color = Color(0xFF171D28),
            fontSize = 18.sp,
            fontWeight = FontWeight.SemiBold
        )
    }
}

@Composable
private fun CalendarBottomNavigation(
    selected: String,
    onSelected: (String) -> Unit
) {
    val items = listOf(
        Triple("Inicio", Icons.Outlined.Dashboard, "Inicio"),
        Triple("Calendario", Icons.Outlined.CalendarMonth, "Calendario"),
        Triple("Mochila", Icons.Outlined.Backpack, "Mochila"),
        Triple("Gastos", Icons.Outlined.AccountBalanceWallet, "Gastos"),
        Triple("Más", Icons.Outlined.MoreHoriz, "Más")
    )

    NavigationBar(
        containerColor = Color.White,
        tonalElevation = 4.dp
    ) {
        items.forEach { (label, icon, route) ->
            NavigationBarItem(
                selected = selected == route,
                onClick = { onSelected(route) },
                icon = {
                    Box(
                        modifier = Modifier
                            .clip(RoundedCornerShape(15.dp))
                            .background(
                                if (selected == route) Color(0xFFFFEEE6)
                                else Color.Transparent
                            )
                            .padding(horizontal = 13.dp, vertical = 8.dp)
                    ) {
                        Icon(
                            icon,
                            contentDescription = label,
                            tint = if (selected == route) Orange else Muted
                        )
                    }
                },
                label = { Text(label, fontSize = 11.sp) },
                colors = NavigationBarItemDefaults.colors(
                    selectedIconColor = Orange,
                    selectedTextColor = Navy,
                    unselectedIconColor = Muted,
                    unselectedTextColor = Muted,
                    indicatorColor = Color.Transparent
                )
            )
        }
    }
}

private fun buildRegimeDays(
    monthIndex: Int,
    year: Int,
    mineDays: Int,
    restDays: Int,
    firstMineDate: String
): List<CalendarDay> {

    fun isLeapYear(y: Int) =
        y % 4 == 0 && (y % 100 != 0 || y % 400 == 0)

    fun daysInMonth(month: Int, y: Int): Int =
        when (month) {
            2 -> if (isLeapYear(y)) 29 else 28
            4, 6, 9, 11 -> 30
            else -> 31
        }

    // Número de día acumulado desde el año 1.
    fun dayNumber(day: Int, month: Int, y: Int): Int {
        var total = 0

        for (currentYear in 1 until y) {
            total += if (isLeapYear(currentYear)) 366 else 365
        }

        for (currentMonth in 1 until month) {
            total += daysInMonth(currentMonth, y)
        }

        return total + day
    }

    val parts = firstMineDate.split("/")
    val startDay = parts.getOrNull(0)?.toIntOrNull()
    val startMonth = parts.getOrNull(1)?.toIntOrNull()
    val startYear = parts.getOrNull(2)?.toIntOrNull()

    val validDate = startDay != null &&
            startMonth != null &&
            startYear != null &&
            startYear in 1..9999 &&
            startMonth in 1..12 &&
            startDay in 1..daysInMonth(startMonth, startYear)

    val validRegime = mineDays > 0 && restDays > 0
    val cycleLength = mineDays + restDays


    // Lunes = 0 ... domingo = 6.
    fun weekdayOffset(month: Int, y: Int): Int {
        return LocalDate(y, month, 1)
            .dayOfWeek
            .isoDayNumber - 1
    }

    val month = monthIndex + 1
    val totalDays = daysInMonth(month, year)
    val offset = weekdayOffset(month, year)
    val result = mutableListOf<CalendarDay>()

    val previousMonth = if (monthIndex == 0) 12 else monthIndex
    val previousYear = if (monthIndex == 0) year - 1 else year
    val previousCount = daysInMonth(previousMonth, previousYear)

    // Días del mes anterior.
    repeat(offset) { index ->
        result.add(
            CalendarDay(
                day = previousCount - offset + index + 1,
                type = DayType.OUTSIDE,
                inCurrentMonth = false
            )
        )
    }

    val startNumber = if (validDate) {
        dayNumber(startDay!!, startMonth!!, startYear!!)
    } else {
        0
    }

    for (day in 1..totalDays) {
        val type = if (!validDate || !validRegime) {
            DayType.OUTSIDE
        } else {
            val currentNumber = dayNumber(day, month, year)
            val elapsed = currentNumber - startNumber

            // El ciclo también se extiende antes de la fecha inicial.
            val position = ((elapsed % cycleLength) + cycleLength) % cycleLength

            if (position < mineDays) {
                DayType.MINE
            } else {
                DayType.REST
            }
        }

        result.add(CalendarDay(day = day, type = type))
    }

    // Completa la última semana.
    var nextDay = 1
    while (result.size % 7 != 0) {
        result.add(
            CalendarDay(
                day = nextDay++,
                type = DayType.OUTSIDE,
                inCurrentMonth = false
            )
        )
    }

    return result
}
