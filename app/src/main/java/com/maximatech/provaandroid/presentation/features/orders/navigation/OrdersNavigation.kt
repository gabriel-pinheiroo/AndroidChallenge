package com.maximatech.provaandroid.presentation.features.orders.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import com.maximatech.provaandroid.presentation.features.orders.OrdersRoute
import com.maximatech.provaandroid.presentation.navigation.Routes

fun NavController.navigateToOrders(
    navOptions: NavOptions? = null
) = navigate(route = Routes.Orders, navOptions)

fun NavGraphBuilder.ordersScreen(
) {
    composable<Routes.Orders> {
        OrdersRoute(
        )
    }
}