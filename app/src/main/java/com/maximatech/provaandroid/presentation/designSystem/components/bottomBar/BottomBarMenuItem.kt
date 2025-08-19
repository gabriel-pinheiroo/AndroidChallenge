package com.maximatech.provaandroid.presentation.designSystem.components.bottomBar

import androidx.annotation.DrawableRes

data class BottomBarMenuItem(
    val name: String = "",
    val route: String = "",
    @DrawableRes val activeIcon: Int? = null,
    @DrawableRes val inactiveIcon: Int? = null,
    val isSelected: Boolean = false
)