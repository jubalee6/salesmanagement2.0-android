package com.intravan.salesmanagement.presentation.ui.auth

import android.os.Bundle
import com.intravan.salesmanagement.core.presentation.base.BaseViewBindingActivity
import com.intravan.salesmanagement.databinding.ActivityAuthBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class AuthActivity : BaseViewBindingActivity<ActivityAuthBinding>(
    {
    ActivityAuthBinding.inflate(it)
    }
) {
    override fun initScreen(savedInstanceState: Bundle?) {
        // Extras 전달.
    }

    override fun initObserver() {

    }

    override fun initListener() {

    }
}
