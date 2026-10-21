package com.example.aplicacionvariada.auth

interface GoogleAuthClient {
    suspend fun registerWithGoogle(): Result<String>
}