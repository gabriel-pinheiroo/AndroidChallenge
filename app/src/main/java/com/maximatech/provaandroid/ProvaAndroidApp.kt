package com.maximatech.provaandroid

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import com.maximatech.provaandroid.presentation.navigation.ProvaAndroidNavHost

@Composable
fun ProvaAndroidApp(
    state: ProvaAndroidAppState,
    navController: NavHostController,
) {

    Surface(
        modifier = Modifier.fillMaxSize(),
        color = MaterialTheme.colorScheme.background
    ) {
        Scaffold(
            modifier = Modifier.fillMaxSize()
        ) { paddingValues ->
            ProvaAndroidNavHost(
                paddingValues = paddingValues,
                navController = navController,
            )
        }
    }
}
