package com.intravan.salesmanagement.data.remote.request

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 * 인증 체크 확인.
 */
@Serializable
data class GetStartingAuthRequest (
    @SerialName("ivcode")
    val code : String = "",
    @SerialName("mobileno")
    val uuid: String = ""
)