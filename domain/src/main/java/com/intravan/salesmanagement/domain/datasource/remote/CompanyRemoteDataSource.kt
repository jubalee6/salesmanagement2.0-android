package com.intravan.salesmanagement.domain.datasource.remote

import com.intravan.salesmanagement.core.util.Resource
import com.intravan.salesmanagement.domain.model.Company

interface CompanyRemoteDataSource {

    // 업체목록
    suspend fun getCompany():Resource<Company>
}