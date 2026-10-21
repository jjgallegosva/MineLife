package com.example.nexogestion.ui.auth

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.example.nexogestion.ui.theme.NexoColors

@Composable
fun RecoverPasswordScreen(
    onBackClick: () -> Unit = {},
    onPasswordChanged: () -> Unit = {}
) {
    var currentStep by rememberSaveable { mutableStateOf(1) }

    var email by rememberSaveable { mutableStateOf("") }
    var code by rememberSaveable { mutableStateOf("") }
    var newPassword by rememberSaveable { mutableStateOf("") }
    var confirmPassword by rememberSaveable { mutableStateOf("") }

    AuthBackground {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .padding(horizontal = 28.dp)
                .padding(top = 54.dp, bottom = 28.dp)
        ) {
            BackButton(
                onClick = {
                    if (currentStep > 1) {
                        currentStep--
                    } else {
                        onBackClick()
                    }
                }
            )

            Spacer(Modifier.height(24.dp))

            BrandHeader(
                modifier = Modifier.align(Alignment.CenterHorizontally)
            )

            Spacer(Modifier.height(56.dp))

            Text(
                text = when (currentStep) {
                    1 -> "Recuperar contraseña"
                    2 -> "Verificar código"
                    3 -> "Nueva contraseña"
                    else -> "Contraseña actualizada"
                },
                style = MaterialTheme.typography.headlineMedium,
                color = NexoColors.TextPrimary,
                modifier = Modifier.align(Alignment.CenterHorizontally)
            )

            Spacer(Modifier.height(8.dp))

            Text(
                text = when (currentStep) {
                    1 -> "Te ayudaremos a restablecer tu contraseña"
                    2 -> "Ingresa el código enviado a tu correo"
                    3 -> "Crea una nueva contraseña segura"
                    else -> "Ya puedes iniciar sesión nuevamente"
                },
                style = MaterialTheme.typography.bodyLarge,
                color = NexoColors.TextSecondary,
                modifier = Modifier.align(Alignment.CenterHorizontally)
            )

            Spacer(Modifier.height(36.dp))

            AuthCard {
                RecoveryStepper(currentStep = currentStep)

                Spacer(Modifier.height(36.dp))

                when (currentStep) {
                    1 -> StepEmail(
                        email = email,
                        onEmailChange = { email = it },
                        onContinue = {
                            currentStep = 2
                        }
                    )

                    2 -> StepCode(
                        code = code,
                        onCodeChange = { code = it },
                        onContinue = {
                            currentStep = 3
                        }
                    )

                    3 -> StepNewPassword(
                        newPassword = newPassword,
                        onNewPasswordChange = { newPassword = it },
                        confirmPassword = confirmPassword,
                        onConfirmPasswordChange = { confirmPassword = it },
                        onContinue = {
                            currentStep = 4
                        }
                    )

                    4 -> StepSuccess(
                        onContinue = onPasswordChanged
                    )
                }
            }

            Spacer(Modifier.height(34.dp))

            if (currentStep != 4) {
                SecurityInfoCard()
            }
        }
    }
}

@Composable
private fun StepEmail(
    email: String,
    onEmailChange: (String) -> Unit,
    onContinue: () -> Unit
) {
    Text(
        text = "Ingresa tu correo electrónico",
        style = MaterialTheme.typography.titleMedium,
        color = NexoColors.TextPrimary
    )

    Spacer(Modifier.height(8.dp))

    Text(
        text = "Te enviaremos un código de verificación para restablecer tu contraseña.",
        style = MaterialTheme.typography.bodyLarge,
        color = NexoColors.TextSecondary
    )

    Spacer(Modifier.height(24.dp))

    AuthTextField(
        value = email,
        onValueChange = onEmailChange,
        label = "",
        placeholder = "ejemplo@correo.com",
        leadingIcon = {}
    )

    Spacer(Modifier.height(28.dp))

    PrimaryAuthButton(
        text = "Enviar código",
        onClick = onContinue
    )
}

