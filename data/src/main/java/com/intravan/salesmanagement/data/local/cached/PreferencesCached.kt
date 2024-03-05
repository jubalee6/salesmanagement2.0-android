package com.intravan.salesmanagement.data.local.cached

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class PreferencesCached(
    @ColumnInfo(name = "is_first_run")
    val isFirstRun: Boolean = false,
    @ColumnInfo(name = "is_first_login")
    val isFirstLogin: Boolean = false,
    @ColumnInfo(name = "can_login_log")
    val canLoginLog: Boolean = false,
    @ColumnInfo(name = "can_feedback_log")
    val canFeedbackLog: Boolean = false,
    @ColumnInfo(name = "is_authenticated")
    val isAuthenticated: Boolean = false,
    @ColumnInfo(name = "is_save_id")
    val isSaveId: Boolean = false,
    @ColumnInfo(name = "is_auto_login")
    val isAutoLogin: Boolean = false,
    @PrimaryKey
    @ColumnInfo(name = "user_id")
    val userID: String = "",
    @ColumnInfo(name = "user_password")
    val userPassword: String = "",
    @ColumnInfo(name = "uuid")
    val uuid: String = "",
    @ColumnInfo(name = "base_url")
    val baseUrl: String = "",
    @ColumnInfo(name = "business_number")
    val businessNumber: String = "",
    @ColumnInfo(name = "mobile_number")
    val mobileNumber: String = "",
    @ColumnInfo(name = "company_code")
    val companyCode: String = "",
    @ColumnInfo(name = "company_name")
    val companyName: String = "",
    @ColumnInfo(name = "token")
    val token: String = "",
    @ColumnInfo(name = "last_token")
    val lastToken: String = "",
    @ColumnInfo(name = "created_at")
    val createdAt: Long = 0L,
    @ColumnInfo(name = "modified_at")
    val modifiedAt: Long = 0L
) {
}