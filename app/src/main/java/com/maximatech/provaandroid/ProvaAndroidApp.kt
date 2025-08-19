package com.maximatech.provaandroid

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import com.maximatech.provaandroid.presentation.designSystem.components.bottomBar.BottomBarMenuItem
import com.maximatech.provaandroid.presentation.designSystem.components.bottomBar.ProvaAndroidBottomBar
import com.maximatech.provaandroid.presentation.navigation.ProvaAndroidNavHost
import com.maximatech.provaandroid.presentation.navigation.Routes
import com.maximatech.provaandroid.presentation.navigation.navigateToTopLevelDestination

@Composable
fun ProvaAndroidApp(
    state: ProvaAndroidAppState,
    navController: NavHostController,
    onMenuSelected: (BottomBarMenuItem) -> Unit,
) {
    Surface(
        modifier = Modifier.fillMaxSize(),
        color = MaterialTheme.colorScheme.background
    ) {
        Scaffold(
            modifier = Modifier.fillMaxSize(),
            bottomBar = {
                if (state.isBottomBarEnabled) {
                    ProvaAndroidBottomBar(
                        menu = state.menu,
                        onItemClicked = { item ->
                            onMenuSelected(item)
                            when (item.route) {
                                "clients" -> navController.navigateToTopLevelDestination(Routes.Clients)
                                "orders" -> navController.navigateToTopLevelDestination(Routes.Orders)
                            }
                        }
                    )
                }
            }
        ) { paddingValues ->
            ProvaAndroidNavHost(
                paddingValues = paddingValues,
                navController = navController,
                onMenuSelected = onMenuSelected
            )
        }
    }
}
