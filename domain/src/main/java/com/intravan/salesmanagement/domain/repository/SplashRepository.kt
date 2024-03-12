package com.intravan.salesmanagement.domain.repository

import kotlinx.coroutines.flow.Flow

interface SplashRepository {

    // 화면시작.
    fun beginScreen(): Flow<Boolean>
}