
package com.minelife.ui.home

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
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
import com.example.aplicacionvariada.core.ui.components.MineProfileMenu

import com.example.aplicacionvariada.feature.calendar.data.RegimeDataStore
import com.example.aplicacionvariada.feature.home.presentation.HomeViewModel

import com.minelife.core.ui.components.MineStatusCard
import com.minelife.core.ui.components.QuickAccess
import com.minelife.core.ui.components.SummaryCard
import com.minelife.core.ui.components.InfoCard
import com.minelife.core.ui.components.SavingsCard
import com.minelife.core.ui.components.WeekCard
import com.minelife.core.ui.components.MineBottomNavigation
import com.minelife.core.ui.theme.MineBackground
import com.minelife.core.ui.theme.MineNavy
import com.minelife.core.ui.theme.MineOrange
import com.minelife.core.ui.theme.MineMuted

@Composable
fun HomeScreen(
    regimeDataStore: RegimeDataStore,
    userName: String = "Juan",
    onNavigate: (String) -> Unit = {}
) {
    val homeViewModel = remember(regimeDataStore) {
        HomeViewModel(regimeDataStore)
    }

    val uiState by homeViewModel.uiState.collectAsState()

    var selectedTab by remember { mutableStateOf("Inicio") }
    var showProfileMenu by remember { mutableStateOf(false) }
    var isDarkMode by remember { mutableStateOf(false) }

    Scaffold(
        modifier = Modifier.statusBarsPadding(),
        containerColor = MineBackground,

        topBar = {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(MineBackground)
                    .border(
                        width = 0.5.dp,
                        color = Color(0xFFE2E8F0)
                    )
                    .padding(
                        horizontal = 20.dp,
                        vertical = 14.dp
                    ),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Box(
                    modifier = Modifier
                        .size(48.dp)
                        .clip(RoundedCornerShape(16.dp))
                        .background(MineOrange),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        imageVector = Icons.Outlined.Construction,
                        contentDescription = "MineLife",
                        tint = Color.White,
                        modifier = Modifier.size(27.dp)
                    )
                }

                Spacer(Modifier.width(14.dp))

                Text(
                    text = "MineLife",
                    fontSize = 21.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color(0xFF171D28)
                )

                Spacer(Modifier.weight(1f))

                Box {
                    Box(
                        modifier = Modifier
                            .size(48.dp)
                            .clip(CircleShape)
                            .background(MineNavy)
                            .clickable {
                                showProfileMenu = !showProfileMenu
                            },
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = userName
                                .split(" ")
                                .filter { it.isNotBlank() }
                                .take(2)
                                .mapNotNull { it.firstOrNull()?.uppercaseChar() }
                                .joinToString(""),
                            color = Color.White,
                            fontWeight = FontWeight.SemiBold
                        )
                    }

                    MineProfileMenu(
                        expanded = showProfileMenu,
                        userName = userName,
                        isDarkMode = isDarkMode,
                        onDismiss = {
                            showProfileMenu = false
                        },
                        onProfileClick = {
                            showProfileMenu = false
                            onNavigate("Mi perfil y ajustes")
                        },
                        onDarkModeChange = { enabled ->
                            isDarkMode = enabled
                        },
                        onLogoutClick = {
                            showProfileMenu = false
                            onNavigate("Cerrar sesión")
                        }
                    )

                }
            }
        },

        bottomBar = {
            MineBottomNavigation(
                selected = selectedTab,
                onSelected = { destination ->
                    selectedTab = destination
                    onNavigate(destination)
                }
            )
        }
    ) { paddingValues ->

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .verticalScroll(rememberScrollState())
                .padding(horizontal = 20.dp)
                .padding(top = 26.dp, bottom = 24.dp),
            verticalArrangement = Arrangement.spacedBy(20.dp)
        ) {

            // Saludo
            Column(
                verticalArrangement = Arrangement.spacedBy(6.dp)
            ) {
                Text(
                    text = "TU VIDA MINERA, ORGANIZADA",
                    color = MineOrange,
                    fontSize = 12.sp,
                    fontWeight = FontWeight.Bold,
                    letterSpacing = 1.5.sp
                )

                Text(
                    text = "Hola, $userName",
                    fontSize = 32.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color(0xFF171D28)
                )
            }

            // Estado del régimen minero
            MineStatusCard(
                status = uiState.regimeStatus,
                isLoading = uiState.isLoading,
                onCalendar = {
                    onNavigate("Calendario")
                },
                onNextShift = {
                    onNavigate("Registrar turno")
                }
            )

            // Accesos rápidos
            Text(
                text = "ACCESOS RÁPIDOS",
                fontSize = 13.sp,
                fontWeight = FontWeight.Bold,
                color = MineMuted,
                letterSpacing = 1.5.sp
            )

            Column(
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    QuickAccess(
                        title = "Registrar\ngasto",
                        icon = Icons.Outlined.AccountBalanceWallet,
                        tint = Color(0xFFFFEEE6),
                        accent = MineOrange,
                        modifier = Modifier.weight(1f)
                    ) {
                        onNavigate("Gastos")
                    }

                    QuickAccess(
                        title = "Preparar\nmochila",
                        icon = Icons.Outlined.Backpack,
                        tint = Color(0xFFE7F2ED),
                        accent = Color(0xFF168653),
                        modifier = Modifier.weight(1f)
                    ) {
                        onNavigate("Mochila")
                    }
                }

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    QuickAccess(
                        title = "Ver\ncalendario",
                        icon = Icons.Outlined.CalendarMonth,
                        tint = Color(0xFFE7EEFF),
                        accent = Color(0xFF2675FF),
                        modifier = Modifier.weight(1f)
                    ) {
                        onNavigate("Calendario")
                    }

                    QuickAccess(
                        title = "Registrar\nturno",
                        icon = Icons.Outlined.EditNote,
                        tint = Color(0xFFF0E8FC),
                        accent = Color(0xFF8B45D7),
                        modifier = Modifier.weight(1f)
                    ) {
                        onNavigate("Registrar turno")
                    }
                }
            }

            // Resumen de actividad

            Text(
                text = "TU RESUMEN",
                fontSize = 13.sp,
                fontWeight = FontWeight.Bold,
                color = MineMuted,
                letterSpacing = 1.5.sp
            )

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                SummaryCard(
                    title = "GASTOS DEL MES",
                    value = "S/ 12",
                    subtitle = "Ver detalle",
                    icon = Icons.Outlined.AccountBalanceWallet,
                    tint = Color(0xFFFFEEE6),
                    accent = MineOrange,
                    modifier = Modifier.weight(1f)
                ) {
                    onNavigate("Gastos")
                }

                SummaryCard(
                    title = "MOCHILA",
                    value = "0% lista",
                    subtitle = "turno2",
                    icon = Icons.Outlined.Backpack,
                    tint = Color(0xFFE7F2ED),
                    accent = Color(0xFF168653),
                    modifier = Modifier.weight(1f)
                ) {
                    onNavigate("Mochila")
                }

                SummaryCard(
                    title = "BARBERÍA",
                    value = "Sin citas",
                    subtitle = "Reservar una cita",
                    icon = Icons.Outlined.ContentCut,
                    tint = Color(0xFFF0E8FC),
                    accent = Color(0xFF8B45D7),
                    modifier = Modifier.weight(1f)
                ) {
                    onNavigate("Más")
                }
            }


            // Checklist
            InfoCard(
                icon = Icons.Outlined.Luggage,
                heading = "PRÓXIMO CHECKLIST",
                content = "Prepara tu mochila minera",
                detail = "Revisa los artículos antes de tu próximo turno.",
                action = "Ver checklist"
            ) {
                onNavigate("Mochila")
            }

            // Documentos
            InfoCard(
                icon = Icons.Outlined.VerifiedUser,
                heading = "DOCUMENTOS",
                content = "Mantén tus documentos al día",
                detail = "Consulta tus documentos y vencimientos.",
                action = "Ver documentos"
            ) {
                onNavigate("Documentos")
            }

            // Pendientes
            InfoCard(
                icon = Icons.Outlined.Checklist,
                heading = "PENDIENTES",
                content = "Organiza tus tareas personales",
                action = "Ver pendientes"
            ) {
                onNavigate("Pendientes")
            }

            // Ahorro
            SavingsCard(
                onDefine = {
                    onNavigate("Meta de ahorro")
                }
            )

            // Semana
            WeekCard(
                onCalendar = {
                    onNavigate("Calendario")
                }
            )
        }
    }
}
