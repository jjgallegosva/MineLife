package com.example.aplicacionvariada
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.navigation3.runtime.NavEntry
import androidx.navigation3.runtime.NavKey
import androidx.navigation3.runtime.rememberNavBackStack
import androidx.navigation3.ui.NavDisplay
import androidx.savedstate.serialization.SavedStateConfiguration
import com.example.aplicacionvariada.auth.rememberGoogleAuthClient
import com.example.aplicacionvariada.core.presentation.SplashScreen
import com.example.aplicacionvariada.feature.jornadaLaboral.presentation.ui.ConsultarJornadaScreen
import kotlinx.serialization.modules.SerializersModule
import kotlinx.serialization.modules.polymorphic
import com.example.aplicacionvariada.feature.jornadaLaboral.presentation.ui.JornadaCalendarScreen
import com.example.aplicacionvariada.feature.register.presentation.RegisterScreen
import com.example.aplicacionvariada.feature.register.presentation.EmailVerificationDialog
import com.example.nexogestion.ui.auth.RecoverPasswordScreen
import com.minelife.ui.login.LoginScreen
import jornadaPatterns
import kotlinx.coroutines.launch
import kotlinx.datetime.LocalDate
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.TextButton
import com.example.aplicacionvariada.feature.calendar.presentation.CalendarScreen
import com.minelife.ui.home.HomeScreen
import com.example.aplicacionvariada.feature.calendar.data.RegimeDataStore
import com.example.aplicacionvariada.core.network.AuthRepository
import com.example.aplicacionvariada.core.network.toApiUserMessage
import kotlinx.coroutines.CancellationException

