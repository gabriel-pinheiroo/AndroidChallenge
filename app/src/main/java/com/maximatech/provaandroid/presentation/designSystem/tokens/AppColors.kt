package com.maximatech.provaandroid.presentation.designSystem.tokens

import androidx.compose.ui.graphics.Color

/**
 * Cores principais da aplicação
 */
object AppColors {

    // ==================== BACKGROUND ====================
    /**
     * A cor de fundo para todas as telas.
     */
    val Background: Color = Color.AppBackground

    /**
     * A cor da superfície de componentes, como cards, sheets, menus, etc.
     */
    val Surface: Color = Color.AppCardContent

    /**
     * Fundo da tela que tiver cards
     */
    val CardBackground: Color = Color.AppCardBackground

    // ==================== TEXT ====================
    /**
     * A cor das fontes para títulos e destaques.
     */
    val OnSurfaceHigh: Color = Color.ClientText

    /**
     * A cor das fontes para parágrafos e texto regular.
     */
    val OnSurfaceMedium: Color = Color.ClientSocialReason

    /**
     * A cor das fontes para textos menos importantes como datas.
     */
    val OnSurfaceLight: Color = Color.ClientTextLabels

    // ==================== PRIMARY ====================
    /**
     * A cor para todos os componentes importantes como botões, ícones, etc.
     */
    val Primary: Color = Color.AppToolbar

    /**
     * A cor das fontes para componentes importantes.
     */
    val OnPrimary: Color = Color.AppBottomNavigationActive

    /**
     * Status bar color
     */
    val StatusBar: Color = Color.AppStatusBar

    // ==================== SECONDARY ====================
    /**
     * Cor secundária da aplicação
     */
    val Secondary: Color = Color.AppBottomNavigationInactive

    /**
     * Texto em componentes secundários
     */
    val OnSecondary: Color = Color.ClientText

    // ==================== BOTTOM NAVIGATION ====================
    /**
     * Fundo do bottom navigation
     */
    val BottomNavigation: Color = Color.AppBottomNavigation

    /**
     * Ícone ativo do bottom navigation
     */
    val BottomNavigationActive: Color = Color.AppBottomNavigationActive

    /**
     * Ícone inativo do bottom navigation
     */
    val BottomNavigationInactive: Color = Color.AppBottomNavigationInactive

    // ==================== STATUS COLORS ====================
    /**
     * A cor para componentes de sucesso.
     */
    val Success: Color = Color.OrderStatusMounted

    /**
     * A cor para componentes de aviso.
     */
    val Warning: Color = Color.OrderStatusRejected

    /**
     * A cor para componentes de erro.
     */
    val Error: Color = Color.OrderStatusCanceled

    /**
     * A cor para componentes informativos.
     */
    val Info: Color = Color.AppToolbar

    // ==================== ACTIONS ====================
    /**
     * Cor para botão de telefone
     */
    val PhoneAction: Color = Color.ClientPhoneButton

    /**
     * Cor para botão de email
     */
    val EmailAction: Color = Color.ClientEmailButton

    /**
     * Cor para botão de verificar status
     */
    val VerifyAction: Color = Color.ClientVerifyStatusButton

    // ==================== SPLASH ====================
    /**
     * Cor de fundo do splash
     */
    val SplashBackground: Color = Color.SplashBackground

    /**
     * Cor inicial do gradiente da splash screen (azul mais claro)
     */
    val SplashGradientStart: Color = Color.SplashGradientStart

    /**
     * Cor final do gradiente da splash screen (azul mais escuro)
     */
    val SplashGradientEnd: Color = Color.SplashGradientEnd
}