package com.intravan.salesmanagement.data.repository

import com.intravan.salesmanagement.core.extension.resourceOf
import com.intravan.salesmanagement.core.util.Resource
import com.intravan.salesmanagement.domain.datasource.local.PreferencesLocalDataSource
import com.intravan.salesmanagement.domain.datasource.local.SplashLocalDataSource
import com.intravan.salesmanagement.domain.datasource.remote.SplashRemoteDataSource
import com.intravan.salesmanagement.domain.repository.SplashRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class SplashRepositoryImpl @Inject constructor(
    private val preferences: PreferencesLocalDataSource,
    private val splashLocalDataSource: SplashLocalDataSource,
    private val splashRemoteDataSource: SplashRemoteDataSource
) : SplashRepository {

    override fun beginScreen(): Flow<Resource<Boolean>> = flow {
        if (preferences.isAuthenticated) {
            splashRemoteDataSource
                .beginScreen().run {
                    emit(this)
                }
        }
        else{
            emit(
                resourceOf {
                    false
                }
            )
        }
    }
}
// Bolean 값이 true일때 , main으로 넘어가게끔  -> success
// Bolean 값이 false일때, 인증화면으로         -> Failure