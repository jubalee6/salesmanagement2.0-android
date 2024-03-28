package com.intravan.salesmanagement.mapper

import com.intravan.salesmanagement.domain.model.Splash
import com.intravan.salesmanagement.presentation.model.SplashDisplayable

fun SplashDisplayable.toDomainModel() = Splash(
    isAuthenticated = isAuthenticated,
    code = code,
    uuid = uuid
)

fun Splash.toPresentationModel() = SplashDisplayable(
    isAuthenticated = isAuthenticated,
    code = code,
    uuid = uuid
)