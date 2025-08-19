package com.maximatech.provaandroid

import androidx.compose.runtime.Composable
import androidx.compose.runtime.Stable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.maximatech.provaandroid.presentation.navigation.Routes

@Composable
fun rememberProvaAndroidAppState(
    navController: NavController = rememberNavController()
) = remember(
    navController
) {
    ProvaAndroidAppState(
        navController = navController
    )
}

@Stable
class ProvaAndroidAppState(
    val navController: NavController,
) {
    var isBottomBarEnabled by mutableStateOf(true)

    private val fullScreenDestinations: Set<String> = setOf(
        Routes.Splash::class.java.canonicalName.orEmpty(),
    )

    init {
        navController.addOnDestinationChangedListener { _, destination, _ ->
            isBottomBarEnabled = destination.route !in fullScreenDestinations
        }
    }
}

