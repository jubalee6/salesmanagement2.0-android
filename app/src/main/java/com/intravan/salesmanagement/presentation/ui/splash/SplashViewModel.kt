package com.intravan.salesmanagement.presentation.ui.splash

import androidx.lifecycle.SavedStateHandle
import com.intravan.salesmanagement.core.util.DebugLog
import com.intravan.salesmanagement.domain.usecase.BeginSplashScreenUseCase
import com.intravan.salesmanagement.domain.usecase.GetStartingUseCase
import com.intravan.salesmanagement.mapper.toPresentationModel
import com.intravan.salesmanagement.presentation.ui.splash.SplashEvent.NavigateToMain
import com.intravan.salesmanagement.presentation.ui.splash.SplashUiState.PartialState
import com.intravan.salesmanagement.presentation.ui.splash.SplashUiState.PartialState.Fetched
import com.intravan.salesmanagement.presentation.viewmodel.AnalyticsViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.onStart
import javax.inject.Inject

/**
 * Splash.
 */
@HiltViewModel
class SplashViewModel @Inject constructor(
    private val beginSplashScreenUseCase: BeginSplashScreenUseCase,
    private val getstartingUseCase: GetStartingUseCase,
    savedStateHandle: SavedStateHandle,
    initialState: SplashUiState
) : AnalyticsViewModel<SplashUiState, PartialState, SplashEvent, SplashIntent>(
    savedStateHandle,
    initialState
) {

    init {
        acceptIntent(SplashIntent.BeginScreen)
        acceptIntent(SplashIntent.Starting)
    }

    override fun mapIntents(intent: SplashIntent): Flow<PartialState> =
        when (intent) {
            is SplashIntent.BeginScreen -> beginScreen()
            is SplashIntent.Starting -> starting()
        }

    override fun reduceUiState(
        uiState: SplashUiState,
        partial: PartialState
    ): SplashUiState = when (partial) {
        is PartialState.Loading -> uiState.copy(
            isLoading = true,
            message = "",
            isError = false
        )

        is Fetched -> uiState.copy(
            isLoading = false,
            display = partial.display,
            message = partial.message,
            isError = false
        )

        is PartialState.Error -> uiState.copy(
            isLoading = false,
            message = partial.throwable.message ?: "",
            isError = true
        )
    }

    // 화면시작.
    private fun beginScreen(): Flow<PartialState> = flow {
        beginSplashScreenUseCase
            .execute()
            .flowOn(Dispatchers.Default)
            .onStart {
                emit(PartialState.Loading)
            }
            .collect { result ->
                result
                    .onSuccess { }
                    .onFailure { }
            }
    }

    // 초기정보.
    private fun starting(): Flow<PartialState> = flow {
        getstartingUseCase
            .execute()
            .flowOn(Dispatchers.IO)
            .onStart {
                emit(PartialState.Loading)
            }
            .collect { result ->
                result
                    .onSuccess {
                        emit(Fetched(it.value.toPresentationModel()))

                        when {
                            it.value.isAuthenticated -> publishEvent(NavigateToMain)
                            else -> publishEvent(SplashEvent.NavigateToAuth)
                        }
                        DebugLog.e { "CLICK AUTH -> >>>>>>>${publishEvent(NavigateToMain)}" }
                        DebugLog.e { "CLICK AUTH -> >>>>>>>${publishEvent(SplashEvent.NavigateToAuth)}" }
                    }
                    .onFailure {
                        publishEvent(SplashEvent.StartingFailed(it.message ?: ""))
                    }
            }
    }
}