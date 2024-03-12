package com.intravan.salesmanagement.presentation.ui.splash

import androidx.lifecycle.SavedStateHandle
import com.intravan.salesmanagement.domain.usecase.BeginSplashScreenUseCase
import com.intravan.salesmanagement.presentation.viewmodel.AnalyticsViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collect
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
    savedStateHandle: SavedStateHandle,
    initialState: SplashUiState
) : AnalyticsViewModel<SplashUiState, SplashUiState.PartialState, SplashEvent, SplashIntent>(
    savedStateHandle,
    initialState
) {
    init {
        acceptIntent(SplashIntent.BeginScreen)
    }

    override fun mapIntents(intent: SplashIntent): Flow<SplashUiState.PartialState> =
        when (intent) {
            is SplashIntent.BeginScreen -> beginScreen()
        }

    override fun reduceUiState(
        uiState: SplashUiState,
        partial: SplashUiState.PartialState
    ): SplashUiState = when (partial) {
        is SplashUiState.PartialState.Loading -> uiState.copy(
            isLoading = true,
            message = "",
            isError = false
        )

        is SplashUiState.PartialState.Fetched -> uiState.copy(
            isLoading = false,
            display = partial.display,
            message = partial.message,
            isError = false
        )

        is SplashUiState.PartialState.Error -> uiState.copy(
            isLoading = false,
            message = partial.throwable.message ?: "",
            isError = true
        )
    }

    private fun beginScreen(): Flow<SplashUiState.PartialState> = flow {
        beginSplashScreenUseCase
            .execute()
            .flowOn(Dispatchers.Default)
            .onStart {
                emit(SplashUiState.PartialState.Loading)
            }
            .collect { result ->
                result
                    .onSuccess { }
                    .onFailure { }
            }
    }

}