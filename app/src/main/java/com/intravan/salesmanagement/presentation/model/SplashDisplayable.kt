package com.intravan.salesmanagement.presentation.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class SplashDisplayable (
    val isAuthenticated: Boolean = false,
    val code: String = "",
    val uuid: String = ""
):Parcelable