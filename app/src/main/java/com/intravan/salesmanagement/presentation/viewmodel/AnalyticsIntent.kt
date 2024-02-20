package com.intravan.salesmanagement.presentation.viewmodel

sealed class AnalyticsIntent {

    // Track Fragment.
    data class TrackFragment(val name: String) : AnalyticsIntent()

    // Track ViewModel.
    data object TrackViewModel : AnalyticsIntent()

    // Track Intent.
    data class TrackIntent(val name: String) : AnalyticsIntent()

    // Track Event.
    data class TrackEvent(val name: String) : AnalyticsIntent()
}
