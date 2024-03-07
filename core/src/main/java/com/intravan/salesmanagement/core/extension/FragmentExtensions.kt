@file:Suppress("unused")

package com.intravan.salesmanagement.core.extension

import android.content.Context
import android.view.inputmethod.InputMethodManager
import androidx.annotation.StringRes
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.intravan.salesmanagement.core.R
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

/**
 * Fragment Extension.
 */
// 키보드 내리기.
fun Fragment.hideSoftInput() {
    view?.requestFocus()
    val imm = context?.getSystemService(Context.INPUT_METHOD_SERVICE) as? InputMethodManager
    imm?.hideSoftInputFromWindow(view?.applicationWindowToken, 0)
}

inline fun Fragment.repeatOnStarted(crossinline block: suspend CoroutineScope.() -> Unit) {
    viewLifecycleOwner.lifecycleScope.launch {
        lifecycle.repeatOnLifecycle(Lifecycle.State.STARTED) {
            block()
        }
    }
}

fun Fragment.showErrorAlert(
    message: CharSequence?,
    @StringRes titleId: Int = R.string.ivcore_text_error,
    isCancelable: Boolean = true
) {
    activity?.showNotifyAlert(message, titleId, isCancelable)
}

fun Fragment.showNotifyAlert(
    @StringRes messageId: Int,
    @StringRes titleId: Int = R.string.ivcore_text_notify,
    isCancelable: Boolean = true
) {
    activity?.showNotifyAlert(messageId, titleId, isCancelable)
}

fun Fragment.showNotifyAlert(
    message: CharSequence?,
    @StringRes titleId: Int = R.string.ivcore_text_notify,
    isCancelable: Boolean = true
) {
    activity?.showNotifyAlert(message, titleId, isCancelable)
}

