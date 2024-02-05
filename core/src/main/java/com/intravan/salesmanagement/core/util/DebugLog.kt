@file:Suppress("unused")

package com.intravan.salesmanagement.core.util

import com.intravan.salesmanagement.core.BuildConfig
import timber.log.Timber

/**
 * Logger.
 */
object DebugLog {

    inline fun v(message: () -> String?) {
        if (BuildConfig.DEBUG) {
            Timber.v(message())
        }
    }

    inline fun d(message: () -> String?) {
        if (BuildConfig.DEBUG) {
            Timber.d(message())
        }
    }

    inline fun i(message: () -> String?) {
        if (BuildConfig.DEBUG) {
            Timber.i(message())
        }
    }

    inline fun w(message: () -> String?) {
        if (BuildConfig.DEBUG) {
            Timber.w(message())
        }
    }

    inline fun e(message: () -> String?) {
        if (BuildConfig.DEBUG) {
            Timber.e(message())
        }
    }
}