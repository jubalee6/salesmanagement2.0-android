package com.intravan.salesmanagement.domain.usecase

import com.intravan.salesmanagement.core.extension.resultOf
import com.intravan.salesmanagement.core.extension.toFailedThrowable
import com.intravan.salesmanagement.core.util.Resource
import com.intravan.salesmanagement.domain.model.Company
import com.intravan.salesmanagement.domain.repository.CompanyRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import javax.inject.Inject

// 업체 목록 가져오기.
class GetCompanyUseCase @Inject constructor(private val reppsitory: CompanyRepository) {

    fun execute(): Flow<Result<Resource<Company>>> = reppsitory
        .getComapany()
        .map {
            require(!it.isFailure) {
                it.message
            }
            resultOf {
                it
            }
        }
        .catch {
            emit(Result.failure(it.toFailedThrowable()))
        }

}