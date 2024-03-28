package com.intravan.salesmanagement.domain.datasource.remote

import com.intravan.salesmanagement.core.util.Resource
import com.intravan.salesmanagement.domain.model.Splash

interface SplashRemoteDataSource {

    // 초기정보.
    suspend fun beginScreen(splash: Splash): Resource<Splash>
}