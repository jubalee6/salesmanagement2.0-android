package com.intravan.salesmanagement.presentation.ui.auth

import android.view.Display
import com.intravan.salesmanagement.presentation.model.AuthDisplayable

sealed class AuthIntent {

    data class GetAuthNumberClicked(val display: AuthDisplayable) : AuthIntent()

    data class VerifyAuthClicked(val display: AuthDisplayable) : AuthIntent()
}