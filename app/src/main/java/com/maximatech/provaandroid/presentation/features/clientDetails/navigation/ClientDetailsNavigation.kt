package com.maximatech.provaandroid.presentation.features.clientDetails.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import com.maximatech.provaandroid.presentation.features.clientDetails.ClientDetailsRoute
import com.maximatech.provaandroid.presentation.navigation.Routes

fun NavController.navigateToClientDetails(
    navOptions: NavOptions? = null
) = navigate(route = Routes.ClientDetails, navOptions)

fun NavGraphBuilder.clientDetailsScreen(
    onNavigateBack: () -> Unit
) {
    composable<Routes.ClientDetails> {
        ClientDetailsRoute(
            onNavigateBack = onNavigateBack
        )
    }
}