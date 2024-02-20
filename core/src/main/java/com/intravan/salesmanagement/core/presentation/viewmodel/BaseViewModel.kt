/*
 * BaseViewModel 클래스는 Android 앱의 ViewModel을 작성하는 데 도움을 주는 추상 클래스입니다.
 *
 *  - UI_STATE, PARTIAL_STATE, EVENT, INTENT: 사용자 인터페이스 상태, 부분 UI 상태, 이벤트, 인텐트의 형식을
 *      나타냅니다. 이 형식은 프로젝트에 맞게 사용자 정의되어야 합니다.
 *  - savedStateHandle: Android의 상태를 저장하고 복원하기 위한 도구입니다.
 *  - uiState: 프로퍼티는 앱이 종료되고 다시 시작될 때 UI 상태를 보존하기 위해 사용됩니다.
 *  - intentFlow: 사용자 액션 또는 이벤트를 받아들이는 데 사용되는 Flow입니다.
 *  - acceptIntent: 메서드를 통해 액션을 제출할 수 있습니다.
 *  - eventChannel: ViewModel에서 발생시키는 이벤트를 보내는 데 사용됩니다.
 *  - publishEvent: 메서드를 통해 이벤트를 발생시킬 수 있습니다.
 *  - mapIntents, reduceUiState: 메서드는 앱의 로직에 따라 사용자 액션 또는 이벤트를 UI 상태로 변환합니다.
 *
 * 이러한 메서드는 하위 클래스에서 구현되어야 합니다.
 * 이 코드는 Clean Architecture와 MVI(모델-뷰-인텐트) 패턴을 기반으로 하며,
 * Android 앱 개발에 유용한 아키텍처 구성 요소를 제공합니다.
 */
@file:Suppress("KDocUnresolvedReference")

package com.intravan.salesmanagement.core.presentation.viewmodel

import android.os.Parcelable
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.intravan.salesmanagement.core.util.DebugLog
import kotlinx.coroutines.channels.BufferOverflow
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flatMapMerge
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.scan
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

//private const val SAVED_UI_STATE_KEY = "savedUiStateKey"

/**
 * Base ViewModel 클래스.
 * @param UI_STATE ViewModel에서 관리하는 UI 상태의 형식 (Parcelable)
 * @param PARTIAL_STATE UI 상태를 갱신하는 데 사용되는 부분 UI 상태의 형식
 * @param EVENT ViewModel에서 발생시키는 이벤트의 형식
 * @param INTENT 사용자 액션 또는 이벤트를 나타내는 인텐트의 형식
 * @property savedStateHandle Android의 SavedStateHandle을 통해 UI 상태를 저장하고 복원합니다.
 * @property initialState 초기 UI 상태
 */
