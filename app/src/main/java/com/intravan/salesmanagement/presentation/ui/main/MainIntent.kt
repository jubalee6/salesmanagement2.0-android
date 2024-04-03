package com.intravan.salesmanagement.presentation.ui.main

sealed class MainIntent {

    // 업체 목록.
    data object CompanyClicked: MainIntent()
}