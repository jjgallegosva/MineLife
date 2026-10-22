package com.example.aplicacionvariada.auth

class IOSGoogleAuthClient : GoogleAuthClient {

    override suspend fun registerWithGoogle(): Result<String> {
        return Result.failure(
            UnsupportedOperationException(
                "El inicio de sesión con Google todavía no está implementado para iOS."
            )
        )
    }
}