package com.intravan.salesmanagement.domain.usecase

import com.intravan.salesmanagement.core.util.Resource
import com.intravan.salesmanagement.domain.model.Auth
import com.intravan.salesmanagement.domain.repository.AuthRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

/**
 * 인증확인.
 */
class VerifyAuthUseCase @Inject constructor(private val repository: AuthRepository){
//
//    fun execute(auth: Auth): Flow<Result<Resource<Auth>>> = repository
//        .

}