package com.intravan.salesmanagement.domain.datasource.remote

import com.intravan.salesmanagement.core.util.Resource
import com.intravan.salesmanagement.domain.model.Auth

interface AuthRemoteDataSource {

    // 인증번호 요청.
    suspend fun getAuthNumber(auth: Auth): Resource<Auth>

    // 인증번호 확인.
    suspend fun verifyAuth(auth: Auth): Resource<Auth>
}