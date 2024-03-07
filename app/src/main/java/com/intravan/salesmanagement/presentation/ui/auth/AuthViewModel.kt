package com.intravan.salesmanagement.presentation.ui.auth

import androidx.lifecycle.SavedStateHandle
import com.intravan.salesmanagement.core.presentation.viewmodel.BaseViewModel
import com.intravan.salesmanagement.core.util.DebugLog
import com.intravan.salesmanagement.domain.usecase.GetAuthNumberUseCase
import com.intravan.salesmanagement.domain.usecase.VerifyAuthUseCase
import com.intravan.salesmanagement.mapper.toDomainModel
import com.intravan.salesmanagement.mapper.toPresentationModel
import com.intravan.salesmanagement.presentation.model.AuthDisplayable
import com.intravan.salesmanagement.presentation.ui.auth.AuthUiState.Companion.ERROR
import com.intravan.salesmanagement.presentation.ui.auth.AuthUiState.Companion.FETCHED
import com.intravan.salesmanagement.presentation.ui.auth.AuthUiState.Companion.GET_AUTH_NUMBER
import com.intravan.salesmanagement.presentation.ui.auth.AuthUiState.Companion.LOADING
import com.intravan.salesmanagement.presentation.ui.auth.AuthUiState.PartialState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.onStart
import javax.inject.Inject

/**
 * 인증.
 */
@HiltViewModel
class AuthViewModel @Inject constructor(
    private val getAuthNumberUseCase: GetAuthNumberUseCase,
    private val verifyAuthUseCase: VerifyAuthUseCase,
    savedStateHandle: SavedStateHandle,
    initialState: AuthUiState
) : BaseViewModel<AuthUiState, PartialState, AuthEvent, AuthIntent>(
    savedStateHandle,
    initialState
) {
    override fun mapIntents(intent: AuthIntent): Flow<PartialState> = when (intent) {
        is AuthIntent.GetAuthNumberClicked -> getAuthNumberClicked(intent.display)
        is AuthIntent.VerifyAuthClicked -> verifyAuthClicked(intent.display)

    }

    override fun reduceUiState(
        uiState: AuthUiState,
        partial: PartialState
    ): AuthUiState = when (partial) {
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
            message = partial.throwable.message ?: ""
        )
    }

    // 인증번호 가져오기 버튼.
    private fun getAuthNumberClicked(display: AuthDisplayable): Flow<PartialState> = flow {
        if (display.mobileNumber.isBlank()) {
            publishEvent(AuthEvent.ErrorEmptyMobileNumber)
            return@flow
        } else if (display.mobileNumber.length < 10) {
            publishEvent(AuthEvent.ErrorMobileNumberLength)
            return@flow
        }
        getAuthNumberUseCase
            .execute(display.toDomainModel())
            .flowOn(Dispatchers.IO)
            .onStart {
                emit(PartialState.Loading(GET_AUTH_NUMBER))
            }
            .collect { result ->
                result
                    .onSuccess {
                        emit(PartialState.Fetched(it.value.toPresentationModel(), it.message))
                    }
                    .onFailure {
                        emit(PartialState.Error(it))
                    }
            }
    }

    // 인증번호 확인 버튼.
    private fun verifyAuthClicked(display: AuthDisplayable): Flow<PartialState> = flow {
        if (display.responseAuthNumber == display.authNumber) {
            publishEvent(AuthEvent.NavigateToMain)
        } else if (display.authNumber.length != 5) {
            publishEvent(AuthEvent.ErrorAuthNumberLength)
        } else if (display.authNumber.isBlank()) {
            publishEvent(AuthEvent.ErrorIncorrectAuthNumber)
        }
            DebugLog.e { "<<<<<<<<<verifyAuthClicked: ${publishEvent(AuthEvent.NavigateToMain)}" }
    }
}