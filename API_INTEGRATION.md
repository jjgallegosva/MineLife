# Integración de MineLife API

La app comparte un cliente Ktor desde `commonMain`. La base configurada es `https://mineone.sao.dom.my.id`; el cliente agrega `/api/v1`, por lo que las llamadas van a `https://mineone.sao.dom.my.id/api/v1/...`. Android permite HTTP solo en `debug`; `release` conserva tráfico claro desactivado. Para volver al backend local en desarrollo, cambia `apiBaseUrl()` en los adaptadores de plataforma y usa el emulador Android (`http://10.0.2.2:3000`) o el simulador iOS (`http://127.0.0.1:3000`).

## Capas y flujos conectados

- `core/network/MineLifeApi.kt`: transporte Ktor compartido, serialización JSON, timeout, cabecera Bearer y traducción del error `{ title, code, requestId }`.
- `AuthRepository`: login, registro, perfil propio, refresh con exclusión mutua, logout, logout global, verificación/reenvío de correo, recuperación y cambio de contraseña.
- `Navigation.kt`: login por correo contra `/auth/login` y por Google contra `/auth/google`, perfil `/users/me`, registro por correo contra `/auth/register`, diálogo para validar el código de 8 dígitos mediante `/auth/verify-email`, reenvío mediante `/auth/resend-verification` y logout contra `/auth/logout`.
- Login devuelve `accessToken`, `refreshToken`, `tokenType` y `expiresIn`; el repositorio conserva el par únicamente en memoria. No se persisten credenciales hasta añadir implementaciones nativas de Keychain y Android Keystore.

## Contrato y cobertura pendiente

Se revisaron `minelifeApi/openapi.yaml` y los handlers de Next.js. La verificación de correo usa un código numérico de 8 dígitos con vencimiento en 30 minutos, enviado en el campo JSON `code`. Los códigos se guardan con HMAC usando el secreto del backend, tienen un solo uso y el endpoint limita intentos por IP. La pantalla de recuperación de contraseña sigue pendiente; las pantallas del calendario y de jornada aún no conectan `/schedules` ni `/calendar/events`.

Google Sign-In está conectado en Android: la app obtiene el Google ID token y lo envía a MineLife API, que lo valida y crea o vincula la cuenta con una identidad Google persistida en la base de datos. El backend necesita `GOOGLE_CLIENT_ID` igual al OAuth Web client ID de `google-services.json`, y producción debe aplicar `npx prisma migrate deploy` antes del despliegue. Facebook y el cliente Google de iOS todavía no están implementados. El OpenAPI documenta algunas rutas pero omite esquemas detallados de respuesta y de petición para perfil, horarios y eventos; antes de conectar esos módulos, mantén como referencia los handlers y modelos Prisma junto con el OpenAPI.

El backend actualizado debe desplegarse antes de usar la verificación nueva desde la app. Las cuentas que recibieron el enlace anterior pueden solicitar un código nuevo desde el endpoint de reenvío.

## Entorno

La app apunta ahora al host HTTPS publicado. Para desarrollo local, inicia `minelifeApi` en el puerto 3000 y usa los hosts de loopback específicos de cada simulador. Un dispositivo físico requiere una dirección de red alcanzable; no se ha agregado una excepción TLS.
