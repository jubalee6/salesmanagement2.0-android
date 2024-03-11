package com.intravan.salesmanagement.presentation.ui.splash

import android.os.Parcelable
import com.intravan.salesmanagement.presentation.model.SplashDisplayable
import kotlinx.parcelize.Parcelize
import javax.annotation.concurrent.Immutable

/**
 * UI State.
 */
@Immutable
@Parcelize
data class SplashUiState(
    val isLoading: Boolean = false,
    val display: SplashDisplayable = SplashDisplayable(),
    val message: String = "",
    val isError: Boolean = false
) : Parcelable {
    sealed class PartialState {
        object Loading : PartialState()
        data class Fetched(
            val display: SplashDisplayable, val message: String = ""
        ) : PartialState()

        data class Error(
            val throwable: Throwable
        ) : PartialState()
    }
}