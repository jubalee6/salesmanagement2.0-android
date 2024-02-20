@file:Suppress("unused")

package com.intravan.salesmanagement.core.extension

import android.content.Context
import android.view.inputmethod.InputMethodManager
import androidx.fragment.app.Fragment

/**
 * Fragment Extension.
 */
// 키보드 내리기.
fun Fragment.hideSoftInput() {
    view?.requestFocus()
    val imm = context?.getSystemService(Context.INPUT_METHOD_SERVICE) as? InputMethodManager
    imm?.hideSoftInputFromWindow(view?.applicationWindowToken, 0)
}

