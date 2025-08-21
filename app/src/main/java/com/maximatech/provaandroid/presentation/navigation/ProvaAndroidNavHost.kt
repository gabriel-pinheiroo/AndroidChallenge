package com.maximatech.provaandroid.presentation.navigation

import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navOptions
import com.maximatech.provaandroid.presentation.features.clientDetails.navigation.clientDetailsScreen
import com.maximatech.provaandroid.presentation.features.clientDetails.navigation.navigateToClientDetails
import com.maximatech.provaandroid.presentation.features.clients.navigation.clientsScreen
import com.maximatech.provaandroid.presentation.features.clients.navigation.navigateToClients
import com.maximatech.provaandroid.presentation.features.orders.navigation.ordersScreen
import com.maximatech.provaandroid.presentation.features.splash.navigation.splashScreen

@Composable
fun ProvaAndroidNavHost(
    modifier: Modifier = Modifier,
    navController: NavHostController = rememberNavController(),
    startDestination: Routes = Routes.Splash,
) {
    NavHost(
        modifier = modifier,
        navController = navController,
        startDestination = startDestination,
        enterTransition = { fadeIn() },
        exitTransition = { fadeOut() },
    ) {
        splashScreen(
            onSplashCompleted = {
                navController.navigateToClients(
                    navOptions = navOptions {
                        popUpTo(Routes.Splash) {
                            inclusive = true
                        }
                    }
                )
            }
        )

        clientsScreen(
            onNavigateToClientDetails = { clientName ->
                navController.navigateToClientDetails(
                    clientName = clientName
                )
            }
        )

        ordersScreen()

        clientDetailsScreen(
            onNavigateBack = navController::popBackStack
        )
    }
}