package com.maximatech.provaandroid.presentation.features.clients.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import com.maximatech.provaandroid.presentation.features.clients.ClientsRoute
import com.maximatech.provaandroid.presentation.navigation.Routes

fun NavController.navigateToClients(
    navOptions: NavOptions? = null
) = navigate(route = Routes.Clients, navOptions)

fun NavGraphBuilder.clientsScreen(
    onNavigateToClientDetails: () -> Unit = {},
) {
    composable<Routes.Clients> {
        ClientsRoute(
            navigateToClientDetails = onNavigateToClientDetails
        )
    }
}