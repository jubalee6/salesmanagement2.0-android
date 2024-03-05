@file:Suppress("unused")

package com.intravan.salesmanagement.core.extension

import android.app.Activity
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.media.MediaScannerConnection
import android.net.Uri
import android.view.inputmethod.InputMethodManager
import androidx.annotation.ArrayRes
import androidx.annotation.StringRes
import androidx.appcompat.app.AlertDialog
import androidx.core.content.ContextCompat
import com.intravan.salesmanagement.core.R
import com.intravan.salesmanagement.core.util.DebugLog

/**
 * Context Extension.
 */
// 어플리케이션 종료.


fun Context.navigateToUpdate() {
    val marketUri = "market://details?id=$packageName"
    val intent = Intent(Intent.ACTION_VIEW, Uri.parse(marketUri))
    startActivity(intent)
    (this@navigateToUpdate as? Activity)?.finish()
}

fun <T : Activity> Context.launchActivityWithClearTop(
    clazz: Class<T>, intent: (Intent.() -> Unit)? = null
) {
    Intent(this, clazz).apply {
        flags = Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_SINGLE_TOP
    }.also {
        intent?.invoke(it)
    }.run {
        startActivity(this)
        (this@launchActivityWithClearTop as? Activity)?.finish()
    }
}

fun <T : Activity> Context.launchActivityWithReorderFront(
    clazz: Class<T>, intent: (Intent.() -> Unit)? = null
) {
    Intent(this, clazz).apply {
        flags = Intent.FLAG_ACTIVITY_REORDER_TO_FRONT or Intent.FLAG_ACTIVITY_SINGLE_TOP
    }.also {
        intent?.invoke(it)
    }.run {
        startActivity(this)
    }
}

fun Context.launchActivityWithActionView(
    uri: Uri = Uri.EMPTY, intent: (Intent.() -> Unit)? = null
) {
    Intent(Intent.ACTION_VIEW).apply {
        data = uri
    }.also {
        intent?.invoke(it)
    }.run {
        startActivity(this)
    }
}


fun Context.sendBroadcast(action: String, intent: (Intent.() -> Unit)? = null) {
    Intent(action)
        .also {
            intent?.invoke(it)
        }.run {
            sendBroadcast(this)
        }
}

fun Context.getResourceIndex(@ArrayRes resourceId: Int, text: String?): Int {
    resources.getStringArray(resourceId).forEachIndexed { index, s ->
        if (s == text) {
            return index
        }
    }
    return 0
}

//
fun Context.getResourceString(@ArrayRes resourceId: Int, index: Int): String {
    return resources.getStringArray(resourceId)[index]
}

// AlertDialog.
fun Context.showAlert(
    message: CharSequence?,
    @StringRes titleId: Int = R.string.ivcore_text_notify
) {
    showAlert {
        setTitle(titleId)
        setMessage(message)
    }
}

// AlertDialog.
fun Context.showAlert(
    @StringRes messageId: Int,
    @StringRes titleId: Int = R.string.ivcore_text_notify
) {
    showAlert {
        setTitle(titleId)
        setMessage(messageId)
    }
}

// AlertDialog.
inline fun Context.showAlert(
    message: CharSequence?,
    @StringRes titleId: Int = R.string.ivcore_text_notify,
    showAlertDialog: AlertDialog.Builder.() -> Unit
) {
    if ((this as? Activity)?.isFinishing != true) {
        val dialogBuilder = AlertDialog.Builder(this)
        dialogBuilder.showAlertDialog()
        dialogBuilder.create()
        dialogBuilder.setTitle(titleId)
        dialogBuilder.setMessage(message)
        dialogBuilder.setPositiveButton(R.string.ivcore_text_confirm, null)
        dialogBuilder.show()
    }
}

// AlertDialog.
inline fun Context.showAlert(
    @StringRes messageId: Int,
    @StringRes titleId: Int = R.string.ivcore_text_notify,
    showAlertDialog: AlertDialog.Builder.() -> Unit
) {
    if ((this as? Activity)?.isFinishing != true) {
        val dialogBuilder = AlertDialog.Builder(this)
        dialogBuilder.showAlertDialog()
        dialogBuilder.create()
        dialogBuilder.setTitle(titleId)
        dialogBuilder.setMessage(messageId)
        dialogBuilder.setPositiveButton(R.string.ivcore_text_confirm, null)
        dialogBuilder.show()
    }
}

// AlertDialog.
inline fun Context.showAlert(showAlertDialog: AlertDialog.Builder.() -> Any) {
    if ((this as? Activity)?.isFinishing != true) {
        val dialogBuilder = AlertDialog.Builder(this)
        dialogBuilder.showAlertDialog()
        dialogBuilder.create()
        dialogBuilder.show()
    }
}

// Media Scanning
//internal fun Context.mediaScanning(file: File) = OBMediaScanning(this, file)

// Media Scanning
fun Context.mediaScanning(
    path: String?, mimeType: String? = null, action: ((path: String?, uri: Uri?) -> Unit)? = null
) = MediaScannerConnection.scanFile(
    this, arrayOf(path), arrayOf(mimeType)
) { scanPath, scanUri ->
    // Debug.
    DebugLog.i { ">>>>> MediaScannerConnection URI => $scanUri" }
    DebugLog.i { ">>>>> MediaScannerConnection PATH => $scanPath" }
    action?.invoke(scanPath, scanUri)
}

// Media Scanning
fun Context.mediaScanning(
    paths: Array<String>?,
    mimeTypes: Array<String>,
    action: ((path: String?, uri: Uri?) -> Unit)? = null
) = MediaScannerConnection.scanFile(
    this, paths, mimeTypes
) { scanPath, scanUri ->
    // Debug.
    DebugLog.i { ">>>>> MediaScannerConnection URI => $scanUri" }
    DebugLog.i { ">>>>> MediaScannerConnection PATH => $scanPath" }
    action?.invoke(scanPath, scanUri)
}

// InputMethodManager.
val Context.inputMethodManager: InputMethodManager
    get() = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager

/**
 * Generic registerReceiver extension to reduce boilerplate
 *
 * Call this like so:
 * val myReceiver = registerReceiver(IntentFilter(BROADCAST_SOMETHING_HAPPENED)) {
 *     when (intent?.action) {
 *         BROADCAST_SOMETHING_HAPPENED -> handleSomethingHappened()
 *     }
 * }
 *
 * Call this extension from your Activity's onStart(), keep a reference
 * to the returned receiver and unregister it in onStop()
 *
 * Note: If you support devices on Honeycomb or earlier,
 * then you must call this in onResume() and unregister in onPause()
 */
inline fun Context.registerReceiver(
    intentFilter: IntentFilter,
    crossinline onReceive: (intent: Intent?) -> Unit
): BroadcastReceiver {
    val receiver = object : BroadcastReceiver() {
        override fun onReceive(context: Context, intent: Intent?) {
            onReceive.invoke(intent)
        }
    }
    ContextCompat.registerReceiver(
        this, receiver, intentFilter, ContextCompat.RECEIVER_NOT_EXPORTED
    )
    return receiver
}

// Pixel to dp
fun Context.pxToDp(px: Int): Float {
    return if (px > 0) {
        val density = resources.displayMetrics.density
        px / density
    } else {
        0.0f
    }
}

// Dp to pixel
fun Context.dpToPx(dp: Int): Float {
    return if (dp > 0) {
        val density = resources.displayMetrics.density
        dp * density
    } else {
        0.0f
    }
}
