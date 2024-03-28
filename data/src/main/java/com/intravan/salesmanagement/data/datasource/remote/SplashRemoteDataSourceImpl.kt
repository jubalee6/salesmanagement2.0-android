package com.intravan.salesmanagement.data.datasource.remote

import com.intravan.salesmanagement.core.di.DefaultJson
import com.intravan.salesmanagement.core.extension.resourceOf
import com.intravan.salesmanagement.core.util.Resource
import com.intravan.salesmanagement.data.mapper.toDomainModel
import com.intravan.salesmanagement.data.remote.api.IntravanApi
import com.intravan.salesmanagement.data.remote.request.GetStartingAuthRequest
import com.intravan.salesmanagement.domain.datasource.local.PreferencesLocalDataSource
import com.intravan.salesmanagement.domain.datasource.remote.SplashRemoteDataSource
import com.intravan.salesmanagement.domain.model.Splash
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import javax.inject.Inject

class SplashRemoteDataSourceImpl @Inject constructor(
    @DefaultJson private val json: Json,
    private val intravanApi: IntravanApi,
    private val prefereneces: PreferencesLocalDataSource
): SplashRemoteDataSource{

    // 사용확인.
    override suspend fun beginScreen(splash: Splash): Resource<Splash> {

        // Request
        val request = GetStartingAuthRequest(
            uuid = prefereneces.uuid,
            code = splash.code
        )
            .let {
                json.encodeToString(it)
            }
        intravanApi
            .getStarting(request)
            .let {
                if(it.isResult) {
                    prefereneces.isAuthenticated = true
                }
                resourceOf(it.isResult,it.message){
                    it.toDomainModel(splash)
                }
            }
            .run {
                return this
            }
    }
}