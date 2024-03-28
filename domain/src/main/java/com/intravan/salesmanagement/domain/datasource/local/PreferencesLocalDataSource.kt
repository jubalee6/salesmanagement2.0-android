package com.intravan.salesmanagement.domain.datasource.local

interface PreferencesLocalDataSource {

    // 인증완료.
    var isAuthenticated: Boolean

    // 기기 고유식별자.
    val uuid: String

    // Host.
    var baseUrl: String

    // ivcode.
    var code : String
}