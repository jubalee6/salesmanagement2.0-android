package com.intravan.salesmanagement.domain.repository

import com.intravan.salesmanagement.core.util.Resource
import com.intravan.salesmanagement.domain.model.Splash
import kotlinx.coroutines.flow.Flow

interface SplashRepository {

    // 화면시작.
    fun beginScreen(splash: Splash): Flow<Resource<Splash>>
}