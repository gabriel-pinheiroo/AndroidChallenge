package com.maximatech.provaandroid.presentation.navigation

import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import com.maximatech.provaandroid.presentation.designSystem.components.bottomBar.BottomBarMenuItem
import com.maximatech.provaandroid.presentation.features.clients.navigation.clientsScreen
import com.maximatech.provaandroid.presentation.features.orders.navigation.ordersScreen

@Composable
fun ProvaAndroidNavHost(
    modifier: Modifier = Modifier,
    navController: NavHostController = rememberNavController(),
    paddingValues: PaddingValues = PaddingValues(),
    onMenuSelected: (BottomBarMenuItem) -> Unit = {},
    startDestination: Routes = Routes.Clients,
) {
    NavHost(
        navController = navController,
        startDestination = startDestination,
        modifier = modifier.padding(paddingValues),
        enterTransition = { fadeIn() },
        exitTransition = { fadeOut() },
    ) {

        clientsScreen()

        ordersScreen()
    }
}