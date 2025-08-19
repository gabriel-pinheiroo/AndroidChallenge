package com.maximatech.provaandroid.presentation.designSystem.tokens

import androidx.compose.runtime.Stable
import androidx.compose.ui.graphics.Color
import androidx.core.graphics.toColorInt

fun String.toColor() = Color(color = this.toColorInt())

@Stable
val Color.Companion.AppBackground
    get() = "#FFFFFF".toColor()

@Stable
val Color.Companion.AppToolbar
    get() = "#186096".toColor()

@Stable
val Color.Companion.AppStatusBar
    get() = "#00386C".toColor()

@Stable
val Color.Companion.AppCardBackground
    get() = "#F1F1F1".toColor()

@Stable
val Color.Companion.AppCardContent
    get() = "#FFFFFF".toColor()

@Stable
val Color.Companion.AppBottomNavigation
    get() = "#186096".toColor()

@Stable
val Color.Companion.AppBottomNavigationActive
    get() = "#FFFFFF".toColor()

@Stable
val Color.Companion.AppBottomNavigationInactive
    get() = "#95B6CF".toColor()

@Stable
val Color.Companion.SplashBackground
    get() = "#186096".toColor()

@Stable
val Color.Companion.ClientText
    get() = "#000000".toColor()

@Stable
val Color.Companion.ClientTextLabels
    get() = "#95989A".toColor()

@Stable
val Color.Companion.ClientSocialReason
    get() = "#606060".toColor()

@Stable
val Color.Companion.ClientPhoneButton
    get() = "#186096".toColor()

@Stable
val Color.Companion.ClientEmailButton
    get() = "#C1392B".toColor()

@Stable
val Color.Companion.ClientVerifyStatusButton
    get() = "#638735".toColor()

@Stable
val Color.Companion.OrderHistoryText
    get() = "#000000".toColor()

@Stable
val Color.Companion.OrderHistoryLabels
    get() = "#95989A".toColor()

@Stable
val Color.Companion.OrderStatusProcessing
    get() = "#95989A".toColor()

@Stable
val Color.Companion.OrderStatusRejected
    get() = "#FF9900".toColor()

@Stable
val Color.Companion.OrderStatusPending
    get() = "#606060".toColor()

@Stable
val Color.Companion.OrderStatusBlocked
    get() = "#3557AA".toColor()

@Stable
val Color.Companion.OrderStatusReleased
    get() = "#186096".toColor()

@Stable
val Color.Companion.OrderStatusMounted
    get() = "#7FAA33".toColor()

@Stable
val Color.Companion.OrderStatusInvoiced
    get() = "#648638".toColor()

@Stable
val Color.Companion.OrderStatusCanceled
    get() = "#F40613".toColor()

@Stable
val Color.Companion.OrderStatusBudget
    get() = "#2D3E4E".toColor()

@Stable
val Color.Companion.CriticTypeWaitingReturn
    get() = "#757575".toColor()

@Stable
val Color.Companion.CriticTypeSuccess
    get() = "#648638".toColor()

@Stable
val Color.Companion.CriticTypePartialFailure
    get() = "#FF9900".toColor()

@Stable
val Color.Companion.CriticTypeTotalFailure
    get() = "#F40613".toColor()

@Stable
val Color.Companion.LegendCutOrder
    get() = "#FF9900".toColor()

@Stable
val Color.Companion.LegendMissingOrder
    get() = "#BF595F".toColor()

@Stable
val Color.Companion.LegendCanceledErp
    get() = "#F40613".toColor()

@Stable
val Color.Companion.LegendWithDevolution
    get() = "#186096".toColor()

@Stable
val Color.Companion.LegendTelemarketing
    get() = "#648638".toColor()