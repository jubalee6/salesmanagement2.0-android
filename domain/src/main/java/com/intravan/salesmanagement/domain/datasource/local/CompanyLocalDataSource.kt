package com.intravan.salesmanagement.domain.datasource.local

import com.intravan.salesmanagement.domain.model.Company

interface CompanyLocalDataSource {

    // 업체리스트.
    suspend fun companySelectAll() : List<Company.Item>
    // 업체리스트 저장
    suspend fun companyDeleteAllAndInsert(items: List<Company.Item>)
}