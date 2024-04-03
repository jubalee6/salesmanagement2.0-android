package com.intravan.salesmanagement.presentation.ui.company

import com.intravan.salesmanagement.presentation.model.CompanyDisplayable

/**
 * 업체 리스트.
 */
sealed class CompanyIntent {
    // 업체목록
    data class GetCompany(
        val display: CompanyDisplayable = CompanyDisplayable()
    ): CompanyIntent()
}