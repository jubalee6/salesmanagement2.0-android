package com.intravan.salesmanagement.presentation.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class SplashDisplayable (
    val isAuthenticated: Boolean = false,
    val AutoLogin: Boolean =false,
    val versionCode: String = "",
    val versionName: String = ""
):Parcelable