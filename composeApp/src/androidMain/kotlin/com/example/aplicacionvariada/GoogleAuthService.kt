package com.example.aplicacionvariada

import android.content.Context
import androidx.credentials.CredentialManager
import androidx.credentials.CustomCredential
import androidx.credentials.GetCredentialRequest
import com.example.aplicacionvariada.auth.GoogleAuthClient
import com.google.android.libraries.identity.googleid.GetGoogleIdOption
import com.google.android.libraries.identity.googleid.GoogleIdTokenCredential
import kotlinx.coroutines.CancellationException

class GoogleAuthService(
    private val context: Context
) : GoogleAuthClient {
    override suspend fun getGoogleIdToken(): Result<String> {
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

            Result.success(googleCredential.idToken)

        } catch (error: CancellationException) {
            throw error
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}
