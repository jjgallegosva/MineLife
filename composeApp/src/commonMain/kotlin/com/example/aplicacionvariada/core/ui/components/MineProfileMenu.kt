package com.example.aplicacionvariada.core.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.DarkMode
import androidx.compose.material.icons.outlined.Logout
import androidx.compose.material.icons.outlined.Person
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.minelife.core.ui.theme.MineMuted
import androidx.compose.ui.Alignment
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.draw.clip

@Composable
fun MineProfileMenu(
    expanded: Boolean,
    userName: String,
    isDarkMode: Boolean,
    onDismiss: () -> Unit,
    onProfileClick: () -> Unit,
    onDarkModeChange: (Boolean) -> Unit,
    onLogoutClick: () -> Unit
) {
    DropdownMenu(
        expanded = expanded,
        onDismissRequest = onDismiss,
        shape = RoundedCornerShape(16.dp),
        containerColor = Color.White,
        tonalElevation = 0.dp,
        shadowElevation = 4.dp
    ) {
        Column(
            modifier = Modifier.padding(
                horizontal = 16.dp,
                vertical = 12.dp
            )
        ) {
            Text(
                text = userName,
                fontSize = 16.sp,
                fontWeight = FontWeight.SemiBold,
                color = Color(0xFF171D28)
            )

            Text(
                text = "Mi cuenta MineLife",
                fontSize = 14.sp,
                color = MineMuted
            )
        }

        HorizontalDivider(color = Color(0xFFE2E8F0))

        DropdownMenuItem(
            text = { Text("Mi perfil y ajustes") },
            leadingIcon = {
                Icon(
                    imageVector = Icons.Outlined.Person,
                    contentDescription = null
                )
            },
            onClick = onProfileClick
        )

        DropdownMenuItem(
            text = { Text("Modo oscuro") },
            leadingIcon = {
                Icon(
                    imageVector = Icons.Outlined.DarkMode,
                    contentDescription = null
                )
            },
            trailingIcon = {
                Switch(
                    checked = isDarkMode,
                    onCheckedChange = onDarkModeChange
                )
            },
            onClick = {
                onDarkModeChange(!isDarkMode)
            }
        )

        HorizontalDivider(color = Color(0xFFE2E8F0))

        DropdownMenuItem(
            text = {
                Text(
                    text = "Cerrar sesión",
                    color = Color.Red
                )
            },
            leadingIcon = {
                Icon(
                    imageVector = Icons.Outlined.Logout,
                    contentDescription = null,
                    tint = Color.Red
                )
            },
            onClick = onLogoutClick
        )
    }
}
@Preview(showBackground = true, backgroundColor = 0xFFF5F7F9)
@Composable
private fun MineProfileMenuPreview() {
    var isDarkMode by remember { mutableStateOf(false) }

    MaterialTheme {
        Box(
            modifier = Modifier    
                .fillMaxWidth()
                .fillMaxHeight()
                .padding(24.dp),
            contentAlignment = Alignment.TopEnd
        ) {
            Box {
                // Elemento de referencia del menú
                Box(
                    modifier = Modifier
                        .size(48.dp)
                        .background(
                            color = Color(0xFF172554),
                            shape = CircleShape
                        ),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = "JJ",
                        color = Color.White,
                        fontWeight = FontWeight.Bold
                    )
                }

                // Menú abierto para visualizarlo en el Preview
                MineProfileMenu(
                    expanded = true,
                    userName = "Juan José",
                    isDarkMode = isDarkMode,
                    onDismiss = {},
                    onProfileClick = {},
                    onDarkModeChange = { isDarkMode = it },
                    onLogoutClick = {}
                )
            }
        }
    }
}