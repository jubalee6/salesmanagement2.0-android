package com.intravan.salesmanagement.presentation.ui.splash

import android.annotation.SuppressLint
import android.os.Bundle
import com.intravan.salesmanagement.core.extension.repeatOnStarted
import com.intravan.salesmanagement.core.presentation.base.BaseLifecycleLogActivity
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.Flow

/**
 * Splash.
 */
@SuppressLint("CustomSplashScreen")
@AndroidEntryPoint
class SplashActivity : BaseLifecycleLogActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Collect.
    }

    // Handle Event.
    private fun handleEvent(events: Flow<SplashEvent>) = repeatOnStarted {

    }

    // Handle uiState.
    private fun handleUiState(uiState: Flow<SplashUiState>) = repeatOnStarted {

    }

    // 로딩.
    private fun handleLoading(@Suppress("UNUSED_PARAMETER") uiStates: SplashUiState) {

    }

    // 성공.
    private fun handleSuccess(@Suppress("UNUSED_PARAMETER") uiStates: SplashUiState){

    }

    // 실패.
    private fun HandleFailure(uiStates: SplashUiState){

    }
}