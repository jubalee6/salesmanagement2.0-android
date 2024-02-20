package com.intravan.salesmanagement.presentation.viewmodel

import android.os.Parcelable
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import com.intravan.salesmanagement.core.di.DefaultDispatcher
import com.intravan.salesmanagement.core.di.IoDispatcher
import com.intravan.salesmanagement.core.presentation.viewmodel.BaseViewModel
import com.intravan.salesmanagement.core.util.DebugLog
import com.intravan.salesmanagement.presentation.viewmodel.AnalyticsIntent.TrackEvent
import com.intravan.salesmanagement.presentation.viewmodel.AnalyticsIntent.TrackIntent
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.channels.BufferOverflow
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * Common.
 */
abstract class AnalyticsViewModel<UI_STATE : Parcelable, PARTIAL_STATE, EVENT, INTENT>(
    savedStateHandle: SavedStateHandle,
    initialState: UI_STATE
) : BaseViewModel<UI_STATE, PARTIAL_STATE, EVENT, INTENT>(
    savedStateHandle,
    initialState
) {

    // Analytics에서 사용할 축약된 클래스 패스.
    private val analyticsClassPath: String = this::class.java.name.let { name ->
        val parts = name
            .drop(21)//.replace("com.intravan.autotab.presentation.ui.", "")
            .split(".")
        val firstLetters = parts
            .dropLast(1)
            .joinToString("") {
                it.take(1)
            }
        val lastPart = parts
            .last()
            .replace("ViewModel", "")
            .filter {
                it.isUpperCase()
            }
        firstLetters + lastPart
    }

    // Default Displatcher.
    private lateinit var defaultDispatcher: CoroutineDispatcher

    // IO Displatcher.
    private lateinit var ioDispatcher: CoroutineDispatcher


    @Inject
    fun setDefaultDispatcher(@DefaultDispatcher dispatcher: CoroutineDispatcher) {
        defaultDispatcher = dispatcher
    }

    @Inject
    fun setIoDispatcher(@IoDispatcher dispatcher: CoroutineDispatcher) {
        ioDispatcher = dispatcher
    }


    // Intent를 받아들이기 위한 Flow.
    private val _analyticsIntentFlow = MutableSharedFlow<AnalyticsIntent>(
        replay = 0,
        extraBufferCapacity = 25,
        onBufferOverflow = BufferOverflow.SUSPEND
    )

    // 이벤트를 보내기 위한 채널.
    private val _analyticsEventChannel = Channel<AnalyticsEvent>(Channel.BUFFERED)
    val analyticsEvent = _analyticsEventChannel.receiveAsFlow()




    // 처리할 Intent 식별.
    override fun onIntentHandled(name: String) {
        super.onIntentHandled(name)

        // Track Intent.
        acceptAnalyticsIntent(TrackIntent(name))
    }

    // 처리할 Event 식별.
    override fun onEventHandled(name: String) {
        super.onEventHandled(name)

        // Track Intent.
        acceptAnalyticsIntent(TrackEvent(name))
    }

    // Intent를 받아들이는 함수.
    private fun acceptAnalyticsIntent(intent: AnalyticsIntent) = viewModelScope.launch {
        // Debug.
        DebugLog.d { ">>>>>>>>>> ACCEPT_ANALYTICS_INTENT : $intent" }
        _analyticsIntentFlow.emit(intent)
    }

    // 이벤트를 발생시키는 함수.
    private fun publishEvent(event: AnalyticsEvent) = viewModelScope.launch {
        // Debug.
        DebugLog.d { ">>>>>>>>>> PUBLISH_ANALYTICS_EVENT : $event" }
        _analyticsEventChannel.send(event)
    }
}