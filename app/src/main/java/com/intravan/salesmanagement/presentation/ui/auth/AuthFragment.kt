package com.intravan.salesmanagement.presentation.ui.auth

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import com.intravan.salesmanagement.R
import com.intravan.salesmanagement.core.extension.containsAll
import com.intravan.salesmanagement.core.extension.getStringByTrim
import com.intravan.salesmanagement.core.extension.hideSoftInput
import com.intravan.salesmanagement.core.extension.launchActivityWithClearTop
import com.intravan.salesmanagement.core.extension.notContains
import com.intravan.salesmanagement.core.extension.repeatOnStarted
import com.intravan.salesmanagement.core.extension.showNotifyAlert
import com.intravan.salesmanagement.core.extension.showSoftInput
import com.intravan.salesmanagement.core.presentation.base.BaseViewBindingFragment
import com.intravan.salesmanagement.core.util.DebugLog
import com.intravan.salesmanagement.databinding.FragmentAuthBinding
import com.intravan.salesmanagement.mapper.toPresentationModel
import com.intravan.salesmanagement.presentation.ui.auth.AuthUiState.Companion.GET_AUTH_NUMBER
import com.intravan.salesmanagement.presentation.ui.auth.AuthUiState.Companion.LOADING
import com.intravan.salesmanagement.presentation.ui.auth.AuthUiState.Companion.VERIFY_AUTH
import com.intravan.salesmanagement.presentation.ui.main.MainActivity
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collect
import timber.log.Timber

/*
 * 인증.
 */
@AndroidEntryPoint
class AuthFragment : BaseViewBindingFragment<FragmentAuthBinding>() {

    // ViewModel.
    private val viewModel: AuthViewModel by viewModels()

    // ViewBinding
    override fun fragmentBinding(inflater: LayoutInflater, container: ViewGroup?) =
        FragmentAuthBinding.inflate(inflater, container, false)

    override fun initScreen(view: View, savedInstanceState: Bundle?) {
    }

    override fun initObserver() {
        handleEvent(viewModel.event)
        handleUiState(viewModel.uiState)
    }

    override fun initListener() {
        // 인증번호 발송.
        binding.buttonSendAuthNumber.setOnClickListener {
            viewModel.acceptIntent(AuthIntent.GetAuthNumberClicked(binding.toPresentationModel()))
            binding.edittextAuthNumber.showSoftInput()
        }

        binding.buttonCheckAuth.setOnClickListener {
            hideSoftInput()
            viewModel.acceptIntent(AuthIntent.VerifyAuthClicked(binding.toPresentationModel()))
            DebugLog.e { "<<<<<<<<<<buttonCheck:${viewModel.acceptIntent(AuthIntent.VerifyAuthClicked(binding.toPresentationModel()))}" }
        }
    }

    // Handle Event.
    private fun handleEvent(events: Flow<AuthEvent>) = repeatOnStarted {
        events.collect { event ->
            when (event) {
                is AuthEvent.NavigateToMain -> {
                    activity?.launchActivityWithClearTop(MainActivity::class.java)
                }
                is AuthEvent.ErrorMobileNumberLength -> {
                    showNotifyAlert(R.string.auth_error_mobile_number_length)
                }
                is AuthEvent.ErrorAuthNumberLength -> {
                    showNotifyAlert(R.string.auth_error_auth_number_length)
                }
                is AuthEvent.ErrorEmptyMobileNumber -> {
                    showNotifyAlert(R.string.auth_error_mobile_number_empty)
                }
                is AuthEvent.ErrorIncorrectAuthNumber -> {
                    showNotifyAlert(R.string.auth_error_auth_number_empty)
                }
            }
        }
    }

    // Handle uiState.
    private fun handleUiState(uiStates: StateFlow<AuthUiState>) = repeatOnStarted {
        uiStates.collect {
            handleLoading(it.states)
            if (it.isError) {
                handleFailure(it)
            } else if (!it.isLoading) {
                handleSuccess(it)
            }
            if (it.message.isNotBlank()) {
                showNotifyAlert(it.message)
            }
            // 인증번호 노출.
            // debug 상태에서만 인증번호 값이 리턴 됨.
            if (it.display.responseAuthNumber.isNotBlank()) {
                binding.edittextAuthNumber.setText(it.display.responseAuthNumber)
            }
        }
    }

    // 로딩.
    private fun handleLoading(states: Int) {
        binding.buttonSendAuthNumber.isEnabled = states.notContains(LOADING)
        binding.buttonCheckAuth.isEnabled = states.notContains(LOADING)
        binding.progressbarSendAuthNumber.isVisible = states.containsAll(LOADING, GET_AUTH_NUMBER)
        binding.progressbarCheckAuth.isVisible = states.containsAll(LOADING, VERIFY_AUTH)
    }

    // 성공.
    private fun handleSuccess(uiStates: AuthUiState) {
        binding.edittextPhoneNumber.setText(uiStates.display.mobileNumber)
        binding.edittextAuthNumber.setText(uiStates.display.authNumber)
        DebugLog.e { "<<<<<<PhoneNumber Touch: ${uiStates.display.mobileNumber}" }
        DebugLog.e { "<<<<<<AuthNumber Touch: ${uiStates.display.authNumber}" }
    }

    // 실패.
    private fun handleFailure(@Suppress("UNUSED_PARAMETER") uiStates: AuthUiState) {}
}



