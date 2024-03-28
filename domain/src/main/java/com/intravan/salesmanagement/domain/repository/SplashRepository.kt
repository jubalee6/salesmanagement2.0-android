package com.intravan.salesmanagement.domain.repository

import com.intravan.salesmanagement.core.util.Resource
import kotlinx.coroutines.flow.Flow

interface SplashRepository {

    // 화면시작.
    fun beginScreen(): Flow<Resource<Boolean>>
}