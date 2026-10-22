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
fun QuickAccess(
    title: String,
    icon: androidx.compose.ui.graphics.vector.ImageVector,
    tint: Color,
    accent: Color,
    modifier: Modifier = Modifier,
    onClick: () -> Unit
) {
    Row(
        modifier = modifier
            .heightIn(min = 80.dp)
            .clip(RoundedCornerShape(30.dp))
            .background(Color.White)
            .border(1.dp, Color(0xFFE2E8F0), RoundedCornerShape(30.dp))
            .clickable { onClick() }
            .padding(16.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        IconBadge(icon, tint, accent)
        Text(
            title,
            color = Color(0xFF171D28),
            fontSize = 15.sp,
            lineHeight = 20.sp,
            fontWeight = FontWeight.Medium
        )
    }
}

@Preview(showBackground = true, backgroundColor = 0xFFF5F7F9)
@Composable
private fun QuickAccessPreview() {
    MaterialTheme {
        QuickAccess(
            title = "Registrar gasto",
            icon = Icons.Outlined.AccountBalanceWallet,
            tint = Color(0xFFFFEEE6),
            accent = MineOrange,
            modifier = Modifier.padding(16.dp),
            onClick = {}
        )
    }
}
