package com.intravan.salesmanagement.presentation.ui.auth

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.intravan.salesmanagement.core.presentation.base.BaseViewBindingFragment
import com.intravan.salesmanagement.databinding.FragmentAuthBinding
import dagger.hilt.android.AndroidEntryPoint

/*
 * 인증.
 */
@AndroidEntryPoint
class AuthFragment : BaseViewBindingFragment<FragmentAuthBinding>() {
    override fun fragmentBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    )= FragmentAuthBinding.inflate(inflater, container, false)

    override fun initListener() {

    }

    override fun initScreen(view: View, savedInstanceState: Bundle?) {

    }

    override fun initObserver() {

    }

}