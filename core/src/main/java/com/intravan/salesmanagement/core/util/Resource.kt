package com.intravan.salesmanagement.core.util

/**
 * 결과<T>를 캡슐화하여 상태를 전달.
 * isCache: Local / Remote.
 * isSuccess: Success / Failure.
 * message: Result Message.
 * value: T
 */
data class Resource<out T>(
    val isCache: Boolean = false,
    val isSuccess: Boolean = false,
    val message: String = "",
    val value: T
) {

    val isFailure: Boolean = !isCache && !isSuccess

    companion object {

        fun <T> cache(value: T): Resource<T> = Resource(
            isCache = true, value = value
        )

        fun <T> success(value: T): Resource<T> = Resource(
            isSuccess = true, value = value
        )

        fun <T> failure(value: T): Resource<T> = Resource(
            value = value
        )

        inline fun <T> cache(message: String = "", block: () -> T): Resource<T> = Resource(
            isCache = true, message = message, value = block()
        )

        inline fun <T> success(message: String = "", block: () -> T): Resource<T> = Resource(
            isSuccess = true, message = message, value = block()
        )

        inline fun <T> failure(message: String = "", block: () -> T): Resource<T> = Resource(
            message = message, value = block()
        )
    }
}
