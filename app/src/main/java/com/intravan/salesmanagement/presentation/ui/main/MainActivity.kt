package com.intravan.salesmanagement.presentation.ui.main

import android.os.Bundle
import com.intravan.salesmanagement.core.presentation.base.BaseViewBindingActivity
import com.intravan.salesmanagement.databinding.ActivityMainBinding
import dagger.hilt.android.AndroidEntryPoint

/**
 * 메인.
 */
@AndroidEntryPoint
class MainActivity : BaseViewBindingActivity<ActivityMainBinding>(
    { ActivityMainBinding.inflate(it) }
) {
    override fun initScreen(savedInstanceState: Bundle?) {
        // Extras 전달.
    }

    override fun initObserver() {}

    override fun initListener() {}
}