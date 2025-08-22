package com.maximatech.provaandroid.presentation.features.splash

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.maximatech.provaandroid.R
import com.maximatech.provaandroid.presentation.designSystem.tokens.AppColors
import com.maximatech.provaandroid.presentation.designSystem.tokens.*
import com.maximatech.provaandroid.presentation.theme.ProvaAndroidTheme
import kotlinx.coroutines.delay

@Composable
fun SplashRoute(
    modifier: Modifier = Modifier,
    onSplashCompleted: () -> Unit = {}
) {
    LaunchedEffect(Unit) {
        delay(2000)
        onSplashCompleted()
    }

    SplashScreen(modifier = modifier)
}

@Composable
private fun SplashScreen(
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .fillMaxSize()
            .background(
                brush = Brush.verticalGradient(
                    colors = listOf(
                        AppColors.SplashGradientStart,
                        AppColors.SplashGradientEnd
                    )
                )
            ),
        contentAlignment = Alignment.Center
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Image(
                painter = painterResource(id = R.drawable.maxima_logotipo),
                contentDescription = stringResource(R.string.logo_maxima),
                modifier = Modifier.size(LogoSize)
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun SplashScreenPreview() {
    ProvaAndroidTheme {
        SplashScreen()
    }
}