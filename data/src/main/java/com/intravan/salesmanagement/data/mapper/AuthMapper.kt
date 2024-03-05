package com.intravan.salesmanagement.data.mapper

import com.intravan.salesmanagement.data.remote.request.GetAuthNumberRequest
import com.intravan.salesmanagement.data.remote.response.AuthNumberResponse
import com.intravan.salesmanagement.domain.model.Auth

fun Auth.toSendAuthNumberRequest() = GetAuthNumberRequest(
    phoneNumber = phoneNumber,
    mobileNumber = mobileNumber
)

fun AuthNumberResponse.toDomainModel(auth: Auth) = auth.copy(
    responseAuthNumber = number
)