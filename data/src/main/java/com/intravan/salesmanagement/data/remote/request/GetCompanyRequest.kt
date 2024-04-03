package com.intravan.salesmanagement.data.remote.request

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class GetCompanyRequest(
    @SerialName("ivcode")
    val code: String = ""
)