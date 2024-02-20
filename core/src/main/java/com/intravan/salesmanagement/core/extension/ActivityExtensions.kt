@file:Suppress("unused")

package com.intravan.salesmanagement.core.extension

import android.app.Activity
import android.content.Context
import android.view.inputmethod.InputMethodManager
import androidx.annotation.IdRes
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
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
