package com.example.aplicacionvariada.core.presentation

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import aplicacionvariada.composeapp.generated.resources.Res
import aplicacionvariada.composeapp.generated.resources.logo
import aplicacionvariada.composeapp.generated.resources.logo2
import kotlinx.coroutines.delay
import org.jetbrains.compose.resources.painterResource
@Composable
fun SplashContent(
    onTimeOut: () -> Unit,
) {
    val splashTime = 1000L

    LaunchedEffect(Unit) {
        delay(splashTime)
        onTimeOut()
    }
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.primary),
        contentAlignment = Alignment.Center,
    ) {
        Image(
            painter = painterResource(Res.drawable.logo2),
            contentDescription = "Logo",
            modifier = Modifier.size(200.dp),
            contentScale = ContentScale.Fit,
        )
    }
}

@Preview
@Composable
fun SplashContentPreview() {
    SplashContent { }
}


