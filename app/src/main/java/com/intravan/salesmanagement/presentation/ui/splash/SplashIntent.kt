package com.intravan.salesmanagement.presentation.ui.splash

import com.intravan.salesmanagement.presentation.model.SplashDisplayable

sealed class SplashIntent {

    data class BeginScreen(val display:SplashDisplayable) : SplashIntent()


}