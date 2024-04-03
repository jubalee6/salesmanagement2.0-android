package com.intravan.salesmanagement.presentation.ui.company

import androidx.lifecycle.SavedStateHandle
import com.intravan.salesmanagement.domain.usecase.GetCompanyUseCase
import com.intravan.salesmanagement.mapper.toPresentationModel
import com.intravan.salesmanagement.presentation.ui.company.CompanyUiState.PartialState
import com.intravan.salesmanagement.presentation.viewmodel.AnalyticsViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.onStart
import javax.inject.Inject

/**
 * 업체 리스트.
 */
@HiltViewModel
class CompanyViewModel @Inject constructor(
    private val getCompanyUseCase: GetCompanyUseCase,
    savedStateHandle: SavedStateHandle,
    initialState: CompanyUiState
):AnalyticsViewModel<CompanyUiState, PartialState,CompanyEvent,CompanyIntent>(
    savedStateHandle,
    initialState
){

    override fun mapIntents(intent: CompanyIntent): Flow<PartialState> = when(intent){
       is CompanyIntent.GetCompany -> getCompany()
    }

    override fun reduceUiState(
        uiState: CompanyUiState,
        partial: PartialState
    ): CompanyUiState = when (partial){
        is PartialState.Loading -> uiState.copy(
            isError = false,
            isLoading = true,
            message = ""
        )
        is PartialState.Cached -> uiState.copy(
            isError = false,
            message = "",
            display = partial.display
        )
        is PartialState.Fetched -> uiState.copy(
            isError = false,
            isLoading = false,
            message = "",
            display = partial.display
        )
        is PartialState.Error -> uiState.copy(
            isError = true,
            isLoading = false,
            message = partial.throwable.message?:""
        )
    }

    // 업체 목록 가져오기.
    private fun getCompany():Flow<PartialState> = flow {
        getCompanyUseCase
            .execute()
            .flowOn(Dispatchers.Default)
            .onStart {
                emit(PartialState.Loading)
            }
            .collect{result ->
                result
                    .onSuccess {
                        emit(PartialState.Fetched(it.value.toPresentationModel()))
                    }
                    .onFailure {
                        emit(error(it))
                    }
            }
    }
}