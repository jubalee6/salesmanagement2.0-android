package com.intravan.salesmanagement.data.datasource.remote

import com.intravan.salesmanagement.core.di.DefaultJson
import com.intravan.salesmanagement.core.extension.resourceOf
import com.intravan.salesmanagement.core.util.Resource
import com.intravan.salesmanagement.data.mapper.toDomainModel
import com.intravan.salesmanagement.data.remote.api.IntravanApi
import com.intravan.salesmanagement.data.remote.request.GetCompanyRequest
import com.intravan.salesmanagement.domain.datasource.local.PreferencesLocalDataSource
import com.intravan.salesmanagement.domain.datasource.remote.CompanyRemoteDataSource
import com.intravan.salesmanagement.domain.model.Company
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import javax.inject.Inject

class CompanyRemoteDataSourceImpl @Inject constructor(
    @DefaultJson private val json: Json,
    private val intravanApi: IntravanApi,
    private val preferences: PreferencesLocalDataSource
) : CompanyRemoteDataSource {

    // 업체목록
    override suspend fun getCompany(): Resource<Company> {
        // Request
        val request = GetCompanyRequest(
            code = preferences.code
        ).let {
            json.encodeToString(it)
        }

        // API 호출.
        intravanApi
            .getCompany(request)
            .let { response ->
                // 결과.
                resourceOf(response.isResult, response.message) {
                    response.toDomainModel()
                }
            }.run {
                return this
            }
    }
}