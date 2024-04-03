package com.intravan.salesmanagement.data.repository

import com.intravan.salesmanagement.core.util.Resource
import com.intravan.salesmanagement.domain.datasource.remote.CompanyRemoteDataSource
import com.intravan.salesmanagement.domain.model.Company
import com.intravan.salesmanagement.domain.repository.CompanyRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class CompanyRepositoryImpl @Inject constructor(
    private val companyRemoteDataSource: CompanyRemoteDataSource
): CompanyRepository {

    // 업체 목록
    override fun getComapany(): Flow<Resource<Company>> = flow{
        companyRemoteDataSource
            .getCompany()
            .run {
                emit(this)
            }
    }
}