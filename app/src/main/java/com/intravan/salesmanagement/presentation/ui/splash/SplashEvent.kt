package com.intravan.salesmanagement.presentation.ui.splash

sealed class SplashEvent {

    data class StartingFailed(val failedMessage: String) : SplashEvent()

    data object NavigateToAuth : SplashEvent()

    data object NavigateToMain : SplashEvent()
}