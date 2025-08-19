package com.maximatech.provaandroid.presentation.designSystem.components.topBar

data class TopBarConfig(
    var title: String = "",
    var onBackClicked: () -> Unit = {},
    var onMenuClicked: () -> Unit = {},
    var showBackButton: Boolean = false,
    var showTitle: Boolean = false,
    var showMenuButton: Boolean = false,
)