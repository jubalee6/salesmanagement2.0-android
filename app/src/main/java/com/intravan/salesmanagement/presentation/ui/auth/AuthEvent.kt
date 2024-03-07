package com.intravan.salesmanagement.presentation.ui.auth

sealed class AuthEvent {

    // Main 화면이동.
    data object NavigateToMain : AuthEvent()
    // 번호 누락.
    data object ErrorEmptyMobileNumber : AuthEvent()
    // 번호 길이 제한.
    data object ErrorMobileNumberLength : AuthEvent()
    // 인증번호 길이 제한.
    data object ErrorAuthNumberLength : AuthEvent()
    // 인증번호 불일치.
    data object ErrorIncorrectAuthNumber : AuthEvent()
}