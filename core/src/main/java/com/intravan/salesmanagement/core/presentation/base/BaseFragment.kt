package com.intravan.salesmanagement.core.presentation.base

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.os.Bundle
import androidx.activity.OnBackPressedCallback
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.NavHostFragment
import com.intravan.salesmanagement.core.extension.findNavControllerSafely
import com.intravan.salesmanagement.core.extension.registerReceiver
import com.intravan.salesmanagement.core.util.DebugLog
import com.intravan.salesmanagement.core.util.IntentAction
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

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Event BroadcastReceiver 등록.
        registerEventReceiver()
        // Activity onRestart BroadcastReceiver 등록.
        registerActivityOnRestartReceiver()


        /*// Create Delegate.
        (activity as? FragmentCreateListener)?.onFragmentCreate(WeakReference(this))*/
    }

    override fun onStart() {
        super.onStart()

        // Event BroadcastReceiver 등록.
        registerEventReceiverForActivated()

        /*// Start Delegate.
        (activity as? FragmentStartListener)?.onFragmentStart(WeakReference(this))*/
    }

    override fun onResume() {
        super.onResume()

        //
        registerAppearsToShowSoftinputReceiver()
        //
        registerDisappearsToHideSoftinputReceiver()

        // Resume Delegate.
        //(activity as? FragmentResumeListener)?.onFragmentResume(WeakReference(this))
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
    private fun registerEventReceiver() {
        val intentFilter = IntentFilter(IntentAction.BROADCAST_EVENT)
        context?.run {
            eventBroadcastReceiver = registerReceiver(intentFilter) {
                eventBroadcastReceiver(it)
            }
        }
    }

    // Data BroadcastReceiver 등록.
    private fun registerEventReceiverForActivated() {
        val intentFilter = IntentFilter(IntentAction.BROADCAST_EVENT_ACTIVATED)
        context?.run {
            eventBroadcastReceiverForActivated = registerReceiver(intentFilter) {
                eventBroadcastReceiverForActivated(it)
            }
        }
    }

    // Activity onRestart BroadcastReceiver 등록.
    private fun registerActivityOnRestartReceiver() {
        val intentFilter = IntentFilter(IntentAction.BROADCAST_ACTIVITY_ON_RESTART)
        context?.run {
            activityOnRestartReceiver = registerReceiver(intentFilter) {
                onActivityRestart()
            }
        }
    }

    // appears softinput BroadcastReceiver 등록.
    private fun registerAppearsToShowSoftinputReceiver() {
        val intentFilter = IntentFilter(IntentAction.BROADCAST_APPEARS_TO_SHOW_SOFTINPUT)
        context?.run {
            appearsToShowSoftinputReceiver = registerReceiver(intentFilter) {
                onAppearsToShowSoftinput()
            }
        }
    }

    // disappears softinput BroadcastReceiver 등록.
    private fun registerDisappearsToHideSoftinputReceiver() {
        val intentFilter = IntentFilter(IntentAction.BROADCAST_DISAPPEARS_TO_HIDE_SOFTINPUT)
        context?.run {
            disappearsToHideSoftinputReceiver = registerReceiver(intentFilter) {
                onDisappearsToHideSoftinput()
            }
        }
    }



    // 매출 사용중지 BroadcastReceiver 등록.


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
