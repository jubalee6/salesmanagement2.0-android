package com.intravan.salesmanagement.core.presentation.base

import android.content.BroadcastReceiver
import android.content.Intent
import android.os.Bundle
import androidx.navigation.fragment.NavHostFragment
import com.intravan.salesmanagement.core.extension.findNavControllerSafely
import com.intravan.salesmanagement.core.util.DebugLog


/**
 * Base Fragment.
 */
abstract class BaseDialogFragment :  BaseLifecycleLogDialogFragment() {

    /*// Action Event Listener.
    var actionEventListenerRef: WeakReference<ActionEventListener?>? = null*/

    // Event BroadcastReceiver.
    private lateinit var eventBroadcastReceiver: BroadcastReceiver

    // Event BroadcastReceiver.
    private lateinit var eventBroadcastReceiverForActivated: BroadcastReceiver

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Event BroadcastReceiver 등록.


        /*// Create Delegate.
        (activity as? FragmentCreateListener)?.onFragmentCreate(WeakReference(this))*/
    }

    override fun onStart() {
        super.onStart()

        // Event BroadcastReceiver 등록.


        /*// Start Delegate.
        (activity as? FragmentStartListener)?.onFragmentStart(WeakReference(this))*/
    }

    /*override fun onResume() {
        super.onResume()

        // Resume Delegate.
        (activity as? FragmentResumeListener)?.onFragmentResume(WeakReference(this))
    }*/

    override fun onStop() {
        super.onStop()

        // Event BroadcastReceiver 해지.
        unregisterEventReceiverForActivated()
    }

    override fun onDestroy() {
        super.onDestroy()

        // Action Event BroadcastReceiver 해지.
        unregisterEventReceiver()
    }

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
        context?.applicationContext?.unregisterReceiver(eventBroadcastReceiver)
    }

    // Data BroadcastReceiver 해지.
    private fun unregisterEventReceiverForActivated() {
        context?.applicationContext?.unregisterReceiver(eventBroadcastReceiverForActivated)
    }

    // Event Receiver.
    protected open fun eventBroadcastReceiver(intent: Intent?) {
        DebugLog.i { "EVENT_BROADCAST_RECEIVER" }
    }

    // Event Receiver.
    protected open fun eventBroadcastReceiverForActivated(intent: Intent?) {
        DebugLog.i { "EVENT_BROADCAST_RECEIVER_FOR_ACTIVATED" }
    }

    // Pop BackStack.
    protected fun navigateToBack() {
        val navHostFragment = this.parentFragment as? NavHostFragment
        if (navHostFragment != null && navHostFragment.childFragmentManager.backStackEntryCount == 0) {
            activity?.finish()
        } else {
            findNavControllerSafely()?.popBackStack()
        }
    }

    // Pop BackStack.
    protected fun popBackStack() {
        findNavControllerSafely()?.popBackStack()
    }
}
