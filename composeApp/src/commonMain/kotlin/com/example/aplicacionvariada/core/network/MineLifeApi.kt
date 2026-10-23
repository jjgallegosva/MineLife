package com.example.aplicacionvariada.core.network

import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.HttpTimeout
import io.ktor.client.request.HttpRequestBuilder
import io.ktor.client.request.accept
import io.ktor.client.request.delete
import io.ktor.client.request.get
import io.ktor.client.request.header
import io.ktor.client.request.patch
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import io.ktor.client.statement.HttpResponse
import io.ktor.http.ContentType
import io.ktor.http.HttpHeaders
import io.ktor.http.contentType
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.JsonObject
import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.sync.withLock
import kotlinx.coroutines.CancellationException

@Serializable
data class ApiEnvelope<T>(val data: T)

@Serializable
data class ApiErrorBody(val title: String = "No se pudo completar la solicitud", val status: Int? = null, val code: String? = null, val requestId: String? = null)

class MineLifeApiException(val status: Int, val code: String?, val requestId: String?, override val message: String) : Exception(message)

fun Throwable.toApiUserMessage(): String = when (this) {
    is MineLifeApiException -> message
    else -> "No se pudo conectar con MineLife. Revisa tu conexión e inténtalo nuevamente."
}

class MineLifeApi(@PublishedApi internal val client: HttpClient = HttpClient {
    expectSuccess = false
    install(HttpTimeout) {
        requestTimeoutMillis = 20_000
        connectTimeoutMillis = 10_000
        socketTimeoutMillis = 20_000
    }
    install(ContentNegotiation) {
        json(Json {
            ignoreUnknownKeys = true
            explicitNulls = false
            encodeDefaults = false
        })
    }
}, private val baseUrl: String = apiBaseUrl().trimEnd('/')) {
    suspend inline fun <reified T> get(path: String, accessToken: String? = null): T = decode(client.get(url(path)) { commonHeaders(accessToken) })
    suspend inline fun <reified T, reified B> post(path: String, body: B, accessToken: String? = null): T = decode(client.post(url(path)) { commonHeaders(accessToken); setBody(body) })
    suspend inline fun <reified T, reified B> patch(path: String, body: B, accessToken: String): T = decode(client.patch(url(path)) { commonHeaders(accessToken); setBody(body) })
    suspend fun delete(path: String, accessToken: String) { checkStatus(client.delete(url(path)) { commonHeaders(accessToken) }) }
    @PublishedApi internal fun url(path: String) = "$baseUrl/api/v1/${path.trimStart('/')}"
    @PublishedApi internal fun HttpRequestBuilder.commonHeaders(accessToken: String?) {
        accept(ContentType.Application.Json)
        contentType(ContentType.Application.Json)
        header(HttpHeaders.CacheControl, "no-store")
        accessToken?.let { header(HttpHeaders.Authorization, "Bearer $it") }
    }
    @PublishedApi internal suspend inline fun <reified T> decode(response: HttpResponse): T { checkStatus(response); return response.body() }
    @PublishedApi internal suspend fun checkStatus(response: HttpResponse) {
        if (response.status.value in 200..299) return
        val error = runCatching { response.body<ApiErrorBody>() }.getOrNull()
        throw MineLifeApiException(response.status.value, error?.code, error?.requestId, error?.title ?: when (response.status.value) {
            401 -> "Tu sesión no es válida. Inicia sesión nuevamente."
            429 -> "Hay demasiadas solicitudes. Inténtalo más tarde."
            in 500..599 -> "El servicio no está disponible. Inténtalo más tarde."
            else -> "No se pudo completar la solicitud."
        })
    }
}

