package com.intravan.salesmanagement.presentation.ui.auth

import androidx.lifecycle.SavedStateHandle
import com.intravan.salesmanagement.core.presentation.viewmodel.BaseViewModel
import com.intravan.salesmanagement.domain.usecase.GetAuthNumberUseCase
import com.intravan.salesmanagement.domain.usecase.VerifyAuthUseCase
import com.intravan.salesmanagement.mapper.toDomainModel
import com.intravan.salesmanagement.mapper.toPresentationModel
import com.intravan.salesmanagement.presentation.model.AuthDisplayable
import com.intravan.salesmanagement.presentation.ui.auth.AuthUiState.Companion.ERROR
import com.intravan.salesmanagement.presentation.ui.auth.AuthUiState.Companion.FETCHED
import com.intravan.salesmanagement.presentation.ui.auth.AuthUiState.Companion.GET_AUTH_NUMBER
import com.intravan.salesmanagement.presentation.ui.auth.AuthUiState.Companion.LOADING
import com.intravan.salesmanagement.presentation.ui.auth.AuthUiState.Companion.VERIFY_AUTH
import com.intravan.salesmanagement.presentation.ui.auth.AuthUiState.PartialState
import com.intravan.salesmanagement.presentation.ui.auth.AuthUiState.PartialState.*
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collect
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
        is Loading -> uiState.copy(
            states = LOADING or partial.states,
            display = uiState.display.copy(
                responseAuthNumber = ""
            ),
            message = ""
        )

        is Fetched -> uiState.copy(
            states = FETCHED,
            display = partial.display,
            message = partial.message
        )

        is Error -> uiState.copy(
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
                emit(Loading(GET_AUTH_NUMBER))
            }
            .collect { result ->
                result
                    .onSuccess {
                        // it = ivcode.(uiState에 저장.)
                        emit(Fetched(it.value.toPresentationModel(), it.message))
                    }
                    .onFailure {
                        emit(Error(it))
                    }
            }
    }

    // 인증번호 확인.
    private fun verifyAuthClicked(display: AuthDisplayable): Flow<PartialState> = flow {

        val authDomainModel = display.toDomainModel()
        /*val mobileNumber = display.toDomainModel().mobileNumber
        val authNumber = display.toDomainModel().authNumber*/
        val responseAuthNumber = uiState.value.display.responseAuthNumber

        if (authDomainModel.mobileNumber.length < 10) {
            publishEvent(AuthEvent.ErrorEmptyMobileNumber)
            return@flow
        } else if (authDomainModel.authNumber.length < 5) {
            publishEvent(AuthEvent.ErrorAuthNumberLength)
            return@flow
        } else if (responseAuthNumber != authDomainModel.authNumber) {
            publishEvent(AuthEvent.ErrorIncorrectAuthNumber)
            return@flow
        }
        verifyAuthUseCase
            .execute(authDomainModel)
            .flowOn(Dispatchers.IO)
            .onStart {
                emit(Loading(VERIFY_AUTH))
            }
            .collect { result ->
                result.onSuccess {
                    emit(Fetched(it.value.toPresentationModel(), it.message))
                    publishEvent(AuthEvent.NavigateToMain)
                }
                    .onFailure {
                        emit(Error(it))
                    }
            }
    }
}
/*
    } else if (responseAuthDomainModel.responseAuthNumber != authDomainModel.authNumber) {
        publishEvent(AuthEvent.ErrorIncorrectAuthNumber)
        return@flow
    } else {
        publishEvent(AuthEvent.NavigateToMain)
    }
*/