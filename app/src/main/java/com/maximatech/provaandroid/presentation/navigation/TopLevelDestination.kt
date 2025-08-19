package com.maximatech.provaandroid.presentation.navigation

import android.annotation.SuppressLint
import androidx.navigation.NavController
import androidx.navigation.navOptions
import com.maximatech.provaandroid.presentation.features.clients.navigation.navigateToClients
import com.maximatech.provaandroid.presentation.features.orders.navigation.navigateToOrders

interface TopLevelDestination

@SuppressLint("RestrictedApi")
fun <T : TopLevelDestination> NavController.navigateToTopLevelDestination(
    topLevelDestination: T
) {
    currentDestination?.let { route ->
        if (route.route == topLevelDestination::class.java.canonicalName.orEmpty()) return
    }

    val topLevelNavOptions = navOptions {
        popUpTo(Routes.Clients::class) {
            saveState = true
            inclusive = false
        }
        launchSingleTop = true
        restoreState = true
    }

    when (topLevelDestination) {
        is Routes.Clients -> navigateToClients(topLevelNavOptions)
        is Routes.Orders -> navigateToOrders(topLevelNavOptions)
    }
}