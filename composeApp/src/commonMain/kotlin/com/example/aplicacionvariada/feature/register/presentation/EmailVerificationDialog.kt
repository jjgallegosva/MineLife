package com.example.aplicacionvariada.feature.register.presentation

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.ui.unit.dp

@Composable
fun EmailVerificationDialog(
    email: String,
    code: String,
    onCodeChange: (String) -> Unit,
    busy: Boolean,
    errorMessage: String?,
    infoMessage: String?,
    onVerify: () -> Unit,
    onResend: () -> Unit,
) {
    AlertDialog(
        onDismissRequest = {},
        title = { Text("Verifica tu correo") },
        text = {
            Column {
                Text("Enviamos un código de 8 dígitos a $email. El código vence en 30 minutos.")
                Spacer(Modifier.height(12.dp))
                OutlinedTextField(
                    value = code,
                    onValueChange = { onCodeChange(it.filter(Char::isDigit).take(8)) },
                    modifier = Modifier.fillMaxWidth(),
                    label = { Text("Código de verificación") },
                    singleLine = true,
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.NumberPassword),
                    isError = errorMessage != null,
                )
                if (errorMessage != null) {
                    Spacer(Modifier.height(6.dp))
                    Text(errorMessage, color = MaterialTheme.colorScheme.error, style = MaterialTheme.typography.bodySmall)
                }
                if (infoMessage != null) {
                    Spacer(Modifier.height(6.dp))
                    Text(infoMessage, color = MaterialTheme.colorScheme.primary, style = MaterialTheme.typography.bodySmall)
                }
            }
        },
        confirmButton = {
            TextButton(onClick = onVerify, enabled = !busy && code.length == 8) {
                if (busy) CircularProgressIndicator()
                else Text("Verificar")
            }
        },
        dismissButton = {
            TextButton(onClick = onResend, enabled = !busy) { Text("Reenviar código") }
        },
    )
}
