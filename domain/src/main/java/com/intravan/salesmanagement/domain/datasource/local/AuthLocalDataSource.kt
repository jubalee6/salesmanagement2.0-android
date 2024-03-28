package com.intravan.salesmanagement.domain.datasource.local

interface AuthLocalDataSource {

    // 인증확인 절차.
    fun authenticated(code:String)
}