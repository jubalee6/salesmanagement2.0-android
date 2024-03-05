package com.intravan.salesmanagement.domain.model

data class Auth(
    val isCheckNum: Boolean = false,
    val responseAuthNumber: String = "",
    val phoneNumber: String = "",
    val mobileNumber: String = "",
    val authNumber: String = ""

)