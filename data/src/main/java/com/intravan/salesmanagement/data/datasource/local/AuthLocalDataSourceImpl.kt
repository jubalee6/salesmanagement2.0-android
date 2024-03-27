package com.intravan.salesmanagement.data.datasource.local

import com.intravan.salesmanagement.domain.datasource.local.AuthLocalDataSource
import com.intravan.salesmanagement.domain.datasource.local.PreferencesLocalDataSource
import com.intravan.salesmanagement.domain.model.Auth
import javax.inject.Inject

class AuthLocalDataSourceImpl @Inject constructor(
    private val preferences: PreferencesLocalDataSource
) : AuthLocalDataSource {

    // 인증확인.
    override fun authenticated(auth: Auth): Auth = when{
        preferences.isAuthenticated -> Auth (
            isAuthenticated = true
        )
        else -> Auth(
            isAuthenticated = false
        )
    }
}