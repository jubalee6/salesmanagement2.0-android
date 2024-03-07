package com.intravan.salesmanagement.domain.model

data class Auth(
    val isCheckNum: Boolean = false,
    val responseAuthNumber: String = "",
    val mobileNumber: String = "",
    val uuid: String = "",
    val authNumber: String = ""

)