abstract class BaseViewModel<UI_STATE : Parcelable, PARTIAL_STATE, EVENT, INTENT>(
    private val savedStateHandle: SavedStateHandle,
    private val initialState: UI_STATE
) : ViewModel()/*, DefaultLifecycleObserver*/ {

    // Intent를 받아들이기 위한 Flow.
    private val _intentFlow = MutableSharedFlow<INTENT>(
        replay = 0,
        extraBufferCapacity = 25,
        onBufferOverflow = BufferOverflow.SUSPEND
    )

    // UI 상태를 나타내는 StateFlow.
    //val savedUiState = savedStateHandle.getStateFlow(SAVED_UI_STATE_KEY, initialState)
    private val _uiStateFlow = MutableStateFlow(initialState)
    val uiState = _uiStateFlow.asStateFlow()

    // 이벤트를 보내기 위한 채널.
    private val _eventChannel = Channel<EVENT>(Channel.BUFFERED)
    val event = _eventChannel.receiveAsFlow()

    init {
        viewModelScope.launch {
            /*
             * intentFlow를 플랫맵하여 Partial UI State Flow로 매핑하고, UI 상태를 줄입니다.
             * 이는 MVI 아키텍처에서의 데이터 흐름을 설명하는 것입니다. 구체적으로 다음과 같은 과정을 의미합니다
             *
             *  - Intent Flow: intentFlow는 사용자의 액션 또는 이벤트를 받아들이기 위한 플로우입니다.
             *      이 플로우에는 사용자가 앱에서 수행한 액션 또는 이벤트가 흘러들어옵니다.
             *      이 플로우는 뷰모델에 의해 관찰되고 처리됩니다.
             *
             *  - flatMapMerge: flatMap은 각각의 인텐트를 받아서 다른 플로우로 변환하는 중간연산자입니다.
             *      intentFlow에서 나온 각 인텐트는 mapIntents 메서드로 전달되어 해당 인텐트를 처리하는 데 사용됩니다.
             *
             *  - mapIntents: mapIntents 메서드에서는 각 인텐트를 처리하고,
             *      그 결과로 나오는 부분적인(UI 상태와 관련된) 데이터를 포함하는 새로운 플로우를 반환합니다.
             *      이러한 부분적인 UI 상태를 가리키는 것이 "Partial UI State Flow"입니다.
             *      이 플로우에는 UI 상태를 변경하기 위한 데이터 또는 업데이트가 포함됩니다.
             *
             *  - reduceUiState: 부분적인 UI 상태를 reduceUiState 메서드에 전달합니다.
             *      reduceUiState 메서드는 현재 UI 상태와 부분적인 UI 상태를 결합하여 새로운 UI 상태를 생성하고
             *      반환합니다. 이 과정을 통해 UI 상태가 업데이트 됩니다.
             *
             * 사용자 액션을 받아들이고 이를 처리하여 UI 상태를 갱신하기 위한 데이터 흐름을 제어합니다.
             * 이것은 MVI 아키텍처에서 중요한 개념이며, UI 상태의 변화와 업데이트를 관리하는 데 사용됩니다.
             */
            @Suppress("OPT_IN_USAGE")
            _intentFlow
                .flatMapMerge {
                    mapIntents(it)
                }
                .scan(uiState.value, ::reduceUiState)
                .catch {
                    // Debug.
                    DebugLog.e { ">>>>>>>>>> EXCEPTION : ${it.stackTraceToString()}" }
                }
                .collect { state ->
                    // Debug.
                    DebugLog.d { ">>>>>>>>>> UI_STATE : ${state.javaClass.name}" }

                    _uiStateFlow.update {
                        state
                    }
                    //_uiState.value = state
                    //savedStateHandle[SAVED_UI_STATE_KEY] = state
                }
        }
    }

    // Intent를 받아들이는 함수.
    fun acceptIntent(intent: INTENT) = viewModelScope.launch {
        // Debug.
        DebugLog.d { ">>>>>>>>>> ACCEPT_INTENT : ${intent!!.javaClass.name}" }
        _intentFlow.emit(intent)
        onIntentHandled(intent!!.javaClass.simpleName.substringAfterLast("$"))
    }

    // 처리할 Intent 식별.
    protected open fun onIntentHandled(name: String) {}

    // 이벤트를 발생시키는 함수.
    protected fun publishEvent(event: EVENT) = viewModelScope.launch {
        // Debug.
        DebugLog.d { ">>>>>>>>>> PUBLISH_EVENT : ${event!!.javaClass.name}" }
        _eventChannel.send(event)
        onEventHandled(event!!.javaClass.simpleName.substringAfterLast("$"))
    }

    // 처리할 Intent 식별.
    protected open fun onEventHandled(name: String) {}

    /*var isReset = false*/

    /*// TransactionTooLargeException 대응.
    protected fun getSaveUiState(block: (UI_STATE) -> Unit) {
        savedStateHandle.get<UI_STATE>(SAVED_UI_STATE_KEY)?.let {
            block(it)
        }
    }*/

    /**
     * 사용자 액션 또는 이벤트를 UI 상태로 변환하는 추상 함수.
     * @param intent 사용자 액션 또는 이벤트
     * @return UI 상태를 업데이트하기 위한 부분 UI 상태의 Flow
     */
    protected abstract fun mapIntents(intent: INTENT): Flow<PARTIAL_STATE>

    /**
     * UI 상태를 감소시키는 추상 메서드.
     * @param uiState 현재 UI 상태
     * @param partial 부분 UI 상태
     * @return 새로운 UI 상태
     */
    protected abstract fun reduceUiState(uiState: UI_STATE, partial: PARTIAL_STATE): UI_STATE

    /*// TransactionTooLargeException 대응.
    override fun onStop(owner: LifecycleOwner) {
        super.onStop(owner)

        savedStateHandle[SAVED_UI_STATE_KEY] = initialState
    }*/
}
