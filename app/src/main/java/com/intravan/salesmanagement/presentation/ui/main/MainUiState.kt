package com.intravan.salesmanagement.presentation.ui.main

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import javax.annotation.concurrent.Immutable

/**
 * UI State.
 */
@Immutable
@Parcelize
data class MainUiState(
    val isLoading: Boolean = false,
    val message :String = "",
    val isError: Boolean = false
) : Parcelable {

    sealed class PartialState {
        object Loading : PartialState()
        data class Error(val throwable: Throwable) : PartialState()
    }
}