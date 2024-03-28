package com.intravan.salesmanagement.data.mapper

import com.intravan.salesmanagement.data.remote.response.StartingAuthResponse
import com.intravan.salesmanagement.domain.model.Splash

fun StartingAuthResponse.toDomainModel(splash:Splash) = splash.copy()