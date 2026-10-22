package com.example.aplicacionvariada.auth

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember

@Composable
actual fun rememberGoogleAuthClient(): GoogleAuthClient {
    return remember {
        IOSGoogleAuthClient()
    }
}