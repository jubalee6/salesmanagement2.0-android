package com.intravan.salesmanagement.presentation.ui.auth

import android.os.Parcelable
import com.intravan.salesmanagement.presentation.model.AuthDisplayable
import kotlinx.parcelize.Parcelize
import javax.annotation.concurrent.Immutable


/*
 * UI State.
 */
@Immutable
@Parcelize
data class AuthUiState(
    val states: Int = NONE,
    val display: AuthDisplayable = AuthDisplayable(),
    val message: String = ""
) : Parcelable {

    val isLoading: Boolean
        get() = states and LOADING == LOADING

    val isError: Boolean
        get() = states and ERROR == ERROR

    sealed class PartialState {
        data class Loading(
            val states: Int = LOADING
        ) : PartialState()

        data class Fetched(
            val display: AuthDisplayable,
            val message: String = ""
        ) : PartialState()

        data class Error(
            val throwable: Throwable
        ) : PartialState()
    }

    companion object {
        private const val STATE_SHIFT: Int = 0x0001
        const val NONE: Int = 0x0000
        const val ERROR: Int = STATE_SHIFT
        const val LOADING: Int = ERROR shl STATE_SHIFT
        const val FETCHED: Int = LOADING shl STATE_SHIFT
        const val VERIFY_ID: Int = FETCHED shl STATE_SHIFT
        const val GET_AUTH_NUMBER: Int = VERIFY_ID shl STATE_SHIFT
        const val VERIFY_AUTH: Int = GET_AUTH_NUMBER shl STATE_SHIFT
    }
}