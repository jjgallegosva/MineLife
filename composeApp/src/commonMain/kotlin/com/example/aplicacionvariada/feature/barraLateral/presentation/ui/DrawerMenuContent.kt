package com.example.aplicacionvariada.feature.barraLateral.presentation.ui
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import aplicacionvariada.composeapp.generated.resources.Res
import aplicacionvariada.composeapp.generated.resources.apartment_naranja
import aplicacionvariada.composeapp.generated.resources.exit
import aplicacionvariada.composeapp.generated.resources.icon_mineria
import aplicacionvariada.composeapp.generated.resources.icon_peluqueria
import aplicacionvariada.composeapp.generated.resources.settings
import com.example.aplicacionvariada.feature.barraLateral.presentation.mappers.DrawerItem
import com.example.aplicacionvariada.feature.inicio.presentation.ui.components.DrawerHeader
import org.jetbrains.compose.resources.painterResource
@Composable
fun DrawerMenuContent(
    items: List<DrawerItem>,
    onItemClick: (DrawerItem) -> Unit
) {
    var expandedItem by remember { mutableStateOf<String?>(null) }
    var selectedItem by remember { mutableStateOf<String?>("Inicio") }
    ModalDrawerSheet(
        modifier = Modifier
            .fillMaxHeight()
            .width(280.dp)
            .clip(
                RoundedCornerShape(
                    topEnd = 28.dp,
                    bottomEnd = 28.dp
                )
            ),
        drawerContainerColor = Color(0xFFF7F3EE)
    ) {
        Box(
            modifier = Modifier.fillMaxSize()
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    //.padding(bottom = 150.dp)
            ) {
                Spacer(modifier = Modifier.height(24.dp))

                DrawerHeader()

                HorizontalDivider(
                    color = Color.Black.copy(alpha = 0.08f),
                    modifier = Modifier.padding(horizontal = 20.dp)
                )

                Spacer(modifier = Modifier.height(12.dp))

                items.forEach { item ->
                    val isSelected = selectedItem == item.title
                    Box(
                        modifier = Modifier
                            .padding(horizontal = 12.dp, vertical = 2.dp)
                            .clip(RoundedCornerShape(16.dp))
                            .background(
                                if (isSelected) Color.Black else Color.Transparent
                            )
                            .border(
                                width = 1.dp,
                                color = if (isSelected) Color.Black else Color(0xFF8C7A6B),
                                shape = RoundedCornerShape(16.dp)
                            )
                    ) {
                        NavigationDrawerItem(
                            icon = {
                                item.icon?.let {
                                    Image(
                                        painter = painterResource(it),
                                        contentDescription = item.title,
                                        modifier = Modifier.size(34.dp)
                                    )
                                }
                            },
                            label = {
                                Text(
                                    text = item.title,
                                    color = if (isSelected) Color.White else Color(0xFF2B211A),
                                    fontWeight = FontWeight.Medium
                                )
                            },
                            selected = isSelected,
                            onClick = {
                                selectedItem = item.title

                                if (item.children.isNotEmpty()) {
                                    expandedItem =
                                        if (expandedItem == item.title) null else item.title
                                } else {
                                    onItemClick(item)
                                }
                            },
                            modifier = Modifier.fillMaxWidth(),
                            colors = NavigationDrawerItemDefaults.colors(
                                selectedContainerColor = Color.Transparent,
                                unselectedContainerColor = Color.Transparent,
                                selectedTextColor = Color.White,
                                unselectedTextColor = Color(0xFF2B211A),
                                selectedIconColor = Color.White,
                                unselectedIconColor = Color(0xFF2B211A)
                            )
                        )
                    }
                    Spacer(modifier = Modifier.height(12.dp))

                    if (expandedItem == item.title) {
                        item.children.forEach { child ->
                            NavigationDrawerItem(
                                label = {
                                    Text(
                                        text = child.title,
                                        color = Color(0xFF4A3A2E)
                                    )
                                },
                                selected = false,
                                onClick = {
                                    onItemClick(child)
                                },
                                modifier = Modifier.padding(
                                    start = 52.dp,
                                    end = 12.dp,
                                    top = 0.dp,
                                    bottom = 0.dp
                                ),
                                colors = NavigationDrawerItemDefaults.colors(
                                    unselectedContainerColor = Color.Transparent
                                )
                            )
                        }
                    }
                }

                Spacer(modifier = Modifier.weight(1f))

                HorizontalDivider(
                    color = Color.Black.copy(alpha = 0.15f),
                    modifier = Modifier.padding(horizontal = 20.dp)
                )

                NavigationDrawerItem(
                    icon = {
                        Image(
                            painter = painterResource(Res.drawable.exit),
                            contentDescription = "Cerrar sesión",
                            modifier = Modifier.size(28.dp)
                        )
                    },
                    label = {
                        Text(
                            text = "Cerrar sesión",
                            color = Color.Black,
                            fontWeight = FontWeight.Bold
                        )
                    },
                    selected = false,
                    onClick = {
                        onItemClick(
                            DrawerItem(
                                title = "CerrarSesión",
                                icon = Res.drawable.exit
                            )
                        )
                    },
                    modifier = Modifier.padding(
                        horizontal = 12.dp,
                        vertical = 8.dp
                    ),
                    colors = NavigationDrawerItemDefaults.colors(
                        unselectedContainerColor = Color.Transparent
                    )
                )
            }

            /*Image(
                painter = painterResource(Res.drawable.background_montanas_mina),
                contentDescription = null,
                modifier = Modifier
                    .align(Alignment.BottomCenter)
                    .fillMaxWidth()
                    .height(155.dp),
                contentScale = ContentScale.Crop
            )*/
        }
    }
}
@Preview(
    showBackground = true,
    widthDp = 360,
    heightDp = 800
)
@Composable
fun DrawerMenuContentPreviewExpanded() {
    MaterialTheme {
        DrawerMenuContent(
            items = listOf(
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
            ),
            onItemClick = {}
        )
    }
}