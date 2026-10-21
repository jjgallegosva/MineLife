package com.example.aplicacionvariada.feature.inicio.presentation.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import aplicacionvariada.composeapp.generated.resources.Res
import aplicacionvariada.composeapp.generated.resources.icon_cita
import aplicacionvariada.composeapp.generated.resources.icon_mineria
import aplicacionvariada.composeapp.generated.resources.icon_operacion
import aplicacionvariada.composeapp.generated.resources.icon_peluqueria
import aplicacionvariada.composeapp.generated.resources.icon_reportes
import aplicacionvariada.composeapp.generated.resources.menu
import com.example.aplicacionvariada.feature.barraLateral.presentation.ui.DrawerMenuContent
import com.example.aplicacionvariada.feature.inicio.presentation.ui.components.FeatureCard
import com.example.aplicacionvariada.feature.inicio.presentation.ui.components.HeroSection
import com.example.aplicacionvariada.feature.inicio.presentation.ui.components.QuickActionCard
import com.example.aplicacionvariada.feature.barraLateral.presentation.mappers.drawerItems
import kotlinx.coroutines.launch
import org.jetbrains.compose.resources.painterResource


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainPageScreen( onLogout: () -> Unit, onMineriaClick: () -> Unit) {
    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
    val scope = rememberCoroutineScope()

    ModalNavigationDrawer(
        drawerState = drawerState,
        drawerContent = {
            DrawerMenuContent(
                items = drawerItems,
                onItemClick = { item ->
                    scope.launch { drawerState.close() }

                    when (item.title) {
                        "Inicio" -> {
                            // navegar a inicio
                        }

                        "Minería" -> {
                            onMineriaClick()
                        }

                        "Servicios" -> {
                            // navegar a servicios
                        }

                        "Citas / Reservas" -> {
                            // navegar a citas
                        }

                        "Clientes / Operaciones" -> {
                            // navegar a clientes
                        }

                        "Configuración" -> {
                            // navegar a configuración
                        }

                        "CerrarSesión" -> {
                            onLogout()
                        }
                    }
                }
            )
        }
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(
                    Color(0xFFF7F3EE)

                )
        ) {
            Scaffold(
                containerColor = Color.Transparent,
                topBar = {
                    TopAppBar(
                        title = {
                            Column {
                                Text(
                                    text = "Panel principal",
                                    fontSize = 24.sp,
                                    fontWeight = FontWeight.SemiBold,
                                    color = Color(0xFF191C22),
                                    lineHeight = 28.8.sp // 24sp * 120% * 120%
                                )
                                Text(
                                    text = "Belleza + Minería",
                                    fontSize = 14.sp,
                                    fontWeight = FontWeight.Normal,
                                    color = Color(0xFF6E7480),
                                    lineHeight = 19.6.sp // 14sp * 140%
                                )
                            }
                        },
                        navigationIcon = {
                            IconButton(
                                onClick = {
                                    scope.launch { drawerState.open() }
                                }
                            ) {
                                Icon(
                                    painter = painterResource(Res.drawable.menu),
                                    contentDescription = "Abrir menú",
                                    modifier = Modifier.size(20.dp)
                                )
                            }
                        },
                        colors = TopAppBarDefaults.topAppBarColors(
                            containerColor = Color.Transparent
                        )
                    )
                }
            ) { innerPadding ->

                LazyColumn(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(innerPadding)
                        .padding(horizontal = 16.dp),
                    verticalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    item {
                        HeroSection()
                    }

                    item {
                        Text(
                            text = "Áreas principales",
                            fontSize = 20.sp,
                            fontWeight = FontWeight.SemiBold,
                            color = Color(0xFF191C22),
                            lineHeight = 24.sp // 20sp * 120%
                        )
                    }

                    item {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.spacedBy(12.dp)
                        ) {
                            FeatureCard(
                                modifier = Modifier.weight(1f),
                                title = "Peluquería",
                                subtitle = "Citas, clientes y servicios de belleza",
                                icon = painterResource(Res.drawable.icon_peluqueria),
                                iconBg = Color(0xFFFFE0E6)
                            )

                            FeatureCard(
                                modifier = Modifier.weight(1f),
                                title = "Minería",
                                subtitle = "Operaciones, control y seguimiento",
                                icon = painterResource(Res.drawable.icon_mineria),
                                iconBg = Color(0xFFE6D3B3),
                                onClick = onMineriaClick
                            )
                        }
                    }

                    item {
                        Text(
                            text = "Accesos rápidos",
                            fontSize = 20.sp,
                            fontWeight = FontWeight.SemiBold,
                            color = Color(0xFF191C22),
                            lineHeight = 24.sp // 20sp * 120%
                        )
                    }

                    item {
                        QuickActionCard(
                            title = "Registrar nueva cita",
                            description = "Agenda servicios de peluquería de forma rápida",
                            icon = painterResource(Res.drawable.icon_cita),
                        )
                    }

                    item {
                        QuickActionCard(
                            title = "Registrar operación",
                            description = "Controla actividades relacionadas con minería",
                            icon = painterResource(Res.drawable.icon_operacion),
                        )
                    }

                    item {
                        QuickActionCard(
                            title = "Ver reportes",
                            description = "Consulta indicadores generales del negocio",
                            icon = painterResource(Res.drawable.icon_reportes),
                        )
                    }

                    item {
                        Spacer(modifier = Modifier.height(12.dp))
                    }
                }
            }
        }
    }
}

@Preview
@Composable
fun MenuHamburguesaPreview() {
    MaterialTheme {
        MainPageScreen(
            onLogout = {},
            onMineriaClick = {}
        )

    }
}