@Composable
private fun StepCode(
    code: String,
    onCodeChange: (String) -> Unit,
    onContinue: () -> Unit
) {
    Text(
        text = "Código de verificación",
        style = MaterialTheme.typography.titleMedium,
        color = NexoColors.TextPrimary
    )

    Spacer(Modifier.height(8.dp))

    Text(
        text = "Escribe el código que enviamos a tu correo electrónico.",
        style = MaterialTheme.typography.bodyLarge,
        color = NexoColors.TextSecondary
    )

    Spacer(Modifier.height(24.dp))

    AuthTextField(
        value = code,
        onValueChange = onCodeChange,
        label = "",
        placeholder = "Código de verificación",
        leadingIcon = {}
    )

    Spacer(Modifier.height(28.dp))

    PrimaryAuthButton(
        text = "Verificar código",
        onClick = onContinue
    )
}

@Composable
private fun StepNewPassword(
    newPassword: String,
    onNewPasswordChange: (String) -> Unit,
    confirmPassword: String,
    onConfirmPasswordChange: (String) -> Unit,
    onContinue: () -> Unit
) {
    Text(
        text = "Crea tu nueva contraseña",
        style = MaterialTheme.typography.titleMedium,
        color = NexoColors.TextPrimary
    )

    Spacer(Modifier.height(8.dp))

    Text(
        text = "Usa una contraseña segura para proteger tu cuenta.",
        style = MaterialTheme.typography.bodyLarge,
        color = NexoColors.TextSecondary
    )

    Spacer(Modifier.height(24.dp))

    AuthTextField(
        value = newPassword,
        onValueChange = onNewPasswordChange,
        label = "Nueva contraseña",
        placeholder = "Ingresa tu nueva contraseña",
        leadingIcon = {},
        isPassword = true
    )

    Spacer(Modifier.height(22.dp))

    AuthTextField(
        value = confirmPassword,
        onValueChange = onConfirmPasswordChange,
        label = "Confirmar contraseña",
        placeholder = "Repite tu contraseña",
        leadingIcon = {},
        isPassword = true
    )

    Spacer(Modifier.height(28.dp))

    PrimaryAuthButton(
        text = "Actualizar contraseña",
        onClick = onContinue
    )
}

@Composable
private fun StepSuccess(
    onContinue: () -> Unit
) {
    Column(
        modifier = Modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Box(
            modifier = Modifier
                .size(74.dp)
                .background(NexoColors.AccentPremium, CircleShape),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = "✓",
                style = MaterialTheme.typography.headlineMedium,
                color = Color.White
            )
        }

        Spacer(Modifier.height(24.dp))

        Text(
            text = "Todo listo",
            style = MaterialTheme.typography.titleLarge,
            color = NexoColors.TextPrimary
        )

        Spacer(Modifier.height(8.dp))

        Text(
            text = "Tu contraseña fue actualizada correctamente.",
            style = MaterialTheme.typography.bodyLarge,
            color = NexoColors.TextSecondary
        )

        Spacer(Modifier.height(28.dp))

        PrimaryAuthButton(
            text = "Volver a iniciar sesión",
            onClick = onContinue
        )
    }
}

@Composable
private fun RecoveryStepper(currentStep: Int) {
    val steps = listOf("Correo", "Verificar", "Nueva", "Confirmar")

    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.Top
    ) {
        steps.forEachIndexed { index, label ->
            val step = index + 1
            val active = step == currentStep
            val completed = step < currentStep

            Column(
                modifier = Modifier.weight(1f),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Box(
                    modifier = Modifier
                        .size(56.dp)
                        .background(
                            color = when {
                                active -> NexoColors.PrimaryDark
                                completed -> NexoColors.AccentPremium
                                else -> NexoColors.InfoSoft
                            },
                            shape = CircleShape
                        )
                        .border(
                            width = 1.dp,
                            color = when {
                                active -> NexoColors.PrimaryDark
                                completed -> NexoColors.AccentPremium
                                else -> NexoColors.Border
                            },
                            shape = CircleShape
                        ),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = if (completed) "✓" else step.toString(),
                        style = MaterialTheme.typography.labelLarge,
                        color = if (active || completed) Color.White else NexoColors.TextSecondary
                    )
                }

                Spacer(Modifier.height(10.dp))

                Text(
                    text = label,
                    style = MaterialTheme.typography.bodyMedium,
                    color = if (active) NexoColors.TextPrimary else NexoColors.TextSecondary
                )
            }
        }
    }
}