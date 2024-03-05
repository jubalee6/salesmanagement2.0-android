@file:Suppress("unused")

package com.intravan.salesmanagement.core.extension

import androidx.annotation.StringRes
import androidx.appcompat.app.AlertDialog
import com.intravan.salesmanagement.core.R

/**
 * AlertDialog Extension.
 */

//
fun AlertDialog.Builder.positiveButton(
    @StringRes textId: Int = R.string.ivcore_text_confirm,
    listener: (() -> Unit)? = null
) {
    this.setPositiveButton(textId) { _, _ ->
        listener?.invoke()
    }
}

fun AlertDialog.Builder.negativeButton(
    @StringRes textId: Int = R.string.ivcore_text_cancel,
    listener: (() -> Unit)? = null
) {
    this.setNegativeButton(textId) { _, _ ->
        listener?.invoke()
    }
}

fun AlertDialog.Builder.neutralButton(
    @StringRes textId: Int = R.string.ivcore_text_neutral,
    listener: (() -> Unit)? = null
) {
    this.setNeutralButton(textId) { _, _ ->
        listener?.invoke()
    }
}

fun AlertDialog.Builder.cancelButton(
    @StringRes textId: Int = R.string.ivcore_text_cancel,
    listener: (() -> Unit)? = null
) {
    this.setNegativeButton(textId) { _, _ ->
        listener?.invoke()
    }
}
