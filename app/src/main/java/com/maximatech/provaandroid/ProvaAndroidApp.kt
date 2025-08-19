package com.maximatech.provaandroid

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.maximatech.provaandroid.presentation.theme.ProvaAndroidTheme

@Composable
fun ProvaAndroidApp() {
    Surface(
        modifier = Modifier.fillMaxSize(),
        color = MaterialTheme.colorScheme.background
    ) {
        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Text(
                text = "Hello, World!",
                style = MaterialTheme.typography.headlineLarge
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun ProvaAndroidAppPreview() {
    ProvaAndroidTheme {
        ProvaAndroidApp()
    }
}