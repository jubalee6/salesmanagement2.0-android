package com.intravan.salesmanagement.presentation.ui.main

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.intravan.salesmanagement.core.presentation.base.BaseViewBindingFragment
import com.intravan.salesmanagement.databinding.FragmentMainBinding
import dagger.hilt.android.AndroidEntryPoint

/**
 * 메인.
 */
@AndroidEntryPoint
class MainFragment : BaseViewBindingFragment<FragmentMainBinding>(){

    override fun fragmentBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ) = FragmentMainBinding.inflate(
        inflater, container,false
    )

    override fun initScreen(view: View, savedInstanceState: Bundle?) {

    }

    override fun initObserver() {

    }

    override fun initListener() {

    }
}