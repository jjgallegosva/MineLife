package com.example.aplicacionvariada.feature.register.presentation

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Email
import androidx.compose.material.icons.outlined.Lock
import androidx.compose.material.icons.outlined.PersonAdd
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

private val RegisterNavy = Color(0xFF10233F)
private val RegisterBackground = Color(0xFFF4F6F8)
private val RegisterBorder = Color(0xFFD9E0E8)
private val RegisterText = Color(0xFF171D29)
private val RegisterSecondary = Color(0xFF64748B)

@Composable
fun RegisterScreen(
    onRegister: (name: String, email: String, password: String) -> Unit = { _, _, _ -> },
    onGoogleRegister: () -> Unit = {},
    onFacebookRegister: () -> Unit = {},
    onLogin: () -> Unit = {},
    modifier: Modifier = Modifier
) {
    var name by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var confirmPassword by remember { mutableStateOf("") }

    var passwordVisible by remember { mutableStateOf(false) }
    var confirmPasswordVisible by remember { mutableStateOf(false) }
    var errorMessage by remember { mutableStateOf<String?>(null) }

    Column(
        modifier = modifier
            .fillMaxSize()
            .background(RegisterBackground)
            .verticalScroll(rememberScrollState())
            .padding(horizontal = 22.dp, vertical = 16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // Encabezado
        Box(
            modifier = Modifier
                .size(74.dp)
                .clip(RoundedCornerShape(23.dp))
                .background(RegisterNavy),
            contentAlignment = Alignment.Center
        ) {
            Icon(
                imageVector = Icons.Outlined.PersonAdd,
                contentDescription = "Crear cuenta",
                tint = Color.White,
                modifier = Modifier.size(36.dp)
            )
        }

        Spacer(Modifier.height(22.dp))

        Text(
            text = "Crea tu cuenta",
            fontSize = 34.sp,
            lineHeight = 40.sp,
            fontWeight = FontWeight.Bold,
            color = RegisterText,
            textAlign = TextAlign.Center
        )

        Spacer(Modifier.height(14.dp))

        Text(
            text = "Empieza a controlar tus turnos en minutos",
            fontSize = 18.sp,
            lineHeight = 25.sp,
            color = RegisterSecondary,
            textAlign = TextAlign.Center
        )

        Spacer(Modifier.height(54.dp))

        // Tarjeta principal
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .clip(RoundedCornerShape(23.dp))
                .background(Color.White)
                .border(
                    1.dp,
                    RegisterBorder,
                    RoundedCornerShape(23.dp)
                )
                .padding(horizontal = 22.dp, vertical = 28.dp),
            horizontalAlignment = Alignment.Start
        ) {
            SocialRegisterButton(
                text = "Continuar con Google",
                icon = "G",
                iconColor = Color(0xFF4285F4),
                onClick = onGoogleRegister
            )

            Spacer(Modifier.height(10.dp))

            SocialRegisterButton(
                text = "Continuar con Facebook",
                icon = "f",
                iconColor = Color(0xFF1877F2),
                onClick = onFacebookRegister
            )

            Spacer(Modifier.height(30.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                HorizontalDivider(
                    modifier = Modifier.weight(1f),
                    color = RegisterBorder
                )

                Text(
                    text = "O",
                    color = RegisterSecondary,
                    fontSize = 14.sp,
                    modifier = Modifier.padding(horizontal = 16.dp)
                )

                HorizontalDivider(
                    modifier = Modifier.weight(1f),
                    color = RegisterBorder
                )
            }

            Spacer(Modifier.height(38.dp))

            RegisterFieldLabel("Nombre y apellido")

            Spacer(Modifier.height(12.dp))

            OutlinedTextField(
                value = name,
                onValueChange = {
                    name = it
                    errorMessage = null
                },
                modifier = Modifier.fillMaxWidth(),
                placeholder = {
                    Text("Juan Quispe", color = RegisterSecondary)
                },
                singleLine = true,
                shape = RoundedCornerShape(19.dp),
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Text
                ),
                colors = registerFieldColors()
            )

            Spacer(Modifier.height(24.dp))

            RegisterFieldLabel("Correo electrónico")

            Spacer(Modifier.height(12.dp))

            OutlinedTextField(
                value = email,
                onValueChange = {
                    email = it
                    errorMessage = null
                },
                modifier = Modifier.fillMaxWidth(),
                placeholder = {
                    Text("tu@correo.com", color = RegisterSecondary)
                },
                leadingIcon = {
                    Icon(
                        Icons.Outlined.Email,
                        contentDescription = null,
                        tint = RegisterSecondary
                    )
                },
                singleLine = true,
                shape = RoundedCornerShape(19.dp),
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Email
                ),
                colors = registerFieldColors()
            )

            Spacer(Modifier.height(24.dp))

            RegisterFieldLabel("Contraseña")

            Spacer(Modifier.height(12.dp))

            OutlinedTextField(
                value = password,
                onValueChange = {
                    password = it
                    errorMessage = null
                },
                modifier = Modifier.fillMaxWidth(),
                placeholder = {
                    Text("••••••••", color = RegisterSecondary)
                },
                leadingIcon = {
                    Icon(
                        Icons.Outlined.Lock,
                        contentDescription = null,
                        tint = RegisterSecondary
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
                            tint = RegisterSecondary
                        )
                    }
                },
                visualTransformation = if (passwordVisible) {
                    VisualTransformation.None
                } else {
                    PasswordVisualTransformation()
                },
                singleLine = true,
                shape = RoundedCornerShape(19.dp),
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Password
                ),
                colors = registerFieldColors()
            )

            Spacer(Modifier.height(24.dp))

            RegisterFieldLabel("Confirmar contraseña")

            Spacer(Modifier.height(12.dp))

            OutlinedTextField(
                value = confirmPassword,
                onValueChange = {
                    confirmPassword = it
                    errorMessage = null
                },
                modifier = Modifier.fillMaxWidth(),
                placeholder = {
                    Text("••••••••", color = RegisterSecondary)
                },
                leadingIcon = {
                    Icon(
                        Icons.Outlined.Lock,
                        contentDescription = null,
                        tint = RegisterSecondary
                    )
                },
                trailingIcon = {
                    IconButton(
                        onClick = {
                            confirmPasswordVisible =
                                !confirmPasswordVisible
                        }
                    ) {
                        Icon(
                            imageVector = if (confirmPasswordVisible) {
                                Icons.Outlined.VisibilityOff
                            } else {
                                Icons.Outlined.Visibility
                            },
                            contentDescription = if (confirmPasswordVisible) {
                                "Ocultar contraseña"
                            } else {
                                "Mostrar contraseña"
                            },
                            tint = RegisterSecondary
                        )
                    }
                },
                visualTransformation = if (confirmPasswordVisible) {
                    VisualTransformation.None
                } else {
                    PasswordVisualTransformation()
                },
                singleLine = true,
                shape = RoundedCornerShape(19.dp),
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Password
                ),
                colors = registerFieldColors()
            )

            if (errorMessage != null) {
                Spacer(Modifier.height(12.dp))

                Text(
                    text = errorMessage.orEmpty(),
                    color = MaterialTheme.colorScheme.error,
                    fontSize = 13.sp
                )
            }

            Spacer(Modifier.height(22.dp))

            Button(
                onClick = {
                    val normalizedEmail = email.trim()

                    when {
                        name.isBlank() -> {
                            errorMessage = "Ingresa tu nombre y apellido."
                        }

                        !normalizedEmail.contains("@") ||
                                !normalizedEmail.substringAfter("@", "")
                                    .contains(".") -> {
                            errorMessage =
                                "Ingresa un correo electrónico válido."
                        }

                        password.length < 8 -> {
                            errorMessage =
                                "La contraseña debe tener al menos 8 caracteres."
                        }

                        password != confirmPassword -> {
                            errorMessage =
                                "Las contraseñas no coinciden."
                        }

                        else -> {
                            errorMessage = null
                            onRegister(
                                name.trim(),
                                normalizedEmail,
                                password
                            )
                        }
                    }
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(64.dp),
                shape = RoundedCornerShape(19.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = RegisterNavy,
                    contentColor = Color.White
                )
            ) {
                Text(
                    text = "Crear cuenta",
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Medium
                )
            }
        }

        Spacer(Modifier.height(32.dp))

        // Enlace inferior
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 16.dp),
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "¿Ya tienes cuenta? ",
                fontSize = 16.sp,
                color = RegisterSecondary
            )

            Text(
                text = "Inicia sesión",
                fontSize = 16.sp,
                fontWeight = FontWeight.Medium,
                color = RegisterNavy,
                modifier = Modifier.clickable {
                    onLogin()
                }
            )
        }
    }
}

