package com.intravan.salesmanagement.domain.usecase

import com.intravan.salesmanagement.core.extension.resultOf
import com.intravan.salesmanagement.core.extension.toFailedThrowable
import com.intravan.salesmanagement.core.util.Resource
import com.intravan.salesmanagement.domain.model.Starting
import com.intravan.salesmanagement.domain.repository.StartingRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import javax.inject.Inject

// 시작정보 가져오기.
class GetStartingUseCase @Inject constructor(
    private val repository: StartingRepository
) {
    fun execute(): Flow<Result<Resource<Starting>>> = repository
        .starting()
        .map {
            resultOf { it }
        }
        .catch {
            emit(Result.failure(it.toFailedThrowable()))
        }
}