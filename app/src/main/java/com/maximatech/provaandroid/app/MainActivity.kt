package com.maximatech.provaandroid.app

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.getValue
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.compose.rememberNavController
import com.maximatech.provaandroid.presentation.theme.ProvaAndroidTheme
import org.koin.androidx.viewmodel.ext.android.viewModel

class MainActivity : ComponentActivity() {

    private val viewModel by viewModel<MainActivityViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val navController = rememberNavController()
            val menu by viewModel.menu.collectAsStateWithLifecycle()

            val appState = rememberProvaAndroidAppState(
                menu = menu,
                navController = navController
            )

            ProvaAndroidTheme {
                ProvaAndroidApp(
                    state = appState,
                    navController = navController,
                    onMenuSelected = { menuItem ->
                        viewModel.onMenuSelected(menuItem)
                    }
                )
            }
        }
    }
}