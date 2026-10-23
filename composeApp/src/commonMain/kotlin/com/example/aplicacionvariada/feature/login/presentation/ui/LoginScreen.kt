
package com.minelife.ui.login

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Email
import androidx.compose.material.icons.outlined.Lock
import androidx.compose.material.icons.outlined.Login
import androidx.compose.material.icons.outlined.Visibility
import androidx.compose.material.icons.outlined.VisibilityOff
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.tooling.preview.Preview

private val Navy = Color(0xFF10233F)
private val ScreenBackground = Color(0xFFF4F6F8)
private val BorderColor = Color(0xFFD9E0E8)
private val SecondaryText = Color(0xFF64748B)
private val PrimaryText = Color(0xFF171D29)

@Composable
fun LoginScreen(
    onLogin: (String, String) -> Unit = { _, _ -> },
    onGoogleLogin: () -> Unit = {},
    onFacebookLogin: () -> Unit = {},
    onForgotPassword: () -> Unit = {},
    onRegister: () -> Unit = {},
    isLoading: Boolean = false,
    modifier: Modifier = Modifier
){
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var passwordVisible by remember { mutableStateOf(false) }
    var errorMessage by remember { mutableStateOf<String?>(null) }

    Column(
        modifier = modifier
            .fillMaxSize()
            .background(ScreenBackground)
            .verticalScroll(rememberScrollState())
            .padding(horizontal = 24.dp, vertical = 16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(Modifier.height(24.dp))
        // Icono superior
        Box(
            modifier = Modifier
                .size(84.dp)
                .clip(RoundedCornerShape(24.dp))
                .background(Navy),
            contentAlignment = Alignment.Center
        ) {
            Icon(
                imageVector = Icons.Outlined.Login,
                contentDescription = "Iniciar sesión",
                tint = Color.White,
                modifier = Modifier.size(38.dp)
            )
        }

        Spacer(Modifier.height(24.dp))

        Text(
            text = "Bienvenido de nuevo",
            fontSize = 32.sp,
            lineHeight = 38.sp,
            fontWeight = FontWeight.Bold,
            color = PrimaryText,
            textAlign = TextAlign.Center
        )

        Spacer(Modifier.height(16.dp))

        Text(
            text = "Ingresa a tu cuenta de MineLife",
            fontSize = 19.sp,
            color = SecondaryText,
            textAlign = TextAlign.Center
        )

        Spacer(Modifier.height(60.dp))

        // Tarjeta de autenticación
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .clip(RoundedCornerShape(24.dp))
                .background(Color.White)
                .border(1.dp, BorderColor, RoundedCornerShape(24.dp))
                .padding(horizontal = 24.dp, vertical = 32.dp),
            horizontalAlignment = Alignment.Start
        ) {
            SocialLoginButton(
                text = "Continuar con Google",
                icon = "G",
                iconColor = Color(0xFF4285F4),
                onClick = onGoogleLogin
            )

            Spacer(Modifier.height(12.dp))

            SocialLoginButton(
                text = "Continuar con Facebook",
                icon = "f",
                iconColor = Color(0xFF1877F2),
                onClick = onFacebookLogin
            )

            Spacer(Modifier.height(32.dp))

            // Separador
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                HorizontalDivider(
                    modifier = Modifier.weight(1f),
                    color = BorderColor
                )

                Text(
                    text = "O",
                    modifier = Modifier.padding(horizontal = 16.dp),
                    color = SecondaryText,
                    fontSize = 15.sp
                )

                HorizontalDivider(
                    modifier = Modifier.weight(1f),
                    color = BorderColor
                )
            }

            Spacer(Modifier.height(36.dp))

            Text(
                text = "Correo electrónico",
                fontSize = 17.sp,
                fontWeight = FontWeight.Medium,
                color = PrimaryText
            )

            Spacer(Modifier.height(12.dp))

            OutlinedTextField(
                value = email,
                onValueChange = {
                    email = it
                    errorMessage = null
                },
                modifier = Modifier.fillMaxWidth(),
                placeholder = {
                    Text(
                        "tu@correo.com",
                        color = SecondaryText
                    )
                },
                leadingIcon = {
                    Icon(
                        Icons.Outlined.Email,
                        contentDescription = null,
                        tint = SecondaryText
                    )
                },
                singleLine = true,
                shape = RoundedCornerShape(20.dp),
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Email
                ),
                colors = loginFieldColors()
            )

            Spacer(Modifier.height(22.dp))

            // Contraseña y recuperación
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "Contraseña",
                    fontSize = 17.sp,
                    fontWeight = FontWeight.Medium,
                    color = PrimaryText
                )

                Spacer(Modifier.weight(1f))

                Text(
                    text = "¿Olvidaste tu contraseña?",
                    fontSize = 13.sp,
                    color = Navy,
                    modifier = Modifier.clickable(
                        onClick = onForgotPassword
                    )
                )
            }

            Spacer(Modifier.height(12.dp))

            OutlinedTextField(
                value = password,
                onValueChange = {
                    password = it
                    errorMessage = null
                },
                modifier = Modifier.fillMaxWidth(),
                placeholder = {
                    Text("••••••••", color = SecondaryText)
                },
                leadingIcon = {
                    Icon(
                        Icons.Outlined.Lock,
                        contentDescription = null,
                        tint = SecondaryText
                    )
                },
                trailingIcon = {
                    IconButton(
                        onClick = {
                            passwordVisible = !passwordVisible
                        }
                    ) {
                        Icon(
                            imageVector = if (passwordVisible) {
                                Icons.Outlined.VisibilityOff
                            } else {
                                Icons.Outlined.Visibility
                            },
                            contentDescription = if (passwordVisible) {
                                "Ocultar contraseña"
                            } else {
                                "Mostrar contraseña"
                            },
                            tint = SecondaryText
                        )
                    }
                },
                visualTransformation = if (passwordVisible) {
                    VisualTransformation.None
                } else {
                    PasswordVisualTransformation()
                },
                singleLine = true,
                shape = RoundedCornerShape(20.dp),
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Password
                ),
                colors = loginFieldColors()
            )

            if (errorMessage != null) {
                Spacer(Modifier.height(10.dp))
                Text(
                    text = errorMessage.orEmpty(),
                    color = MaterialTheme.colorScheme.error,
                    fontSize = 13.sp
                )
            }

            Spacer(Modifier.height(24.dp))

            Button(
                enabled = !isLoading,
                onClick = {
                    when {
                        email.isBlank() ||
                                !email.contains("@") ||
                                !email.substringAfter("@", "").contains(".") -> {
                            errorMessage =
                                "Ingresa un correo electrónico válido."
                        }

                        password.isBlank() -> {
                            errorMessage = "Ingresa tu contraseña."
                        }

                        else -> {
                            errorMessage = null
                            onLogin(email.trim(), password)
                        }
                    }
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(64.dp),
                shape = RoundedCornerShape(20.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Navy,
                    contentColor = Color.White
                )
            ) {
                if (isLoading) {
                    CircularProgressIndicator(Modifier.size(20.dp), color = Color.White, strokeWidth = 2.dp)
                    Spacer(Modifier.width(12.dp))
                }
                Text(text = if (isLoading) "Iniciando sesión…" else "Iniciar sesión", fontSize = 19.sp, fontWeight = FontWeight.Medium)
            }
        }
        Spacer(Modifier.height(36.dp))

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 16.dp),
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "¿No tienes cuenta? ",
                fontSize = 17.sp,
                color = SecondaryText
            )

            Text(
                text = "Crear una",
                fontSize = 17.sp,
                fontWeight = FontWeight.SemiBold,
                color = Navy,
                modifier = Modifier.clickable {
                    onRegister()
                }
            )
        }
        Spacer(Modifier.height(24.dp))
    }
}