@Composable
fun AdvanceNavigationWrapper(regimeDataStore: RegimeDataStore) {
    val googleAuthClient = rememberGoogleAuthClient()
    val coroutineScope = rememberCoroutineScope()
    var googleError by remember { mutableStateOf<String?>(null) }
    var authMessage by remember { mutableStateOf<String?>(null) }
    var authBusy by remember { mutableStateOf(false) }
    var verificationEmail by remember { mutableStateOf<String?>(null) }
    var verificationCode by remember { mutableStateOf("") }
    var verificationError by remember { mutableStateOf<String?>(null) }
    var verificationInfo by remember { mutableStateOf<String?>(null) }
    var verificationBusy by remember { mutableStateOf(false) }
    var userDisplayName by remember { mutableStateOf("MineLife") }
    val authRepository = remember { AuthRepository() }
    val backStack = rememberNavBackStack(serializersConfig,Routes.Splash)
    NavDisplay(
        backStack= backStack,
        onBack = {backStack.removeLastOrNull()},
        entryProvider = { key ->
            when(key){
                is Routes.Splash -> NavEntry(key) {
                    SplashScreen() {
                        backStack.add(Routes.Login)
                    }
                }
                is Routes.Login -> NavEntry(key) {
                    LoginScreen(
                        onLogin = { email, password ->
                            if (!authBusy) coroutineScope.launch {
                                authBusy = true
                                try {
                                    authRepository.login(email, password)
                                    userDisplayName = try {
                                        authRepository.currentProfile().displayName
                                    } catch (error: CancellationException) {
                                        throw error
                                    } catch (_: Exception) {
                                        "MineLife"
                                    }
                                    backStack.clear()
                                    backStack.add(Routes.Home)
                                } catch (error: CancellationException) {
                                    throw error
                                } catch (error: Exception) {
                                    authMessage = error.toApiUserMessage()
                                } finally {
                                    authBusy = false
                                }
                            }
                        },

                        onGoogleLogin = {
                            println("GoogleFlow: SE PULSÓ EL BOTÓN DE GOOGLE")
                            googleError = null

                            coroutineScope.launch {
                                try {
                                    println("GoogleFlow: SE PULSÓ EL BOTÓN DE GOOGLE2")
                                    val result = googleAuthClient.registerWithGoogle()

                                    result.onSuccess {
                                        backStack.add(Routes.Home)
                                    }.onFailure { error ->
                                        googleError = error.message
                                            ?: "No se pudo iniciar sesión con Google."
                                    }
                                } catch (error: Exception) {
                                    googleError = error.message
                                        ?: "Ocurrió un error al iniciar sesión."
                                }
                            }
                        },

                        onFacebookLogin = {
                            // Pendiente de implementar.
                        },
                        onForgotPassword = {
                            backStack.add(Routes.RecoverPassword)
                        },
                        onRegister = {
                            backStack.add(Routes.Register)
                        },
                        isLoading = authBusy,
                    )
                }
                is Routes.Register -> NavEntry(key) {
                    RegisterScreen(
                        onRegister = { name, email, password ->
                            if (!authBusy) coroutineScope.launch {
                                authBusy = true
                                try {
                                    val registered = authRepository.register(name, email, password)
                                    backStack.removeLastOrNull()
                                    verificationEmail = registered.email
                                    verificationCode = ""
                                    verificationError = null
                                    verificationInfo = null
                                } catch (error: CancellationException) {
                                    throw error
                                } catch (error: Exception) {
                                    authMessage = error.toApiUserMessage()
                                } finally {
                                    authBusy = false
                                }
                            }
                        },
                        onGoogleRegister = {
                            googleError = null

                            coroutineScope.launch {
                                try {
                                    val result = googleAuthClient.registerWithGoogle()

                                    result.onSuccess {
                                        backStack.add(Routes.Home)
                                    }.onFailure { error ->
                                        googleError = error.message
                                            ?: "No se pudo autenticar con Google."
                                    }
                                } catch (error: Exception) {
                                    googleError = error.message
                                        ?: "Ocurrió un error al conectar con Google."
                                }
                            }
                        },
                        onFacebookRegister = {
                            // Pendiente de implementar.
                        },
                        onLogin = {
                            backStack.removeLastOrNull()
                        },
                        isLoading = authBusy,
                    )
                }
                is Routes.RecoverPassword -> NavEntry(key) {
                    RecoverPasswordScreen(
                        onBackClick = {
                            backStack.removeLastOrNull()
                        },
                        onPasswordChanged = {
                            // Aquí luego puedes mandar a la pantalla de verificar código
                            // backStack.add(Routes.VerifyCode)
                        }
                    )
                }
                is Routes.Home -> NavEntry(key) {
                    HomeScreen(
                        regimeDataStore = regimeDataStore,
                        userName = userDisplayName,
                        onNavigate = { destination ->
                            when (destination) {
                                "Inicio" -> {
                                    // Ya estamos en Home
                                }

                                "Calendario" -> {
                                    backStack.add(Routes.Calendar)
                                }

                                "Registrar turno" -> {
                                    backStack.add(Routes.ConsultarJornada)
                                }

                                "Mochila" -> {
                                    // Agregar la ruta de mochila cuando exista
                                }

                                "Gastos" -> {
                                    // Agregar la ruta de gastos cuando exista
                                }

                                "Más" -> {
                                    // Agregar la ruta de configuración cuando exista
                                }

                                "Cerrar sesión" -> {
                                    coroutineScope.launch {
                                        try { authRepository.logout() }
                                        catch (error: CancellationException) { throw error }
                                        catch (error: Exception) { authMessage = error.toApiUserMessage() }
                                        finally { userDisplayName = "MineLife"; backStack.clear(); backStack.add(Routes.Login) }
                                    }
                                }
                            }
                        }
                    )
                }
                is Routes.ConsultarJornada -> NavEntry(key) {
                    ConsultarJornadaScreen(
                        onOpenCalendar = { startDate, pattern, range ->
                            backStack.add(
                                Routes.JornadaCalendar(
                                    year = startDate.year,
                                    month = startDate.monthNumber,
                                    day = startDate.dayOfMonth,
                                    patternName = pattern.name,
                                    range = range,

                                )
                            )
                        },onBack = {
                            backStack.removeLastOrNull()
                        }
                    )
                }
                is Routes.JornadaCalendar -> NavEntry(key) {
                    val startDate = LocalDate(
                        year = key.year,
                        monthNumber = key.month,
                        dayOfMonth = key.day
                    )

                    val pattern = jornadaPatterns.firstOrNull { it.name == key.patternName }
                        ?: jornadaPatterns.first()

                    JornadaCalendarScreen(
                        startDate = startDate,
                        pattern = pattern,
                        range = key.range,
                        onBack = {
                            backStack.removeLastOrNull()
                        }
                    )
                }
                is Routes.Calendar -> NavEntry(key) {
                    CalendarScreen(
                        regimeDataStore = regimeDataStore,
                        userName = userDisplayName,
                        onNavigate = { destination ->
                            when (destination) {
                                "Inicio" -> backStack.add(Routes.Home)
                                "Calendario" -> { /* Ya estás aquí */ }
                                "Mochila" -> { /* Conectar ruta real */ }
                                "Gastos" -> { /* Conectar ruta real */ }
                                "Más" -> { /* Conectar ruta real */ }
                            }
                        }
                    )
                }

                else ->{
                    NavEntry(key) {
                        Text("Error")
                    }
                }
            }

        }
    )
    googleError?.let { message ->
        AlertDialog(
            onDismissRequest = {
                googleError = null
            },
            title = {
                Text("Error con Google")
            },
            text = {
                Text(message)
            },
            confirmButton = {
                TextButton(
                    onClick = {
                        googleError = null
                    }
                ) {
                    Text("Aceptar")
                }
            }
        )
    }
    authMessage?.let { message ->
        AlertDialog(
            onDismissRequest = { authMessage = null },
            title = { Text("MineLife") },
            text = { Text(message) },
            confirmButton = { TextButton(onClick = { authMessage = null }) { Text("Aceptar") } },
        )
    }
    verificationEmail?.let { email ->
        EmailVerificationDialog(
            email = email,
            code = verificationCode,
            onCodeChange = { verificationCode = it; verificationError = null },
            busy = verificationBusy,
            errorMessage = verificationError,
            infoMessage = verificationInfo,
            onVerify = {
                if (!verificationBusy && verificationCode.length == 8) coroutineScope.launch {
                    verificationBusy = true
                    verificationError = null
                    try {
                        authRepository.verifyEmail(verificationCode)
                        verificationEmail = null
                        authMessage = "Correo verificado. Ya puedes iniciar sesión."
                    } catch (error: CancellationException) {
                        throw error
                    } catch (error: Exception) {
                        verificationError = error.toApiUserMessage()
                    } finally {
                        verificationBusy = false
                    }
                }
            },
            onResend = {
                if (!verificationBusy) coroutineScope.launch {
                    verificationBusy = true
                    verificationError = null
                    verificationInfo = null
                    try {
                        authRepository.resendVerification(email)
                        verificationInfo = "Si la cuenta aún requiere verificación, enviamos un nuevo código."
                    } catch (error: CancellationException) {
                        throw error
                    } catch (error: Exception) {
                        verificationError = error.toApiUserMessage()
                    } finally {
                        verificationBusy = false
                    }
                }
            },
        )
    }

}



val serializersConfig = SavedStateConfiguration {
    serializersModule = SerializersModule {
        polymorphic(NavKey::class) {
            subclass(Routes.Splash::class, Routes.Splash.serializer())
            subclass(Routes.Login::class, Routes.Login.serializer())
            subclass(
                Routes.Register::class,
                Routes.Register.serializer()
            )
            subclass(Routes.ForggetPossward::class, Routes.ForggetPossward.serializer())
            subclass(Routes.Home::class, Routes.Home.serializer())
            subclass(Routes.ConsultarJornada::class, Routes.ConsultarJornada.serializer())
            subclass(Routes.JornadaCalendar::class, Routes.JornadaCalendar.serializer())
        }
    }
}

