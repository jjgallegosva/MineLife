/*
package com.example.aplicacionvariada.core.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun CalendarBottomNavigation(
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

    NavigationBar(containerColor = androidx.compose.ui.graphics.Color.White, tonalElevation = 4.dp) {
        items.forEach { (label, icon, route) ->
            NavigationBarItem(
                selected = selected == route,
                onClick = { onSelected(route) },
                icon = {
                    Box(
                        modifier = Modifier.clip(RoundedCornerShape(15.dp))
                            .background(if (selected == route) androidx.compose.ui.graphics.Color(0xFFFFEEE6) else androidx.compose.ui.graphics.Color.Transparent)
                            .padding(horizontal = 13.dp, vertical = 8.dp)
                    ) {
                        Icon(icon, contentDescription = label,
                            tint = if (selected == route) Orange else Muted)
                    }
                },
                label = { Text(label, fontSize = 11.sp) },
                colors = NavigationBarItemDefaults.colors(
                    selectedIconColor = Orange,
                    selectedTextColor = Navy,
                    unselectedIconColor = Muted,
                    unselectedTextColor = Muted,
                    indicatorColor = androidx.compose.ui.graphics.Color.Transparent
                )
            )
        }
    }
}

@Preview(showBackground = true, name = "CalendarBottomNavigation")
@Composable
private fun CalendarBottomNavigationPreview() {
    CalendarBottomNavigation(selected = "Calendario", onSelected = {})
}
*/
