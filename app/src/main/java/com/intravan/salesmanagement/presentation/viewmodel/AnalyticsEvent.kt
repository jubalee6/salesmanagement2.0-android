package com.intravan.salesmanagement.presentation.viewmodel

sealed class AnalyticsEvent {

    // Fetched alert.
    //data class ShowFetchedAlert(val message: String) : AnalyticsEvent()
    data class ShowMessage(val message: String) : AnalyticsEvent()
}
