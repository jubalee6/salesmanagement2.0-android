package com.intravan.salesmanagement.data.datasource.remote

import com.intravan.salesmanagement.core.di.DefaultJson
import com.intravan.salesmanagement.core.extension.resourceOf
import com.intravan.salesmanagement.core.util.Resource
import com.intravan.salesmanagement.data.remote.api.IntravanApi
import com.intravan.salesmanagement.data.remote.request.GetStartingAuthRequest
import com.intravan.salesmanagement.domain.datasource.local.PreferencesLocalDataSource
import com.intravan.salesmanagement.domain.datasource.remote.SplashRemoteDataSource
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import javax.inject.Inject

class SplashRemoteDataSourceImpl @Inject constructor(
    @DefaultJson private val json: Json,
    private val intravanApi: IntravanApi,
    private val prefereneces: PreferencesLocalDataSource
) : SplashRemoteDataSource {

    // 사용확인.
    override suspend fun beginScreen(): Resource<Boolean> {
        // Request
        val request = GetStartingAuthRequest(
            uuid = prefereneces.uuid,
            code = prefereneces.code
        ).let {
            json.encodeToString(it)
        }

        // API.
        intravanApi
            .getStarting(request)
            .let {
                resourceOf(it.isResult, it.message) {
                    it.isResult
                }
            }
            .run {
                return this
            }
    }
}
/*

// API.
intravanApi
.getStarting(request)
.let {
    resourceOf(it.isResult, it.message) {
        it.isResult
    }
}
.run {
    return this
}
}*/
