package com.maximatech.provaandroid.presentation.designSystem.components.topBar

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue

class TopBarManager {

    var shouldShowTopBar by mutableStateOf(false)
        private set

    var config by mutableStateOf(TopBarConfig())
        private set

    fun setTopBarConfig(block: TopBarConfig.() -> Unit) {
        config = TopBarConfig()
        config.block()
    }

    fun showTopBar() {
        shouldShowTopBar = true
    }

    fun hideTopBar() {
        shouldShowTopBar = false
    }
}