package com.intravan.salesmanagement.presentation.ui.company

import androidx.lifecycle.SavedStateHandle
import com.intravan.salesmanagement.domain.usecase.GetCompanyUseCase
import com.intravan.salesmanagement.mapper.toPresentationModel
import com.intravan.salesmanagement.presentation.ui.company.CompanyIntent.GetCompany
import com.intravan.salesmanagement.presentation.ui.company.CompanyUiState.PartialState
import com.intravan.salesmanagement.presentation.ui.company.CompanyUiState.PartialState.Cached
import com.intravan.salesmanagement.presentation.ui.company.CompanyUiState.PartialState.Error
import com.intravan.salesmanagement.presentation.ui.company.CompanyUiState.PartialState.Fetched
import com.intravan.salesmanagement.presentation.ui.company.CompanyUiState.PartialState.Loading
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

    init {
        acceptIntent(GetCompany())
    }

    override fun mapIntents(intent: CompanyIntent): Flow<PartialState> = when(intent){
       is GetCompany -> getCompany()
    }

    override fun reduceUiState(
        uiState: CompanyUiState,
        partial: PartialState
    ): CompanyUiState = when (partial){
        is Loading -> uiState.copy(
            isError = false,
            isLoading = true,
            message = ""
        )
        is Cached -> uiState.copy(
            isError = false,
            message = "",
            display = partial.display
        )
        is Fetched -> uiState.copy(
            isError = false,
            isLoading = false,
            message = "",
            display = partial.display
        )
        is Error -> uiState.copy(
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
                emit(Loading)
            }
            .collect{result ->
                result
                    .onSuccess {
                        emit(Fetched(it.value.toPresentationModel()))
                    }
                    .onFailure {
                        emit(Error(it))
                    }
            }
    }
}