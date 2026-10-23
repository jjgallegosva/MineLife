package com.example.aplicacionvariada.core.network

import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFalse

class ApiContractTest {
    private val json = Json { explicitNulls = false; encodeDefaults = false }

    @Test
    fun loginRequestUsesBackendFieldNamesAndOmitsOptionalDeviceName() {
        val encoded = json.encodeToString(LoginRequest("user@example.com", "Password123456"))

        assertEquals("""{"email":"user@example.com","password":"Password123456"}""", encoded)
        assertFalse(encoded.contains("deviceName"))
    }

    @Test
    fun registrationRequestMatchesBackendContract() {
        val encoded = json.encodeToString(RegisterRequest("user@example.com", "Mine User", "Password123456"))

        assertEquals("""{"email":"user@example.com","displayName":"Mine User","password":"Password123456"}""", encoded)
    }

    @Test
    fun verificationCodeUsesCodeField() {
        assertEquals("""{"code":"00428173"}""", json.encodeToString(VerifyEmailRequest("00428173")))
    }

    @Test
    fun tokenResponseDecodesFromDataEnvelope() {
        val decoded = json.decodeFromString<ApiEnvelope<TokenPair>>(
            """{"data":{"accessToken":"access","refreshToken":"refresh","tokenType":"Bearer","expiresIn":900}}""",
        )

        assertEquals("access", decoded.data.accessToken)
        assertEquals("refresh", decoded.data.refreshToken)
        assertEquals(900, decoded.data.expiresIn)
    }
}
