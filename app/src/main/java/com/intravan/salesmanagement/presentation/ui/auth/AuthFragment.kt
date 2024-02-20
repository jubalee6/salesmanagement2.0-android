package com.intravan.salesmanagement.presentation.ui.auth

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import com.intravan.salesmanagement.core.extension.hideSoftInput
import com.intravan.salesmanagement.core.presentation.base.BaseViewBindingFragment
import com.intravan.salesmanagement.databinding.FragmentAuthBinding
import dagger.hilt.android.AndroidEntryPoint

/*
 * 인증.
 */
@AndroidEntryPoint
class AuthFragment : BaseViewBindingFragment<FragmentAuthBinding>() {

    // ViewModel.
    private val viewModel: AuthViewModel by viewModels()

    // ViewBinding
    override fun fragmentBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ) = FragmentAuthBinding.inflate(inflater, container, false)

    override fun initScreen(view: View, savedInstanceState: Bundle?) {
    }

    override fun initObserver() {

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

}



