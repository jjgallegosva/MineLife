package com.example.aplicacionvariada.core.presentation

import androidx.compose.runtime.Composable

@Composable
fun SplashScreen(navigateToLogin: () -> Unit) {

    SplashContent(
        onTimeOut = {
            navigateToLogin()
        }
    )
}