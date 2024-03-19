package com.intravan.salesmanagement.domain.repository

import com.intravan.salesmanagement.core.util.Resource
import com.intravan.salesmanagement.domain.model.Starting
import kotlinx.coroutines.flow.Flow

interface StartingRepository {

    // 어플리케이션 실행시 초기정보.
    fun starting(): Flow<Resource<Starting>>
}