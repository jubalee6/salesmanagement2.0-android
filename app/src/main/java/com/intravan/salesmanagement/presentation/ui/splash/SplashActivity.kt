package com.intravan.salesmanagement.presentation.ui.splash

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.activity.viewModels
import com.intravan.salesmanagement.R
import com.intravan.salesmanagement.core.extension.launchActivityWithClearTop
import com.intravan.salesmanagement.core.extension.repeatOnStarted
import com.intravan.salesmanagement.core.extension.showCustomAlert
import com.intravan.salesmanagement.core.presentation.base.BaseLifecycleLogActivity
import com.intravan.salesmanagement.core.presentation.widget.positiveButton
import com.intravan.salesmanagement.core.util.DebugLog
import com.intravan.salesmanagement.presentation.ui.auth.AuthActivity
import com.intravan.salesmanagement.presentation.ui.main.MainActivity
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.Flow

/**
 * Splash.
 */
@SuppressLint("CustomSplashScreen")
@AndroidEntryPoint
class SplashActivity : BaseLifecycleLogActivity() {

    // ViewModel.
    private val viewModel: SplashViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


        // Collect.
        handleEvent(viewModel.event)
        handleUiState(viewModel.uiState)
        DebugLog.e { "HANDLE EVENAT  :: ${handleEvent(viewModel.event)}" }
        DebugLog.e { "HANDLE UISTATE :: ${handleUiState(viewModel.uiState)}" }
    }

    // Handle Event.
    private fun handleEvent(events: Flow<SplashEvent>) = repeatOnStarted {
        events.collect { event ->
            when (event) {
                is SplashEvent.StartingFailed -> showCustomAlert {
                    setTitle(R.string.text_notify)
                    setMessage(com.intravan.salesmanagement.core.R.string.ivcore_this_auth_cannot_used)
                    positiveButton {
                        finish()
                        DebugLog.e { "POSITIVEBUTTON FINISH ${finish()}" }
                    }
                }

                is SplashEvent.NavigateToAuth -> launchActivityWithClearTop(AuthActivity::class.java)
                is SplashEvent.NavigateToMain -> launchActivityWithClearTop(MainActivity::class.java)
            }
        }
    }

    // Handle uiState.
    private fun handleUiState(uiStates: Flow<SplashUiState>) = repeatOnStarted {
        uiStates.collect {
            handleLoading(it)
            when {
                it.isError -> handleFailure(it)
                !it.isLoading -> handleSuccess(it)
            }
        }
    }

    // 로딩.
    private fun handleLoading(@Suppress("UNUSED_PARAMETER") uiStates: SplashUiState) {
    }

    // 성공.
    private fun handleSuccess(@Suppress("UNUSED_PARAMETER") uiStates: SplashUiState) {
    }

    // 실패.
    private fun handleFailure(uiStates: SplashUiState) {
        if (uiStates.message.isNotBlank()) {
            showCustomAlert {
                setTitle(R.string.text_notify)
                setMessage(uiStates.message)
                positiveButton {
                    finish()
                }
            }
        }
    }
}