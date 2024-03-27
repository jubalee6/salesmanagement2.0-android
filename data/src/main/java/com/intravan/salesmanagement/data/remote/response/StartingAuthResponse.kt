package com.intravan.salesmanagement.data.remote.response

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 * 인증 체크 결과.
 */
@Serializable
data class StartingAuthResponse (
    @SerialName("result")
    val isResult: Boolean = false,
    @SerialName("message")
    val message: String = ""
){
}