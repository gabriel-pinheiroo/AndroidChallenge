package com.maximatech.provaandroid

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.navigation.compose.rememberNavController
import com.maximatech.provaandroid.presentation.theme.ProvaAndroidTheme
import org.koin.androidx.viewmodel.ext.android.viewModel

class MainActivity : ComponentActivity() {

    private val viewModel by viewModel<MainActivityViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val navController = rememberNavController()
            val appState = rememberProvaAndroidAppState(
                navController = navController
            )
            ProvaAndroidTheme {
                ProvaAndroidApp(
                    state = appState,
                    navController = navController,
                )
            }
        }
    }
}