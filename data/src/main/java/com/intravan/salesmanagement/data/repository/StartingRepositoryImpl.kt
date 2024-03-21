package com.intravan.salesmanagement.data.repository

import com.intravan.salesmanagement.core.util.Resource
import com.intravan.salesmanagement.domain.datasource.local.PreferencesLocalDataSource
import com.intravan.salesmanagement.domain.model.Starting
import com.intravan.salesmanagement.domain.repository.StartingRepository
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

@ExperimentalCoroutinesApi
class StartingRepositoryImpl @Inject constructor(
    private val preferences: PreferencesLocalDataSource
): StartingRepository{
    // 어플리케이션 실행 시.
    override fun starting(): Flow<Resource<Starting>> = flow {
        // 초기정보.
        emit(
            Resource.success{
                Starting(
                    isAuthenticated = preferences.isAuthenticated
                )
            }
        )
    }
}