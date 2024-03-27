package com.intravan.salesmanagement.domain.datasource.local

import com.intravan.salesmanagement.core.util.Resource
import com.intravan.salesmanagement.domain.model.Auth
import kotlinx.coroutines.flow.Flow

interface AuthLocalDataSource {

    // 인증확인 절차.
    fun authenticated(auth: Auth): Auth
}