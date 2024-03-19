package com.intravan.salesmanagement.presentation.ui.splash

sealed class SplashIntent {

    data object BeginScreen : SplashIntent()

    data object Starting : SplashIntent()

}