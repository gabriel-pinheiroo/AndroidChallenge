package com.maximatech.provaandroid.presentation.navigation

import kotlinx.serialization.Serializable

@Serializable
sealed interface Routes {

    @Serializable
    data object Clients : Routes, TopLevelDestination

    @Serializable
    data object Orders : Routes, TopLevelDestination

    @Serializable
    data object Splash : Routes
}