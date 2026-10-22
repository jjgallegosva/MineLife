package com.example.aplicacionvariada

import android.content.Context
import android.util.Log
import androidx.credentials.CredentialManager
import androidx.credentials.CustomCredential
import androidx.credentials.GetCredentialRequest
import com.example.aplicacionvariada.auth.GoogleAuthClient
import com.google.android.libraries.identity.googleid.GetGoogleIdOption
import com.google.android.libraries.identity.googleid.GoogleIdTokenCredential
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider
import kotlinx.coroutines.tasks.await

class GoogleAuthService(
    private val context: Context
) : GoogleAuthClient {
    private val auth: FirebaseAuth = FirebaseAuth.getInstance()

    override suspend fun registerWithGoogle(): Result<String> {
        println("GoogleFlow: SE PULSÓ EL BOTÓN DE GOOGLE3")
        return try {
            val credentialManager = CredentialManager.create(context)

            val googleOption = GetGoogleIdOption.Builder()
                .setServerClientId(
                    context.getString(R.string.default_web_client_id)
                )
                .setFilterByAuthorizedAccounts(false)
                .setAutoSelectEnabled(false)
                .build()

            val request = GetCredentialRequest.Builder()
                .addCredentialOption(googleOption)
                .build()

            // Solicitar una credencial nueva al usuario.
            val result = credentialManager.getCredential(
                context = context,
                request = request
            )

            val credential = result.credential

            if (
                credential !is CustomCredential ||
                credential.type !=
                GoogleIdTokenCredential.TYPE_GOOGLE_ID_TOKEN_CREDENTIAL
            ) {
                throw IllegalStateException(
                    "Google no devolvió un ID token válido."
                )
            }

            val googleCredential =
                GoogleIdTokenCredential.createFrom(credential.data)

            // No guardar ni reutilizar este token.
            val firebaseCredential = GoogleAuthProvider.getCredential(
                googleCredential.idToken,
                null
            )
            Log.e("GoogleAuthService", "2. Token de Google obtenido")
            Log.e("GoogleAuthService", "3. Enviando credencial a Firebase")

            val authResult =
                auth.signInWithCredential(firebaseCredential).await()
            Log.e(
                "GoogleAuthService",
                "Firebase login exitoso: ${authResult.user?.uid}"
            )
            val user = authResult.user
                ?: throw IllegalStateException(
                    "Firebase no devolvió un usuario."
                )

            Result.success(user.uid)

        } catch (e: Exception) {
            Log.e(
                "GoogleAuthService",
                "Falló la autenticación con Google",
                e
            )

            Result.failure(e)
        }
    }
}