@Serializable
data class LoginRequest(val email: String, val password: String, val deviceName: String? = null)
@Serializable
data class RegisterRequest(val email: String, val displayName: String, val password: String, val deviceName: String? = null)
@Serializable
data class RefreshRequest(val refreshToken: String)
@Serializable
data class TokenPair(val accessToken: String, val refreshToken: String, val tokenType: String, val expiresIn: Int)
@Serializable
data class RegisteredUser(val id: String, val email: String, val displayName: String, val status: String)
@Serializable
data class EmailRequest(val email: String)
@Serializable
data class ResetPasswordRequest(val token: String, val password: String)
@Serializable
data class ChangePasswordRequest(val currentPassword: String, val newPassword: String)

class AuthRepository(private val api: MineLifeApi = MineLifeApi()) {
    private var session: TokenPair? = null
        private set
    private val refreshMutex = Mutex()
    suspend fun login(email: String, password: String): TokenPair {
        val result = api.post<ApiEnvelope<TokenPair>, LoginRequest>("auth/login", LoginRequest(email.trim().lowercase(), password, "MineLife mobile"))
        session = result.data
        return result.data
    }
    suspend fun register(name: String, email: String, password: String): RegisteredUser = api.post<ApiEnvelope<RegisteredUser>, RegisterRequest>("auth/register", RegisterRequest(email.trim().lowercase(), name.trim(), password, "MineLife mobile")).data
    suspend fun currentProfile(): UserProfile = api.get<ApiEnvelope<UserProfile>>("users/me", session?.accessToken).data
    suspend fun verifyEmail(code: String) { api.post<ApiEnvelope<JsonObject>, VerifyEmailRequest>("auth/verify-email", VerifyEmailRequest(code)) }
    suspend fun resendVerification(email: String) { api.post<ApiEnvelope<JsonObject>, EmailRequest>("auth/resend-verification", EmailRequest(email.trim().lowercase())) }
    suspend fun forgotPassword(email: String) { api.post<ApiEnvelope<JsonObject>, EmailRequest>("auth/forgot-password", EmailRequest(email.trim().lowercase())) }
    suspend fun resetPassword(token: String, password: String) { api.post<ApiEnvelope<JsonObject>, ResetPasswordRequest>("auth/reset-password", ResetPasswordRequest(token, password)); session = null }
    suspend fun changePassword(currentPassword: String, newPassword: String) {
        val accessToken = session?.accessToken ?: throw MineLifeApiException(401, "UNAUTHENTICATED", null, "Inicia sesión para cambiar tu contraseña.")
        api.post<ApiEnvelope<JsonObject>, ChangePasswordRequest>("auth/change-password", ChangePasswordRequest(currentPassword, newPassword), accessToken)
        session = null
    }
    suspend fun logoutAll() {
        val accessToken = session?.accessToken ?: return
        try { api.post<ApiEnvelope<JsonObject>, EmptyRequest>("auth/logout-all", EmptyRequest, accessToken) }
        finally { session = null }
    }
    suspend fun refresh(): TokenPair? = refreshMutex.withLock {
        val current = session ?: return null
        try {
            val rotated = api.post<ApiEnvelope<TokenPair>, RefreshRequest>("auth/refresh", RefreshRequest(current.refreshToken)).data
            session = rotated
            rotated
        } catch (error: CancellationException) {
            throw error
        } catch (error: Exception) {
            session = null
            throw error
        }
    }
    suspend fun logout() {
        val current = session ?: return
        try { api.post<ApiEnvelope<JsonObject>, RefreshRequest>("auth/logout", RefreshRequest(current.refreshToken)) }
        finally { session = null }
    }
}

@Serializable
data class UserProfile(val id: String, val email: String, val displayName: String, val status: String? = null, val emailVerifiedAt: String? = null, val createdAt: String? = null, val profile: ProfileDetails? = null)
@Serializable
data class ProfileDetails(val phone: String? = null, val avatarUrl: String? = null, val timezone: String? = null, val locale: String? = null)
@Serializable
data class VerifyEmailRequest(val code: String)
@Serializable
data object EmptyRequest
