package com.maximatech.provaandroid

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import com.maximatech.provaandroid.presentation.designSystem.components.bottomBar.BottomBarMenuItem
import com.maximatech.provaandroid.presentation.designSystem.components.bottomBar.BottomBarManager
import com.maximatech.provaandroid.presentation.designSystem.components.bottomBar.ProvaAndroidBottomBar
import com.maximatech.provaandroid.presentation.designSystem.components.topBar.ProvaAndroidTopBar
import com.maximatech.provaandroid.presentation.designSystem.components.topBar.TopBarManager
import com.maximatech.provaandroid.presentation.navigation.ProvaAndroidNavHost
import com.maximatech.provaandroid.presentation.navigation.Routes
import com.maximatech.provaandroid.presentation.navigation.navigateToTopLevelDestination

val LocalTopBarManager = staticCompositionLocalOf<TopBarManager> {
    error("No TopBarManager provided")
}

val LocalBottomBarManager = staticCompositionLocalOf<BottomBarManager> {
    error("No BottomBarManager provided")
}

@Composable
fun ProvaAndroidApp(
    state: ProvaAndroidAppState,
    navController: NavHostController,
    onMenuSelected: (BottomBarMenuItem) -> Unit,
) {
    val topBarManager = TopBarManager()
    val bottomBarManager = BottomBarManager()

    CompositionLocalProvider(
        LocalTopBarManager provides topBarManager,
        LocalBottomBarManager provides bottomBarManager,
    ) {
        Surface(
            modifier = Modifier.fillMaxSize(),
            color = MaterialTheme.colorScheme.background
        ) {
            Scaffold(
                modifier = Modifier.fillMaxSize(),
                topBar = {
                    if (topBarManager.shouldShowTopBar) {
                        ProvaAndroidTopBar(
                            config = topBarManager.config,
                        )
                    }
                },
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
}
