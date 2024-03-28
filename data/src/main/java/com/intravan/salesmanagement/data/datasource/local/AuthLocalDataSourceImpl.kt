package com.intravan.salesmanagement.data.datasource.local

import com.intravan.salesmanagement.core.util.DebugLog
import com.intravan.salesmanagement.domain.datasource.local.AuthLocalDataSource
import com.intravan.salesmanagement.domain.datasource.local.PreferencesLocalDataSource
import javax.inject.Inject

class AuthLocalDataSourceImpl @Inject constructor(
    private val preferences: PreferencesLocalDataSource
) : AuthLocalDataSource {


    // 인증확인.
    override fun authenticated(code: String) {
        preferences.isAuthenticated = true
        preferences.code = code
        DebugLog.d{"<<<<<<AuthPreferences ,isAuthenticated: ${preferences.isAuthenticated}, code: ${preferences.code}"}
    }
}
