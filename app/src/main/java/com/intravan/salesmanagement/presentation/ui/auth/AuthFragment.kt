package com.intravan.salesmanagement.presentation.ui.auth

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import com.intravan.salesmanagement.core.extension.hideSoftInput
import com.intravan.salesmanagement.core.extension.notContains
import com.intravan.salesmanagement.core.extension.repeatOnStarted
import com.intravan.salesmanagement.core.extension.showNotifyAlert
import com.intravan.salesmanagement.core.presentation.base.BaseViewBindingFragment
import com.intravan.salesmanagement.databinding.FragmentAuthBinding
import com.intravan.salesmanagement.presentation.ui.auth.AuthUiState.Companion.LOADING
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collect

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
            viewModel
        }


        binding.buttonCheckAuth.setOnClickListener {
            hideSoftInput()
            viewModel
        }
    }

    // Handle Event.
    private fun handleEvent(events: Flow<AuthEvent>) = repeatOnStarted {
        TODO()
    }

    // Handle uiState.
    private fun handleUiState(uiStates: StateFlow<AuthUiState>) = repeatOnStarted {
        uiStates.collect{
            handleLoading(it.states)
            if (it.isError) {
                handleFailure(it)
            } else if (!it.isLoading) {
                handleSuccess(it)
            }
            if (it.message.isNotBlank()){
                showNotifyAlert(it.message)
            }
        }
    }

    // 로딩.
    private fun handleLoading(states: Int){
        binding.buttonSendAuthNumber.isEnabled = states.notContains(LOADING)
        binding.buttonCheckAuth.isEnabled = states.notContains(LOADING)
    }

    // 성공.
    private fun handleSuccess(uiStates: AuthUiState) {
        binding.edittextMobileNumber.setText(uiStates.display.mobileNumber)
        binding.edittextAuthNumber.setText(uiStates.display.responseAuthNumber)
    }

    // 실패.
    private fun handleFailure(@Suppress("UNUSED_PARAMETER")uiStates: AuthUiState){}
}



