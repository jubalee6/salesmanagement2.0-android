package com.intravan.salesmanagement.data.remote.request

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 * 인증번호.
 */
@Serializable
data class GetAuthNumberRequest(
    @SerialName("hp")
    val phoneNumber: String = "",
    @SerialName("mobileno")
    val mobileNumber: String = ""
)