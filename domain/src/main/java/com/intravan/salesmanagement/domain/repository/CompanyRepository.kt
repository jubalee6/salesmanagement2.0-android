package com.intravan.salesmanagement.domain.repository

import com.intravan.salesmanagement.core.util.Resource
import com.intravan.salesmanagement.domain.model.Company
import kotlinx.coroutines.flow.Flow

interface CompanyRepository {

    // 업체 목록.
    fun getComapany():Flow<Resource<Company>>
}