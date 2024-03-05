package com.intravan.salesmanagement.data.remote.response

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 * 인증번호 발송.
 */
@Serializable
data class AuthNumberResponse (
    @SerialName("result")
    val isResult: Boolean = false,
    @SerialName("message")
    val message: String = "",
    @SerialName("luseno")
    val number: String = "",
    @SerialName("ivcode")
    val code: String = ""
)
