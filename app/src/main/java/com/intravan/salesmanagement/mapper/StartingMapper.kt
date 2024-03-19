package com.intravan.salesmanagement.mapper

import com.intravan.salesmanagement.domain.model.Starting
import com.intravan.salesmanagement.presentation.model.SplashDisplayable

fun Starting.toPresentationModel() = SplashDisplayable(
    isAuthenticated = isAuthenticated
)