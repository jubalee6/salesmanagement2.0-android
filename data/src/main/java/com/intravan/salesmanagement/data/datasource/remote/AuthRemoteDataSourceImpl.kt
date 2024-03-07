package com.intravan.salesmanagement.data.datasource.remote

import com.intravan.salesmanagement.core.di.DefaultJson
import com.intravan.salesmanagement.core.extension.resourceOf
import com.intravan.salesmanagement.core.extension.resultOf
import com.intravan.salesmanagement.core.util.Resource
import com.intravan.salesmanagement.data.mapper.toDomainModel
import com.intravan.salesmanagement.data.mapper.toSendAuthNumberRequest
import com.intravan.salesmanagement.data.remote.api.IntravanApi
import com.intravan.salesmanagement.data.remote.request.GetAuthNumberRequest
import com.intravan.salesmanagement.domain.datasource.local.PreferencesLocalDataSource
import com.intravan.salesmanagement.domain.datasource.remote.AuthRemoteDataSource
import com.intravan.salesmanagement.domain.model.Auth
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import javax.inject.Inject

class AuthRemoteDataSourceImpl @Inject constructor(
    @DefaultJson private val json: Json,
    private val intravanApi: IntravanApi,
    private val preferences: PreferencesLocalDataSource
) : AuthRemoteDataSource {

    // 인증번호 요청.
    override suspend fun getAuthNumber(auth: Auth): Resource<Auth> {
        val request = GetAuthNumberRequest(
            mobileNumber = auth.mobileNumber,
            uuid = preferences.uuid
        ).let {
            json.encodeToString(it)
        }
        intravanApi
            .getAuthNumber(request)
            .let {
                resourceOf(it.isResult, it.message) {
                    it.toDomainModel(auth)
                }
            }
            .run {
                return this
            }
    }

    // 인증번호 확인.
    override suspend fun verifyAuth(auth: Auth): Resource<Auth> {
        TODO("Not yet implemented")
    }
}