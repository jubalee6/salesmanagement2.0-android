package com.intravan.salesmanagement.domain.model

data class Auth(
    val isAuthenticated : Boolean = false,
    val responseAuthNumber: String = "",
    val mobileNumber: String = "",
    val uuid: String = "",
    val authNumber: String = "",
    val code : String = ""
)