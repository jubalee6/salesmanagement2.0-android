package com.intravan.salesmanagement.data.repository

import com.intravan.salesmanagement.core.util.Resource
import com.intravan.salesmanagement.domain.datasource.local.CompanyLocalDataSource
import com.intravan.salesmanagement.domain.datasource.remote.CompanyRemoteDataSource
import com.intravan.salesmanagement.domain.model.Company
import com.intravan.salesmanagement.domain.repository.CompanyRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class CompanyRepositoryImpl @Inject constructor(
    private val companyRemoteDataSource: CompanyRemoteDataSource,
    private val companyLocalDataSource: CompanyLocalDataSource
) : CompanyRepository {

    // 업체 목록
    override fun getComapany(): Flow<Resource<Company>> = flow {
        // Local.
        companyLocalDataSource
            .companySelectAll()
            .run {
                emit(Resource.cache {
                    Company(
                        items = this,
                        searchedItems = this
                    )
                })
            }
        // Remote.
        companyRemoteDataSource
            .getCompany()
            .run {
                emit(this)
            }
    }
}