package com.maximatech.provaandroid.app

import androidx.compose.runtime.Composable
import androidx.compose.runtime.Stable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.maximatech.provaandroid.presentation.designSystem.components.bottomBar.BottomBarMenuItem
import com.maximatech.provaandroid.presentation.navigation.Routes

@Composable
fun rememberProvaAndroidAppState(
    menu: Set<BottomBarMenuItem> = setOf(),
    navController: NavController = rememberNavController()
) = remember(
    menu,
    navController
) {
    ProvaAndroidAppState(
        menu = menu,
        navController = navController
    )
}

@Stable
class ProvaAndroidAppState(
    val menu: Set<BottomBarMenuItem>,
    val navController: NavController,
) {
    var isBottomBarEnabled by mutableStateOf(true)

    private val fullScreenDestinations: Set<String> = setOf(
        Routes.Splash::class.java.canonicalName.orEmpty(),
        Routes.ClientDetails::class.java.canonicalName.orEmpty().parameterizedDestination(),
    )

    init {
        navController.addOnDestinationChangedListener { _, destination, _ ->
            isBottomBarEnabled = destination.route !in fullScreenDestinations
        }
    }
}

private fun String.parameterizedDestination() = this.plus("/{args}")