@Composable
private fun SocialLoginButton(
    text: String,
    icon: String,
    iconColor: Color,
    onClick: () -> Unit
) {
    OutlinedButton(
        onClick = onClick,
        modifier = Modifier
            .fillMaxWidth()
            .height(72.dp),
        shape = RoundedCornerShape(20.dp),
        border = androidx.compose.foundation.BorderStroke(
            1.dp,
            BorderColor
        ),
        colors = ButtonDefaults.outlinedButtonColors(
            containerColor = Color.White,
            contentColor = PrimaryText
        )
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center
        ) {
            Text(
                text = icon,
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold,
                color = iconColor
            )

            Spacer(Modifier.width(22.dp))

            Text(
                text = text,
                fontSize = 16.sp,
                fontWeight = FontWeight.Medium
            )
        }
    }
}

@Composable
private fun loginFieldColors() = OutlinedTextFieldDefaults.colors(
    focusedBorderColor = Navy,
    unfocusedBorderColor = BorderColor,
    focusedTextColor = PrimaryText,
    unfocusedTextColor = PrimaryText,
    cursorColor = Navy,
    focusedContainerColor = Color.White,
    unfocusedContainerColor = Color.White
)

@Preview
@Composable
private fun LoginScreenPreview() {
    MaterialTheme {
        LoginScreen(
            modifier = Modifier.fillMaxSize()
        )
    }
}
