package com.example.aplicacionvariada.core.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
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
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.foundation.layout.statusBarsPadding
import com.example.aplicacionvariada.feature.calendar.data.RegimeDataStore
import com.example.aplicacionvariada.feature.calendar.domain.RegimeSettings
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.tooling.preview.Preview
import com.example.aplicacionvariada.feature.home.domain.MineRegimeStatus
import com.example.aplicacionvariada.feature.home.presentation.HomeViewModel
import kotlinx.datetime.todayIn
import kotlin.time.Clock
import kotlinx.datetime.DateTimeUnit
import kotlinx.datetime.LocalDate
import kotlinx.datetime.TimeZone
import kotlinx.datetime.plus
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.outlined.DarkMode
import androidx.compose.material.icons.outlined.Person
import androidx.compose.material.icons.outlined.Logout
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Switch
import com.minelife.core.ui.theme.MineNavy
import com.minelife.core.ui.theme.MineOrange
import com.minelife.core.ui.theme.MineMuted

@Composable
fun MineStatusCard(
    status: MineRegimeStatus?,
    isLoading: Boolean,
    onCalendar: () -> Unit,
    onNextShift: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(32.dp))
            .background(
                Brush.linearGradient(
                    listOf(
                        Color(0xFF10233B),
                        Color(0xFF193652),
                        Color(0xFF37313B)
                    )
                )
            )
            .padding(32.dp)
    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Surface(
                color = Color.White.copy(alpha = 0.12f),
                shape = CircleShape
            ) {
                Text(
                    when {
                        isLoading -> "CARGANDO..."
                        status == null -> "SIN RÉGIMEN"
                        status.isBeforeStart -> "RÉGIMEN PENDIENTE"
                        status.isInMine -> "🟠 EN MINA"
                        else -> "🟢 EN DESCANSO"
                    },
                    modifier = Modifier.padding(
                        horizontal = 14.dp,
                        vertical = 8.dp
                    ),
                    color = Color.White,
                    fontSize = 12.sp,
                    fontWeight = FontWeight.Bold,
                    letterSpacing = 1.sp
                )
            }
            Spacer(Modifier.weight(1f))
            Icon(
                Icons.Outlined.Construction,
                contentDescription = null,
                tint = Color.White,
                modifier = Modifier.size(30.dp)
            )
        }

        Spacer(Modifier.height(18.dp))

        Text(
            text = when {
                isLoading -> "Cargando régimen..."
                status == null -> "Configura tu régimen"
                status.isBeforeStart -> "Tu turno aún no comienza"
                status.isInMine -> "Estás en mina"
                else -> "Estás en descanso"
            },
            color = Color.White,
            fontSize = 30.sp,
            fontWeight = FontWeight.Bold
        )

        Spacer(Modifier.height(6.dp))

        Text(
            text = when {
                isLoading -> "Calculando tu próximo cambio..."
                status == null -> "Guarda la configuración de tu régimen."
                status.isBeforeStart ->
                    "Tu régimen comienza en ${status.daysRemaining} días."
                status.isInMine && status.daysRemaining == 0 ->
                    "Hoy es tu último día en mina."
                status.isInMine ->
                    "Faltan ${status.daysRemaining} días para tu salida de mina."
                status.daysRemaining == 0 ->
                    "Hoy termina tu descanso."
                else ->
                    "Faltan ${status.daysRemaining} días para tu próximo ingreso a mina."
            },
            color = Color(0xFFC5CED8),
            fontSize = 18.sp,
            lineHeight = 27.sp
        )

        Spacer(Modifier.height(28.dp))

        Row(verticalAlignment = Alignment.CenterVertically) {
            Column(modifier = Modifier.weight(1f)) {
                Text(
                    "CICLO",
                    color = Color(0xFF9AA9B9),
                    fontSize = 12.sp,
                    letterSpacing = 2.sp
                )
                Text(
                    if (status != null && !status.isBeforeStart) {
                        "Día ${status.currentDay} de ${status.cycleLength} · Régimen ${status.mineDays} × ${status.restDays}"
                    } else {
                        "Sin ciclo activo"
                    },
                    color = Color.White,
                    fontSize = 15.sp,
                    fontWeight = FontWeight.Medium
                )
            }

            Column(horizontalAlignment = Alignment.End) {
                Text(
                    "SALIDA",
                    color = Color(0xFF9AA9B9),
                    fontSize = 12.sp,
                    letterSpacing = 2.sp
                )
                Text(
                    if (status != null && !status.isBeforeStart) {
                        "${status.relevantDate.dayOfMonth.toString().padStart(2, '0')}/${
                            status.relevantDate.monthNumber.toString().padStart(2, '0')
                        }"
                    } else {
                        "--"
                    },
                    color = Color.White,
                    fontSize = 18.sp,
                    fontWeight = FontWeight.SemiBold
                )
            }
        }

        Spacer(Modifier.height(18.dp))

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(6.dp)
                .clip(CircleShape)
                .background(Color.White.copy(alpha = 0.18f))
        ) {
            Box(
                modifier = Modifier
                    .fillMaxWidth(
                        status?.progress?.coerceIn(0f, 1f) ?: 0f
                    )
                    .fillMaxHeight()
                    .clip(CircleShape)
                    .background(MineOrange)
            )
        }

        Spacer(Modifier.height(20.dp))

        Row(verticalAlignment = Alignment.CenterVertically) {
            Text(
                "▧  Ver calendario",
                color = Color.White,
                fontSize = 14.sp,
                modifier = Modifier.clickable { onCalendar() }
            )
            Spacer(Modifier.weight(1f))
            Text(
                "Próximo cambio  →",
                color = MineOrange,
                fontSize = 14.sp,
                fontWeight = FontWeight.SemiBold,
                modifier = Modifier.clickable { onNextShift() }
            )
        }
    }
}

@Preview(showBackground = true, backgroundColor = 0xFFF5F7F9)
@Composable
private fun MineStatusCardPreview() {
    MaterialTheme {
        Box(Modifier.padding(16.dp)) {
            MineStatusCard(status = null, isLoading = true, onCalendar = {}, onNextShift = {})
        }
    }
}
