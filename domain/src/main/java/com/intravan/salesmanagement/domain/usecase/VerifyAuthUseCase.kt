package com.intravan.salesmanagement.domain.usecase

import com.intravan.salesmanagement.core.extension.resultOf
import com.intravan.salesmanagement.core.extension.toFailedThrowable
import com.intravan.salesmanagement.core.util.Resource
import com.intravan.salesmanagement.domain.model.Auth
import com.intravan.salesmanagement.domain.repository.AuthRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class VerifyAuthUseCase @Inject constructor(private val repository: AuthRepository) {

    fun execute(auth: Auth): Flow<Result<Resource<Auth>>> = repository
        .verifyAuth(auth)
        .map {
            require(!it.isFailure) {
                it.message
            }
            resultOf { it }
        }
        .catch {
            emit(Result.failure(it.toFailedThrowable()))
        }
}