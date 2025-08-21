package com.maximatech.provaandroid.presentation.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import com.maximatech.provaandroid.presentation.designSystem.tokens.AppColors

private val AppColorScheme = lightColorScheme(
    primary = AppColors.Primary,
    onPrimary = AppColors.OnPrimary,
    secondary = AppColors.Secondary,
    onSecondary = AppColors.OnSecondary,
    background = AppColors.Background,
    onBackground = AppColors.OnSurfaceHigh,
    surface = AppColors.Surface,
    onSurface = AppColors.OnSurfaceHigh,
    error = AppColors.Error,
    onError = AppColors.OnPrimary
)

@Composable
fun ProvaAndroidTheme(
    content: @Composable () -> Unit
) {

    MaterialTheme(
        colorScheme = AppColorScheme,
        typography = Typography,
        content = content
    )
}