package com.intravan.salesmanagement.presentation.ui.auth

import androidx.lifecycle.SavedStateHandle
import com.intravan.salesmanagement.core.presentation.viewmodel.BaseViewModel
import com.intravan.salesmanagement.presentation.model.AuthDisplayable
import com.intravan.salesmanagement.presentation.ui.auth.AuthUiState.Companion.ERROR
import com.intravan.salesmanagement.presentation.ui.auth.AuthUiState.Companion.FETCHED
import com.intravan.salesmanagement.presentation.ui.auth.AuthUiState.Companion.GET_AUTH_NUMBER
import com.intravan.salesmanagement.presentation.ui.auth.AuthUiState.Companion.LOADING
import com.intravan.salesmanagement.presentation.ui.auth.AuthUiState.Companion.VERIFY_AUTH
import com.intravan.salesmanagement.presentation.ui.auth.AuthUiState.Companion.VERIFY_ID
import com.intravan.salesmanagement.presentation.ui.auth.AuthUiState.PartialState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

/**
 * 인증.
 */
@HiltViewModel
class AuthViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    initialState: AuthUiState
) : BaseViewModel<AuthUiState,PartialState, AuthEvent, AuthIntent>(
    savedStateHandle,
    initialState
) {
    override fun mapIntents(intent: AuthIntent): Flow<PartialState> = when(intent){
            is AuthIntent.GetAuthNumberClicked -> getAuthNumberClicked(intent.display)
            is AuthIntent.VerifyAuthClicked -> verifyAuthClicked(intent.display)

    }

    override fun reduceUiState(
        uiState: AuthUiState,
        partial: PartialState
    ): AuthUiState = when(partial){
        is PartialState.Loading -> uiState.copy(
            states = LOADING or partial.states,
            display = uiState.display.copy(
                responseAuthNumber = ""
            ),
            message = ""
        )
        is PartialState.Fetched -> uiState.copy(
            states = FETCHED,
            display = partial.display,
            message = partial.message
        )
        is PartialState.Error -> uiState.copy(
            states = ERROR,
            message = partial.throwable.message?:""
        )
    }

    private fun getAuthNumberClicked(display: AuthDisplayable): Flow<PartialState> = flow {
    }

    private fun verifyAuthClicked(display:AuthDisplayable): Flow<PartialState> = flow {
    }




}