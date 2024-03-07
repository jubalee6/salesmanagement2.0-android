package com.intravan.salesmanagement.domain.repository

import com.intravan.salesmanagement.core.util.Resource
import com.intravan.salesmanagement.domain.model.Auth
import kotlinx.coroutines.flow.Flow

interface AuthRepository {

    // 인증번호 요청.
    fun getAuthNumber(auth: Auth): Flow<Resource<Auth>>

    // 인증번호 확인.
    fun verifyAuth(auth: Auth): Flow<Resource<Auth>>


}