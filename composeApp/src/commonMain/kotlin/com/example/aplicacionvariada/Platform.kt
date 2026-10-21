package com.example.aplicacionvariada

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform