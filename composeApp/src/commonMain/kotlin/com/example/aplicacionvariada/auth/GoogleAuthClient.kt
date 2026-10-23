package com.example.aplicacionvariada.auth

interface GoogleAuthClient {
    suspend fun getGoogleIdToken(): Result<String>
}
