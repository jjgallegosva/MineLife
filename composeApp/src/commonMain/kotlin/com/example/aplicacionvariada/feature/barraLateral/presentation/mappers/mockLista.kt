package com.example.aplicacionvariada.feature.barraLateral.presentation.mappers

import aplicacionvariada.composeapp.generated.resources.Res
import aplicacionvariada.composeapp.generated.resources.apartment_naranja
import aplicacionvariada.composeapp.generated.resources.icon_mineria
import aplicacionvariada.composeapp.generated.resources.icon_peluqueria
import aplicacionvariada.composeapp.generated.resources.settings

val drawerItems = listOf(
    DrawerItem(
        title = "Inicio",
        icon = Res.drawable.apartment_naranja
    ),
    DrawerItem(
        title = "Minería",
        icon = Res.drawable.icon_mineria,
        children = listOf(
            DrawerItem(title = "Jornada Minera"),
            DrawerItem(title = "Mochila Minera"),
            DrawerItem(title = "Gastos Mineros")
        )
    ),
    DrawerItem(
        title = "Belleza",
        icon = Res.drawable.icon_peluqueria,
        children = listOf(
            DrawerItem(title = "Servicios"),
            DrawerItem(title = "Citas / Reservas"),
            DrawerItem(title = "Clientes / Operaciones")
        )
    ),
    DrawerItem(
        title = "Configuración",
        icon = Res.drawable.settings
    )
)