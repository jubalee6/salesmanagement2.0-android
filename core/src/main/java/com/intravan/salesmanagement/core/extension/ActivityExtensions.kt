@file:Suppress("unused")

package com.intravan.salesmanagement.core.extension

import android.app.Activity
import android.content.Context
import android.view.inputmethod.InputMethodManager
import android.widget.Button
import android.widget.TextView
import androidx.annotation.IdRes
import androidx.annotation.StringRes
import androidx.appcompat.app.AlertDialog
import androidx.core.view.isVisible
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import com.intravan.salesmanagement.core.R
import com.intravan.salesmanagement.core.presentation.widget.CustomAlertDialog
import com.intravan.salesmanagement.core.presentation.widget.positiveButton
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

/**
 * Activity Extension.
 */
// 키보드 내리기.
fun Activity.hideSoftInput() {
    val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as? InputMethodManager
    imm?.hideSoftInputFromWindow(window?.decorView?.rootView?.applicationWindowToken, 0)
}

fun FragmentActivity.navController(@IdRes navHostFragmentIdRes: Int): NavController {
    val navHostFragment = supportFragmentManager
        .findFragmentById(navHostFragmentIdRes) as NavHostFragment
    return navHostFragment.navController
}

fun FragmentActivity.repeatOnStarted(block: suspend CoroutineScope.() -> Unit) {
    lifecycleScope.launch {
        lifecycle.repeatOnLifecycle(Lifecycle.State.STARTED, block)
    }

}
fun Activity.showNotifyAlert(
    message: CharSequence?,
    @StringRes titleId: Int = R.string.ivcore_text_notify,
    isCancelable: Boolean = true
) {
    showCustomAlert {
        setTitle(titleId)
        setMessage(message)
        setCancelable(isCancelable)
        positiveButton(R.string.ivcore_text_confirm) { }
    }
}
inline fun Activity.showCustomAlert(customAlertDialog: CustomAlertDialog.Builder.() -> Unit) {

    if (!isFinishing) {
        // AlertDialog를 생성하기 위한 빌더 생성
        val alertDialogBuilder = AlertDialog.Builder(this, R.style.StyleAlertDialog)
        var alertDialog: AlertDialog? = null

        // CustomAlertDialog 객체를 생성하고 설정
        val dialog = CustomAlertDialog().apply {
            customAlertDialog(Builder())
        }
        val dialogView = layoutInflater.inflate(R.layout.dialog_alert, null)
        with(dialogView) {
            // Title.
            val titleText = dialog.titleString ?: dialog.titleId?.let { getString(it) } ?: ""
            findViewById<TextView>(R.id.core_textview_title)?.text = titleText
            // Message
            val messageText = dialog.messageString ?: dialog.messageId?.let { getString(it) } ?: ""
            if (dialog.titleId == null && dialog.titleString == null) {
                findViewById<TextView>(R.id.core_textview_large_message)?.text = messageText
            } else {
                findViewById<TextView>(R.id.core_textview_regular_message)?.text = messageText
            }
            // 긍정적 버튼.
            findViewById<TextView>(R.id.core_button_positive)?.apply {
                isVisible = dialog.positiveBlock != null
                dialog.positiveTitleId?.let {
                    setText(it)
                }
                setOnClickListener {
                    alertDialog?.dismiss()
                    dialog.positiveBlock?.invoke()
                }
            }
            // 부정적 버튼.
            findViewById<Button>(R.id.core_button_negative)?.apply {
                isVisible = dialog.negativeBlock != null
                dialog.negativeTitleId?.let {
                    setText(it)
                }
                setOnClickListener {
                    dialog.negativeBlock?.invoke()
                    alertDialog?.dismiss()
                }
            }
            // 경고성 버튼.
            findViewById<Button>(R.id.core_button_warning)?.apply {
                isVisible = dialog.warningBlock != null
                dialog.warningTitleId?.let {
                    setText(it)
                }
                setOnClickListener {
                    dialog.warningBlock?.invoke()
                    alertDialog?.dismiss()
                }
            }
        }

        // AlertDialog 설정 및 표시.
        alertDialogBuilder.setView(dialogView)
        alertDialog = alertDialogBuilder.create()
        alertDialog.setCanceledOnTouchOutside(false)
        alertDialog.setCancelable(dialog.isCancelable)
        alertDialog.show()
    }
}
