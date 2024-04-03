package com.intravan.salesmanagement.presentation.ui.main

import androidx.lifecycle.SavedStateHandle
import com.intravan.salesmanagement.presentation.viewmodel.AnalyticsViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

/**
 * 메인.
 */
@HiltViewModel
class MainViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    initialState: MainUiState
) : AnalyticsViewModel<MainUiState, MainUiState.PartialState, MainEvent, MainIntent>(
    savedStateHandle,
    initialState
) {

    // Intent 호출.
    override fun mapIntents(intent: MainIntent): Flow<MainUiState.PartialState> = when (intent){
        is MainIntent.CompanyClicked -> companyClicked()
    }

    // UiState 호출.
    override fun reduceUiState(
        uiState: MainUiState,
        partial: MainUiState.PartialState
    ): MainUiState = when (partial) {
        is MainUiState.PartialState.Loading -> uiState.copy(
            isError = false,
            isLoading = true,
            message = ""
        )

        is MainUiState.PartialState.Error -> uiState.copy(
            isError = true,
            isLoading = false,
            message = partial.throwable.message ?: ""
        )
    }

    // 업체 목록.
    private fun companyClicked(): Flow<MainUiState.PartialState> = flow {
        publishEvent(MainEvent.NavigateToCompany)
    }
}