package com.intravan.salesmanagement.core.presentation.base

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import androidx.activity.OnBackPressedCallback
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.NavHostFragment
import com.intravan.salesmanagement.core.extension.findNavControllerSafely
import com.intravan.salesmanagement.core.util.DebugLog
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

/**
 * Base Fragment.
 */
abstract class BaseFragment : BaseLifecycleLogFragment() {

    /*// Action Event Listener.
    var actionEventListenerRef: WeakReference<ActionEventListener?>? = null*/

    // Event BroadcastReceiver.
    private lateinit var eventBroadcastReceiver: BroadcastReceiver

    // Event BroadcastReceiver.
    private lateinit var eventBroadcastReceiverForActivated: BroadcastReceiver

    // Activity onRestart BroadcastReceiver.
    private lateinit var activityOnRestartReceiver: BroadcastReceiver

    // appears softinput BroadcastReceiver.
    private lateinit var appearsToShowSoftinputReceiver: BroadcastReceiver

    // disappears softinput BroadcastReceiver.
    private lateinit var disappearsToHideSoftinputReceiver: BroadcastReceiver

    // 매출 사용시작 BroadcastReceiver.
    private lateinit var salesUsageStartReceiver: BroadcastReceiver

    // 매출 사용중지 BroadcastReceiver.
    private lateinit var salesUsageStopReceiver: BroadcastReceiver

    // delay job.
    private var delayJob: Job? = null

    // Fragment 재시작.
    private var isRestart: Boolean = false

    override fun onAttach(context: Context) {
        super.onAttach(context)

        // Backkey Pressed.
        requireActivity().onBackPressedDispatcher.addCallback(
            this,
            object : OnBackPressedCallback(true) {
                override fun handleOnBackPressed() {
                    backPressedCallback()
                }
            })
    }



    override fun onPause() {
        super.onPause()

        //
        unregisterAppearsToShowSoftinputReceiver()
        //
        unregisterDisappearsToHideSoftinputReceiver()
    }

    override fun onStop() {
        super.onStop()

        // TODO: 재시작 설정 위치 다시 확인.
        isRestart = true

        // Event BroadcastReceiver 해지.
        unregisterEventReceiverForActivated()
    }

    override fun onDestroy() {
        super.onDestroy()

        // Action Event BroadcastReceiver 해지.
        unregisterEventReceiver()
        // Activity onRestart BroadcastReceiver 해지.
        unregisterActivityOnRestartReceiver()
        // 매출 사용시작 BroadcastReceiver 해지.
        unregisterSalesUsageStartReceiver()
        // 매출 사용중지 BroadcastReceiver 해지.
        unregisterSalesUsageStopReceiver()
    }

    // Activity onRestart
    protected open fun onActivityRestart() {}

    // appears softinput BroadcastReceiver.
    protected open fun onAppearsToShowSoftinput() {}

    // disappears softinput BroadcastReceiver.
    protected open fun onDisappearsToHideSoftinput() {}

    // 매출 사용시작.
    protected open fun onSalesUsageStart() {}

    // 매출 사용중지.
    protected open fun onSalesUsageStop() {}

    /*@Suppress("unused")
    open fun onFragmentSelected() {
        // Debug.
        DebugLog.d { ">>>>> onFragmentSelected" }
    }

    @Suppress("unused")
    open fun onFragmentDeselected() {
        // Debug.
        DebugLog.d { ">>>>> onFragmentDeselected" }
    }

    @Suppress("unused")
    open fun onLastFragmentActivation() {
        // Debug.
        DebugLog.d { ">>>>> onLastFragmentActivation" }
    }*/

    // Action Event BroadcastReceiver 등록.


    // Action Event BroadcastReceiver 해지.
    private fun unregisterEventReceiver() {
        context?.unregisterReceiver(eventBroadcastReceiver)
    }

    // Data BroadcastReceiver 해지.
    private fun unregisterEventReceiverForActivated() {
        context?.unregisterReceiver(eventBroadcastReceiverForActivated)
    }

    // Activity onRestart BroadcastReceiver 해지.
    private fun unregisterActivityOnRestartReceiver() {
        context?.unregisterReceiver(activityOnRestartReceiver)
    }

    // appears softinput BroadcastReceiver 해지.
    private fun unregisterAppearsToShowSoftinputReceiver() {
        context?.unregisterReceiver(appearsToShowSoftinputReceiver)
    }

    // disappears softinput BroadcastReceiver 해지.
    private fun unregisterDisappearsToHideSoftinputReceiver() {
        context?.unregisterReceiver(disappearsToHideSoftinputReceiver)
    }

    // 매출 사용시작 BroadcastReceiver 해지.
    private fun unregisterSalesUsageStartReceiver() {
        context?.unregisterReceiver(salesUsageStartReceiver)
    }

    // 매출 사용중지 BroadcastReceiver 해지.
    private fun unregisterSalesUsageStopReceiver() {
        context?.unregisterReceiver(salesUsageStopReceiver)
    }

    // Event Receiver.
    protected open fun eventBroadcastReceiver(intent: Intent?) {
        DebugLog.v { "=====> EVENT_BROADCAST_RECEIVER" }
    }

    // Event Receiver.
    protected open fun eventBroadcastReceiverForActivated(intent: Intent?) {
        DebugLog.v { "=====> EVENT_BROADCAST_RECEIVER_FOR_ACTIVATED" }
    }

    // BackKey Pressed Callback.
    protected open fun backPressedCallback() {
        DebugLog.v { "=====> ${javaClass.simpleName} FRAGMENT_BACK_PRESSED_CALL_BACK" }
        navigateToBack()
    }

    // Pop BackStack.
    protected open fun navigateToBack() {
        DebugLog.v { "=====> ${javaClass.simpleName} FRAGMENT_NAVIGATE_TO_BACK" }
        val navHostFragment = this.parentFragment as? NavHostFragment
        if (navHostFragment != null && navHostFragment.childFragmentManager.backStackEntryCount == 0) {
            DebugLog.v { "=====> ${javaClass.simpleName} FRAGMENT_NAVIGATE_TO_BACK_FINISH" }
            activity?.finish()
        } else {
            DebugLog.v { "=====> ${javaClass.simpleName} FRAGMENT_NAVIGATE_TO_BACK_POPBACKSTACK" }
            findNavControllerSafely()?.popBackStack()
        }
    }

    // delay
    protected fun delayedExecution(delay: Long = 125L, block: () -> Unit) {
        delayJob?.cancel()
        delayJob = lifecycleScope.launch {
            delay(delay)
            block()
        }
    }
}
