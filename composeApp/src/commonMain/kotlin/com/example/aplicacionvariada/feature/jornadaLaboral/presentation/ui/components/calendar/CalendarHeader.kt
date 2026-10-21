package pe.compartamos.designsystem.components.molecules.calendar


import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import org.jetbrains.compose.resources.painterResource
import aplicacionvariada.composeapp.generated.resources.Res
import aplicacionvariada.composeapp.generated.resources.next
import aplicacionvariada.composeapp.generated.resources.previous

@Composable
internal fun CalendarHeader(
    month: String,
    year: String,
    onPrev: () -> Unit,
    onNext: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color.White)
            .padding( vertical = 8.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        IconButton(onClick = onPrev, modifier = Modifier.size(22.dp)) {
            Icon(
                painter = painterResource(Res.drawable.previous),
                contentDescription = "Previous month",
                tint = Color.Unspecified
            )
        }
        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = month,
                color = Color.Black,
                fontWeight = FontWeight.Bold
            )
            Spacer(modifier = Modifier.width(16.dp))
            Text(
                text = year,
                color = Color.Black,
            )
        }
        IconButton(onClick = onNext, modifier = Modifier.size(22.dp)) {
            Icon(
                painter = painterResource(Res.drawable.next),
                contentDescription = "Next month",
                tint = Color.Unspecified
            )
        }
    }
}
@Preview(showBackground = true, showSystemUi = false)
@Composable
private fun CalendarHeaderPreview() {
    CalendarHeader(
        month = "Septiembre",
        year = "2026",
        onPrev = {},
        onNext = {}
    )
}