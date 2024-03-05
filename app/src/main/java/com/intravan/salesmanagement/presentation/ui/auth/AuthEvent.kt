package com.intravan.salesmanagement.presentation.ui.auth

sealed class AuthEvent {

    data object ErrorEmptyMobileNumber : AuthEvent()
    data object ErrorMobileNumberLength : AuthEvent()
    data object ErrorAuthNumberLength : AuthEvent()
}