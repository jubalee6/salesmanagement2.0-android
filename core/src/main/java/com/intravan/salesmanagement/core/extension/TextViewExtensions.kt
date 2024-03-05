@file:Suppress("unused")

package com.intravan.salesmanagement.core.extension

import android.widget.TextView

/**
 * TextView Extension.
 */
// 입력 문자열 가져오기.
fun TextView?.getString(): String = this?.text?.toString() ?: ""

// 입력 문자열 가져오기.
fun TextView?.getStringByTrim(): String = this?.text?.trim()?.toString() ?: ""

// 입력 문자열 가져오기.
fun TextView?.getNumberStringOrZero(): String =
    this?.text?.trim()?.toString().toNumberStringOrZero()

/*// 입력 문자열 가져오기.
fun TextView?.toIntStringOrZero(): String {
    return this?.text?.toString()?.trim()?.toIntOrNull()?.toString() ?: "0"
}*/

/*public inline fun TextView.doAfterTextChanged(
    crossinline action: (text: Editable?) -> Unit
): TextWatcher = addDelayTextChangedListener(afterTextChanged = action)

inline fun TextView.addDelayTextChangedListener(delay: Long,
    crossinline beforeTextChanged: (
        text: CharSequence?,
        start: Int,
        count: Int,
        after: Int
    ) -> Unit = { _, _, _, _ -> },
    crossinline onTextChanged: (
        text: CharSequence?,
        start: Int,
        before: Int,
        count: Int
    ) -> Unit = { _, _, _, _ -> },
    crossinline afterTextChanged: (text: Editable?) -> Unit = {}
): TextWatcher {
    val textWatcher = object : TextWatcher {
        override fun afterTextChanged(s: Editable?) {
            afterTextChanged.invoke(s)
        }

        override fun beforeTextChanged(text: CharSequence?, start: Int, count: Int, after: Int) {
            beforeTextChanged.invoke(text, start, count, after)
        }

        override fun onTextChanged(text: CharSequence?, start: Int, before: Int, count: Int) {
            onTextChanged.invoke(text, start, before, count)
        }
    }
    addTextChangedListener(textWatcher)

    return textWatcher
}*/
