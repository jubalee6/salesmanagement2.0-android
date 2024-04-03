package com.intravan.salesmanagement.presentation.ui.main

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.intravan.salesmanagement.core.extension.repeatOnStarted
import com.intravan.salesmanagement.core.presentation.base.BaseViewBindingFragment
import com.intravan.salesmanagement.databinding.FragmentMainBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.StateFlow

/**
 * 메인.
 */
@AndroidEntryPoint
class MainFragment : BaseViewBindingFragment<FragmentMainBinding>() {

    // ViewModel.
    private val viewModel: MainViewModel by viewModels()
    override fun fragmentBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ) = FragmentMainBinding.inflate(
        inflater, container, false
    )

    override fun initScreen(view: View, savedInstanceState: Bundle?) {

    }

    override fun initObserver() {
        handleEvent(viewModel.event)
        handleUiState(viewModel.uiState)
    }

    override fun initListener() {
        binding.buttonList.setOnClickListener {
            viewModel.acceptIntent(MainIntent.CompanyClicked)
        }
    }

    // Handle Event.
    private fun handleEvent(events: Flow<MainEvent>) = repeatOnStarted {
        events.collect { event ->
            when (event) {
                is MainEvent.NavigateToCompany -> MainFragmentDirections
                    .actionMainToCompany()
                    .runCatching {
                        findNavController().navigate(this)
                    }
            }
        }
    }

    // Handle uiState.
    private fun handleUiState(uiStates: StateFlow<MainUiState>) = repeatOnStarted {
        uiStates.collect {
            handleLoading(it)
            if (it.isError) {
                handleFailure(it)
            } else {// if (!it.isLoading) {
                handleSuccess(it)
            }
        }
    }

    // 로딩.
    private fun handleLoading(uiStates: MainUiState) {
    }

    // 성공.
    private fun handleSuccess(uiStates: MainUiState) {
    }

    // 실패.
    private fun handleFailure(@Suppress("UNUSED_PARAMETER") uiStates: MainUiState) {
    }
}