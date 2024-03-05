package com.intravan.salesmanagement.domain.datasource.local

interface PreferencesLocalDataSource {

    // 기기 고유식별자.
    val uuid: String

    // Host.
    var baseUrl: String
}