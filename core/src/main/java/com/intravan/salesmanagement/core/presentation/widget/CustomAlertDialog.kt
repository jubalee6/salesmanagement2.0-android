package com.intravan.salesmanagement.core.presentation.widget

import androidx.annotation.StringRes
import com.intravan.salesmanagement.core.R

/**
 * CustomAlertDialog Factory.
 */
class CustomAlertDialog {

    @StringRes
    var titleId: Int? = null
        private set

    var titleString: CharSequence? = null
        private set

    @StringRes
    var messageId: Int? = null
        private set

    var messageString: CharSequence? = null
        private set

    @StringRes
    var positiveTitleId: Int? = null
        private set

    @StringRes
    var negativeTitleId: Int? = null
        private set

    @StringRes
    var neutralTitleId: Int? = null
        private set

    @StringRes
    var warningTitleId: Int? = null
        private set

    var positiveBlock: (() -> Unit)? = null
        private set
    var negativeBlock: (() -> Unit)? = null
        private set
    var neutralBlock: (() -> Unit)? = null
        private set
    var warningBlock: (() -> Unit)? = null
        private set

    var isCancelable: Boolean = true

    inner class Builder {

        fun setTitle(@StringRes textId: Int) {
            titleId = textId
        }

        fun setTitle(text: CharSequence?) {
            text?.let { titleString = it }
        }

        fun setMessage(@StringRes textId: Int) {
            messageId = textId
        }

        fun setMessage(text: CharSequence?) {
            text?.let { messageString = it }
        }

        fun setPositiveTitleId(@StringRes textId: Int) {
            positiveTitleId = textId
        }

        fun setNegativeTitleId(@StringRes textId: Int) {
            negativeTitleId = textId
        }

        fun setNeutralTitleId(@StringRes textId: Int) {
            neutralTitleId = textId
        }

        fun setWarningTitleId(@StringRes textId: Int) {
            warningTitleId = textId
        }

        fun setOnPositiveListener(block: (() -> Unit)) {
            positiveBlock = { block() }
        }

        fun setOnNegativeListener(block: (() -> Unit)) {
            negativeBlock = { block() }
        }

        fun setOnNeutralListener(block: (() -> Unit)) {
            neutralBlock = { block() }
        }

        fun setOnWarningListener(block: (() -> Unit)) {
            warningBlock = { block() }
        }

        fun setCancelable(flag: Boolean = true) {
            isCancelable = flag
        }
    }
}

fun CustomAlertDialog.Builder.positiveButton(block: (() -> Unit)) {
    setOnPositiveListener { block() }
}

fun CustomAlertDialog.Builder.positiveButton(
    @StringRes textId: Int = R.string.ivcore_text_confirm,
    block: (() -> Unit)
) {
    setPositiveTitleId(textId)
    setOnPositiveListener { block() }
}

fun CustomAlertDialog.Builder.negativeButton(block: (() -> Unit)) {
    setOnNegativeListener { block() }
}

fun CustomAlertDialog.Builder.negativeButton(
    @StringRes textId: Int = R.string.ivcore_text_cancel,
    block: (() -> Unit)
) {
    setNegativeTitleId(textId)
    setOnNegativeListener { block() }
}

fun CustomAlertDialog.Builder.neutralButton(block: (() -> Unit)) {
    setOnNeutralListener { block() }
}

fun CustomAlertDialog.Builder.neutralButton(
    @StringRes textId: Int = R.string.ivcore_text_neutral,
    block: (() -> Unit)
) {
    setNeutralTitleId(textId)
    setOnNeutralListener { block() }
}

fun CustomAlertDialog.Builder.warningButton(block: (() -> Unit)) {
    setOnWarningListener { block() }
}

fun CustomAlertDialog.Builder.warningButton(
    @StringRes textId: Int = R.string.ivcore_text_delete,
    block: (() -> Unit)
) {
    setWarningTitleId(textId)
    setOnWarningListener { block() }
}
