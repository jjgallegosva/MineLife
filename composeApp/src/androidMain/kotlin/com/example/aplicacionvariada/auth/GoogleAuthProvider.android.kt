package com.example.aplicacionvariada.auth

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalContext
import com.example.aplicacionvariada.GoogleAuthService

@Composable
actual fun rememberGoogleAuthClient(): GoogleAuthClient {
    val context = LocalContext.current

    return remember(context) {
        GoogleAuthService(context)
    }
}