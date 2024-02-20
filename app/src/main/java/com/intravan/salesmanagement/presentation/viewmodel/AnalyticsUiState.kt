package com.intravan.salesmanagement.presentation.viewmodel

import android.os.Parcelable
import com.intravan.salesmanagement.core.extension.contains
import com.intravan.salesmanagement.core.extension.notContains
import com.intravan.salesmanagement.core.extension.to32bitString
import kotlinx.parcelize.Parcelize
import javax.annotation.concurrent.Immutable

/**
 * UI State.
 */
@Immutable
@Parcelize
data class AnalyticsUiState(
    val states: Int = NONE
) : Parcelable {

    val isLoading: Boolean
        get() = states.contains(LOADING)

    val isError: Boolean
        get() = states.contains(ERROR)

    val isLoadingStarted: Boolean
        get() = states and LOADING == LOADING

    val isLoadingFinished: Boolean
        get() = states.notContains(LOADING, CACHED)

    val isNotLoadingOrError: Boolean
        get() = states.notContains(LOADING, ERROR)

    // FIXME: 오류 시 화면 갱신 불가.
    /*val isCachedOrFetched: Boolean
        get() = states.contains(CACHED, FETCHED)*/
    val isFetchable: Boolean
        get() = states.contains(CACHED, FETCHED, ERROR)

    val isDestroyed: Boolean
        get() = states.contains(DESTROYED)

    // states
    val statesPretty: String
        get() = ">>>>>>>>>>>>>>>>>>>>>NONE ${NONE.to32bitString()}\n" +
                ">>>>>>>>>>>>>>>>>>>>ERROR ${ERROR.to32bitString()}\n" +
                ">>>>>>>>>>>>>>>>>>LOADING ${LOADING.to32bitString()}\n" +
                ">>>>>>>>>>>>>>>>>>>CACHED ${CACHED.to32bitString()}\n" +
                ">>>>>>>>>>>>>>>>>>FETCHED ${FETCHED.to32bitString()}\n" +
                ">>>>>>>>>>>>>>>>DESTROYED ${DESTROYED.to32bitString()}\n" +
                ">>>>>>>>>>>>>>>>>>CURRENT ${states.to32bitString()}\n" +
                ">>>>>>>>>>>>>>>>>>>>>>>>> " +
                (if (states.contains(ERROR)) "ERROR " else "") +
                (if (states.contains(LOADING)) "LOADING " else "") +
                (if (states.contains(CACHED)) "CACHED " else "") +
                (if (states.contains(FETCHED)) "FETCHED " else "") +
                (if (states.contains(DESTROYED)) "DESTROYED " else "")

    sealed class PartialState {
        object Loading : PartialState()
        data class Success(val name: String) : PartialState()
        data class Failure(val throwable: Throwable) : PartialState()
    }

    companion object {
        private const val STATE_SHIFT: Int = 0x0001
        const val NONE: Int = 0x0000
        const val ERROR: Int = STATE_SHIFT
        const val LOADING: Int = ERROR shl STATE_SHIFT
        const val CACHED: Int = LOADING shl STATE_SHIFT
        const val FETCHED: Int = CACHED shl STATE_SHIFT
        const val DESTROYED: Int = FETCHED shl STATE_SHIFT
    }
}
