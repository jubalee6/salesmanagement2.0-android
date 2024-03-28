package com.intravan.salesmanagement.data.repository

import com.intravan.salesmanagement.core.util.Resource
import com.intravan.salesmanagement.domain.datasource.local.AuthLocalDataSource
import com.intravan.salesmanagement.domain.datasource.remote.AuthRemoteDataSource
import com.intravan.salesmanagement.domain.model.Auth
import com.intravan.salesmanagement.domain.repository.AuthRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class AuthRepositoryImpl @Inject constructor(
    private val authRemoteDataSource: AuthRemoteDataSource,
    private val authLocalDataSource: AuthLocalDataSource
) : AuthRepository {

    // 인증번호 요청.
    override fun getAuthNumber(auth: Auth): Flow<Resource<Auth>> = flow {
        authRemoteDataSource.getAuthNumber(auth).run {
            emit(this)
        }
    }

    // 인증 확인.
    override fun verifyAuth(code:String) {
        authLocalDataSource
            .authenticated(code)
    }
}