@Composable
private fun RegisterFieldLabel(text: String) {
    Text(
        text = text,
        fontSize = 17.sp,
        fontWeight = FontWeight.Medium,
        color = RegisterText
    )
}

@Composable
private fun SocialRegisterButton(
    text: String,
    icon: String,
    iconColor: Color,
    onClick: () -> Unit
) {
    OutlinedButton(
        onClick = onClick,
        modifier = Modifier
            .fillMaxWidth()
            .height(64.dp),
        shape = RoundedCornerShape(19.dp),
        border = androidx.compose.foundation.BorderStroke(
            1.dp,
            RegisterBorder
        ),
        colors = ButtonDefaults.outlinedButtonColors(
            containerColor = Color.White,
            contentColor = RegisterText
        )
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center
        ) {
            Text(
                text = icon,
                fontSize = 23.sp,
                fontWeight = FontWeight.Bold,
                color = iconColor
            )

            Spacer(Modifier.width(20.dp))

            Text(
                text = text,
                fontSize = 16.sp,
                fontWeight = FontWeight.Medium
            )
        }
    }
}

@Composable
private fun registerFieldColors() = OutlinedTextFieldDefaults.colors(
    focusedBorderColor = RegisterNavy,
    unfocusedBorderColor = RegisterBorder,
    focusedTextColor = RegisterText,
    unfocusedTextColor = RegisterText,
    cursorColor = RegisterNavy,
    focusedContainerColor = Color.White,
    unfocusedContainerColor = Color.White
)

@Preview
@Composable
private fun RegisterScreenPreview() {
    MaterialTheme {
        RegisterScreen(
            modifier = Modifier.fillMaxSize()
        )
    }
}
