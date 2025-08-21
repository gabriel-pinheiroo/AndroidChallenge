package com.maximatech.provaandroid.presentation.features.splash.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.maximatech.provaandroid.presentation.features.splash.SplashRoute
import com.maximatech.provaandroid.presentation.navigation.Routes

fun NavGraphBuilder.splashScreen(
    onSplashCompleted: () -> Unit = {},
) {
    composable<Routes.Splash> {
        SplashRoute(
            onSplashCompleted = onSplashCompleted
        )
    }
}