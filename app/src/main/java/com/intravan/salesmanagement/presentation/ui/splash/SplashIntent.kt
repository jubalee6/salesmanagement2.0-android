package com.intravan.salesmanagement.presentation.ui.splash

import com.intravan.salesmanagement.presentation.model.SplashDisplayable

sealed class SplashIntent {

    data object BeginScreen : SplashIntent()
}