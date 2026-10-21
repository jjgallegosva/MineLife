package com.example.nexogestion.ui.auth

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.example.nexogestion.ui.theme.NexoColors

val AuthCardShape = RoundedCornerShape(28.dp)
val FieldShape = RoundedCornerShape(14.dp)
val ButtonShape = RoundedCornerShape(18.dp)

@Composable
fun AuthBackground(content: @Composable BoxScope.() -> Unit) {
    Box(modifier = Modifier.fillMaxSize().background(NexoColors.Background)) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(190.dp)
                .align(Alignment.BottomCenter)
                .background(Brush.verticalGradient(listOf(Color.Transparent, NexoColors.InfoSoft.copy(alpha = 0.9f))))
        )
        content()
    }
}

@Composable
fun BrandHeader(
    modifier: Modifier = Modifier,
    title: String = "Nexo Gestión",
    subtitle: String = "Gestión inteligente para\nservicios variados"
) {
    Column(modifier = modifier, horizontalAlignment = Alignment.CenterHorizontally) {
        PremiumLogo()
        Spacer(Modifier.height(16.dp))
        Text(text = title, style = MaterialTheme.typography.headlineLarge, fontWeight = FontWeight.Normal)
        Spacer(Modifier.height(8.dp))
        Text(text = subtitle, style = MaterialTheme.typography.bodyLarge, color = NexoColors.TextSecondary, textAlign = TextAlign.Center)
    }
}

@Composable
fun PremiumLogo() {
    Box(
        modifier = Modifier.size(58.dp).border(1.5.dp, NexoColors.AccentPremium, RoundedCornerShape(14.dp)),
        contentAlignment = Alignment.Center
    ) {
        Box(modifier = Modifier.size(32.dp).border(2.dp, NexoColors.AccentPremium, RoundedCornerShape(8.dp)))
    }
}

@Composable
fun AuthCard(content: @Composable ColumnScope.() -> Unit) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .shadow(14.dp, AuthCardShape, ambientColor = Color.Black.copy(alpha = 0.06f), spotColor = Color.Black.copy(alpha = 0.08f))
            .background(NexoColors.Surface, AuthCardShape)
            .padding(24.dp),
        content = content
    )
}

@Composable
fun AuthTextField(
    value: String,
    onValueChange: (String) -> Unit,
    label: String,
    placeholder: String,
    leadingIcon: @Composable () -> Unit,
    isPassword: Boolean = false,
    modifier: Modifier = Modifier
) {
    var visible by remember { mutableStateOf(false) }
    Column(modifier = modifier.fillMaxWidth()) {
        if (label.isNotBlank()) {
            Text(text = label, style = MaterialTheme.typography.labelLarge, color = NexoColors.TextPrimary)
            Spacer(Modifier.height(10.dp))
        }
        OutlinedTextField(
            value = value,
            onValueChange = onValueChange,
            singleLine = true,
            placeholder = { Text(text = placeholder, style = MaterialTheme.typography.bodyLarge, color = NexoColors.TextSecondary) },
            leadingIcon = leadingIcon,
            trailingIcon = {
                if (isPassword) {
                    IconButton(onClick = { visible = !visible }) {
                       // Icon(if (visible) Icons.Outlined.VisibilityOff else Icons.Outlined.Visibility, null, tint = NexoColors.TextSecondary)
                    }
                }
            },
            visualTransformation = if (isPassword && !visible) PasswordVisualTransformation() else VisualTransformation.None,
            shape = FieldShape,
            colors = OutlinedTextFieldDefaults.colors(
                focusedBorderColor = NexoColors.AccentPremium,
                unfocusedBorderColor = NexoColors.Border,
                focusedContainerColor = NexoColors.Surface,
                unfocusedContainerColor = NexoColors.Surface,
                cursorColor = NexoColors.Primary,
                focusedTextColor = NexoColors.TextPrimary,
                unfocusedTextColor = NexoColors.TextPrimary
            ),
            modifier = Modifier.fillMaxWidth().height(64.dp)
        )
    }
}

@Composable
fun PrimaryAuthButton(text: String, onClick: () -> Unit, modifier: Modifier = Modifier) {
    Button(
        onClick = onClick,
        shape = ButtonShape,
        colors = ButtonDefaults.buttonColors(containerColor = NexoColors.PrimaryDark, contentColor = Color.White),
        modifier = modifier.fillMaxWidth().height(64.dp)
    ) {
        Text(text = text, style = MaterialTheme.typography.labelLarge, color = Color.White)
    }
}

@Composable
fun SocialButton(text: String, iconText: String, onClick: () -> Unit, modifier: Modifier = Modifier) {
    OutlinedButton(
        onClick = onClick,
        shape = RoundedCornerShape(18.dp),
        border = BorderStroke(1.dp, NexoColors.Border),
        colors = ButtonDefaults.outlinedButtonColors(containerColor = NexoColors.Surface, contentColor = NexoColors.TextPrimary),
        modifier = modifier.height(58.dp)
    ) {
        Text(iconText, style = MaterialTheme.typography.titleMedium)
        Spacer(Modifier.width(12.dp))
        Text(text, style = MaterialTheme.typography.labelLarge)
    }
}

@Composable
fun BackButton(onClick: () -> Unit) {
    IconButton(onClick = onClick) {
        //Icon(Icons.Outlined.ArrowBack, contentDescription = "Volver", tint = NexoColors.Primary)
    }
}

@Composable
fun SecurityInfoCard() {
    Row(
        modifier = Modifier.fillMaxWidth().background(NexoColors.InfoSoft, RoundedCornerShape(22.dp)).border(1.dp, NexoColors.Border, RoundedCornerShape(22.dp)).padding(20.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        //Icon(Icons.Outlined.Shield, contentDescription = null, tint = NexoColors.AccentPremium, modifier = Modifier.size(42.dp))
        Spacer(Modifier.width(16.dp))
        Column {
            Text("Tu seguridad es nuestra prioridad", style = MaterialTheme.typography.labelLarge, color = NexoColors.TextPrimary)
            Spacer(Modifier.height(4.dp))
            Text("Usamos procesos seguros para proteger tu información.", style = MaterialTheme.typography.bodyMedium, color = NexoColors.TextSecondary)
        }
    }
}
