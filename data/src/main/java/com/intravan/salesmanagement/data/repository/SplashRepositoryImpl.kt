package com.intravan.salesmanagement.data.repository

import com.intravan.salesmanagement.domain.datasource.local.PreferencesLocalDataSource
import com.intravan.salesmanagement.domain.datasource.local.SplashLocalDataSource
import com.intravan.salesmanagement.domain.repository.SplashRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class SplashRepositoryImpl @Inject constructor(
    private val splashLocalDataSource: SplashLocalDataSource
) : SplashRepository {

    override fun beginScreen(): Flow<Boolean> = flow {
        splashLocalDataSource
            .beginScreen()
            .run {
                emit(true)
            }
    }
}