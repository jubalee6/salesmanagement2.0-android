package com.intravan.salesmanagement.domain.usecase

import com.intravan.salesmanagement.core.extension.resultOf
import com.intravan.salesmanagement.core.extension.toFailedThrowable
import com.intravan.salesmanagement.core.util.Resource
import com.intravan.salesmanagement.domain.model.Splash
import com.intravan.salesmanagement.domain.repository.SplashRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import javax.inject.Inject

/**
 * Splash Start.
 */
class BeginSplashScreenUseCase @Inject constructor(
    private val repository: SplashRepository
) {
    fun execute(splash: Splash): Flow<Result<Resource<Splash>>> = repository
        .beginScreen(splash)
        .map {
            require(!it.isFailure){
                it.message
            }
            resultOf { it }
        }
        .catch {
            emit(Result.failure(it.toFailedThrowable()))
        }
}
