package com.maximatech.provaandroid.presentation.features.clientDetails.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import androidx.navigation.toRoute
import com.maximatech.provaandroid.presentation.features.clientDetails.ClientDetailsRoute
import com.maximatech.provaandroid.presentation.navigation.Routes

fun NavController.navigateToClientDetails(
    navOptions: NavOptions? = null,
    clientName: String = "",
) = navigate(route = Routes.ClientDetails(args = clientName), navOptions)

fun NavGraphBuilder.clientDetailsScreen(
    onNavigateBack: () -> Unit,
) {
    composable<Routes.ClientDetails> { backStackEntry ->
        val clientDetails: Routes.ClientDetails = backStackEntry.toRoute()

        ClientDetailsRoute(
            onNavigateBack = onNavigateBack,
            clientName = clientDetails.args
        )
    }
}