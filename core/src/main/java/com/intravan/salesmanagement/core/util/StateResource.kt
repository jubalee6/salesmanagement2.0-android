package com.intravan.salesmanagement.core.util

import com.intravan.salesmanagement.core.extension.contains
import com.intravan.salesmanagement.core.extension.containsAll

/**
 * 결과<T>를 캡슐화하여 상태를 전달.
 * isCache: Local / Remote.
 * isSuccess: Success / Failure.
 * message: Result Message.
 * value: T
 */
data class StateResource<out T>(
    val states: Int = NONE,
    val message: String = "",
    val value: T
) {

    val isSuccess: Boolean
        get() = states == SUCCEEDED

    val isFailure: Boolean
        get() = states.contains(ERROR, FAILED)

    val isModified: Boolean
        get() = states.contains(MODIFIED)

    val isAvailable: Boolean
        get() = states.containsAll(SUCCEEDED, AVAILABLE)

    companion object {
        private const val STATE_SHIFT: Int = 0x0001
        const val NONE: Int = 0x0000
        const val ERROR: Int = STATE_SHIFT
        const val SUCCEEDED: Int = ERROR shl STATE_SHIFT
        const val FAILED: Int = SUCCEEDED shl STATE_SHIFT
        const val LOADING: Int = FAILED shl STATE_SHIFT
        const val CACHED: Int = LOADING shl STATE_SHIFT
        const val FETCHED: Int = CACHED shl STATE_SHIFT
        const val MODIFIED: Int = FETCHED shl STATE_SHIFT
        const val AVAILABLE: Int = MODIFIED shl STATE_SHIFT

        fun <T> cache(message: String = "", value: T): StateResource<T> = StateResource(
            states = CACHED, value = value
        )

        fun <T> success(message: String = "", value: T): StateResource<T> = StateResource(
            states = SUCCEEDED, value = value
        )

        fun <T> failure(message: String = "", value: T): StateResource<T> = StateResource(
            states = FAILED, value = value
        )

        fun <T> states(states: Int, value: T): StateResource<T> = StateResource(
            states = states, value = value
        )

        inline fun <T> cache(
            message: String = "", block: () -> T
        ): StateResource<T> = StateResource(
            states = CACHED, message = message, value = block()
        )

        inline fun <T> success(
            message: String = "", block: () -> T
        ): StateResource<T> = StateResource(
            states = SUCCEEDED, message = message, value = block()
        )

        inline fun <T> failure(
            message: String = "", block: () -> T
        ): StateResource<T> = StateResource(
            states = FAILED, message = message, value = block()
        )

        inline fun <T> states(
            states: Int, message: String = "", block: () -> T
        ): StateResource<T> = StateResource(
            states = states, message = message, value = block()
        )
    }
}
