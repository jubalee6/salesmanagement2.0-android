package com.intravan.salesmanagement.domain.usecase

import com.intravan.salesmanagement.domain.repository.AuthRepository
import javax.inject.Inject

class VerifyAuthUseCase @Inject constructor(private val repository: AuthRepository) {

    fun execute(code:String) {
       repository.verifyAuth(code)
    }
}