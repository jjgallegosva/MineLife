package com.minelife.core.ui.components

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
fun MineBottomNavigation(
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
                            .padding(
                                horizontal = 14.dp,
                                vertical = 8.dp
                            )
                    ) {
                        Icon(
                            icon,
                            contentDescription = label,
                            tint = if (selected == route) MineOrange
                            else MineMuted
                        )
                    }
                },
                label = {
                    Text(label, fontSize = 11.sp)
                },
                colors = NavigationBarItemDefaults.colors(
                    selectedIconColor = MineOrange,
                    selectedTextColor = MineNavy,
                    unselectedIconColor = MineMuted,
                    unselectedTextColor = MineMuted,
                    indicatorColor = Color.Transparent
                )
            )
        }
    }
}

@Preview(showBackground = true, backgroundColor = 0xFFF5F7F9)
@Composable
private fun MineBottomNavigationPreview() {
    MaterialTheme {
        MineBottomNavigation(selected = "Inicio", onSelected = {})
    }
}
