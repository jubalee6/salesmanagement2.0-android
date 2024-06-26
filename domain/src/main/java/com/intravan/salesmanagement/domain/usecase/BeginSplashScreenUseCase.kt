package com.intravan.salesmanagement.domain.usecase

import com.intravan.salesmanagement.core.extension.resultOf
import com.intravan.salesmanagement.core.extension.toFailedThrowable
import com.intravan.salesmanagement.core.util.Resource
import com.intravan.salesmanagement.domain.repository.SplashRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import javax.inject.Inject

/**
 * Splash Start.
 */
class BeginSplashScreenUseCase @Inject constructor(
    private val repository: SplashRepository
) {
    fun execute(): Flow<Result<Resource<Boolean>>> = repository
        .beginScreen()
        .map {
            resultOf { it }
        }
        .catch {
            emit(Result.failure(it.toFailedThrowable()))
        }
}
