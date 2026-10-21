package com.example.aplicacionvariada.feature.barraLateral.presentation.mappers

import org.jetbrains.compose.resources.DrawableResource

data class DrawerItem(
    val title: String,
    val icon: DrawableResource? = null,
    val children: List<DrawerItem> = emptyList()
)