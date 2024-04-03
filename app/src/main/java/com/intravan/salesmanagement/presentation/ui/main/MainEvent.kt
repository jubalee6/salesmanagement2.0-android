package com.intravan.salesmanagement.presentation.ui.main

sealed class MainEvent {

    // 업체 목록.
    data object NavigateToCompany: MainEvent()
}