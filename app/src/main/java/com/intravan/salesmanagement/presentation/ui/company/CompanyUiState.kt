package com.intravan.salesmanagement.presentation.ui.company

import android.os.Parcelable
import com.intravan.salesmanagement.presentation.model.CompanyDisplayable
import kotlinx.parcelize.Parcelize
import javax.annotation.concurrent.Immutable

/**
 * Ui State.
 */
@Immutable
@Parcelize
data class CompanyUiState(
    val isLoading: Boolean = false,
    val display: CompanyDisplayable = CompanyDisplayable(),
    val message: String = "",
    val isError: Boolean = false
) : Parcelable {

    sealed class PartialState {
        data object Loading : PartialState()
        data class Cached(val display: CompanyDisplayable) : PartialState()
        data class Fetched(val display: CompanyDisplayable) : PartialState()
        data class Error(val throwable: Throwable) : PartialState()
    }
}