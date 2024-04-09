package com.intravan.salesmanagement.data.datasource.local

import com.intravan.salesmanagement.data.local.dao.CompanyDao
import com.intravan.salesmanagement.data.mapper.toDomainModel
import com.intravan.salesmanagement.data.mapper.toEntityModel
import com.intravan.salesmanagement.domain.datasource.local.CompanyLocalDataSource
import com.intravan.salesmanagement.domain.model.Company
import javax.inject.Inject

class CompanyLocalDataSourceImpl @Inject constructor(
    private val companyDao: CompanyDao
) : CompanyLocalDataSource {

    // 업체 리스트.
    override suspend fun companySelectAll(): List<Company.Item> {
        return companyDao
            .selectAll()
            .map {
                it.toDomainModel()
            }
    }

    // 업체 리스트 저장.
    override suspend fun companyDeleteAllAndInsert(items: List<Company.Item>) {
        companyDao.deleteAllAndInsert(items.map {
            it.toEntityModel()
        })
    }
}