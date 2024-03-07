package com.intravan.salesmanagement.mapper

import com.intravan.salesmanagement.core.extension.getStringByTrim
import com.intravan.salesmanagement.databinding.FragmentAuthBinding
import com.intravan.salesmanagement.domain.model.Auth
import com.intravan.salesmanagement.presentation.model.AuthDisplayable

fun FragmentAuthBinding.toPresentationModel() = AuthDisplayable(
    mobileNumber = edittextPhoneNumber.getStringByTrim(),
    authNumber = edittextAuthNumber.getStringByTrim()
)

fun AuthDisplayable.toDomainModel() = Auth(
    isCheckNum = isCheckNum,
    responseAuthNumber = responseAuthNumber,
    mobileNumber = mobileNumber,
    uuid = uuid,
    authNumber = authNumber
)

fun Auth.toPresentationModel() = AuthDisplayable(
    isCheckNum = isCheckNum,
    responseAuthNumber = responseAuthNumber,
    mobileNumber = mobileNumber,
    uuid = uuid,
    authNumber = authNumber
)