package com.intravan.salesmanagement.domain.datasource.remote

import com.intravan.salesmanagement.core.util.Resource

interface SplashRemoteDataSource {

    // 초기정보.
    suspend fun beginScreen(): Resource<Boolean>
}