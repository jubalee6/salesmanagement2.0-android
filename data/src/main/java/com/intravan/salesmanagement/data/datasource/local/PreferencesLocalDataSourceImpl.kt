package com.intravan.salesmanagement.data.datasource.local

import android.content.Context
import com.intravan.salesmanagement.core.BuildConfig
import com.intravan.salesmanagement.core.util.Uuid
import com.intravan.salesmanagement.domain.datasource.local.PreferencesLocalDataSource
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

/**
 * SharedPreferences.
 */
class PreferencesLocalDataSourceImpl @Inject constructor(
    @ApplicationContext private val context: Context
) : PreferencesLocalDataSource {

    private val preferences = context.getSharedPreferences(
        context.packageName, Context.MODE_PRIVATE
    )

    override val uuid: String
        get() {
            var uuid = preferences.getString("uuid", "") ?: ""
            if (uuid.isBlank()) {
                uuid = Uuid.id(context.applicationContext)
                //uuid = UUID.randomUUID().toString()
                val editor = preferences.edit()
                editor.putString("uuid", uuid)
                editor.apply()
            }
            return uuid
        }

    override var baseUrl: String
        get() = preferences.getString("baseUrl", BuildConfig.BASE_URL) ?: BuildConfig.BASE_URL
        set(value) {
            val editor = preferences.edit()
            editor.putString("baseUrl", value)
            editor.apply()
        }

    override var isAuthenticated: Boolean
        get() = preferences.getBoolean("isAuthenticated", false)
        set(value) {
            val editor = preferences.edit()
            editor.putBoolean("isAuthenticated", value)
            editor.apply()
        }

    override var code: String
        get() = preferences.getString("code", "") ?: ""
        set(value) {
            val editor = preferences.edit()
            editor.putString("code", value)
            editor.apply()
        }
}