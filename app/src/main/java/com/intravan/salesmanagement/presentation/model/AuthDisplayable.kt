package com.intravan.salesmanagement.presentation.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class AuthDisplayable(
    val isAuthenticated : Boolean = false,
    val responseAuthNumber: String = "",
    val mobileNumber: String = "",
    val uuid: String = "",
    val authNumber: String = "",
    val code : String = ""
) : Parcelable