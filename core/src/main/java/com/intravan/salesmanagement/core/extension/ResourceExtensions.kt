package com.intravan.salesmanagement.core.extension

import com.intravan.salesmanagement.core.util.Resource
import com.intravan.salesmanagement.core.util.StateResource
import com.intravan.salesmanagement.core.util.StateResource.Companion.AVAILABLE
import com.intravan.salesmanagement.core.util.StateResource.Companion.FAILED
import com.intravan.salesmanagement.core.util.StateResource.Companion.MODIFIED
import com.intravan.salesmanagement.core.util.StateResource.Companion.NONE
import com.intravan.salesmanagement.core.util.StateResource.Companion.SUCCEEDED
import kotlin.contracts.ExperimentalContracts
import kotlin.contracts.InvocationKind
import kotlin.contracts.contract

// 결과 캡슐용 공용 구조체 생성자.
inline fun <T> resourceOf(
    isSuccess: Boolean = false,
    message: String = "",
    crossinline block: () -> T
): Resource<T> {
    return when (isSuccess) {
        true -> Resource.success(message, block)
        else -> Resource.failure(message, block)
    }
}

/*// 결과 캡슐용 공용 구조체 생성자(Cache).
inline fun <T> cacheResourceOf(
    isSuccess: Boolean = false,
    message: String = "",
    crossinline block: () -> T
): Resource<T> {
    return when (isSuccess) {
        true -> Resource.cache(message, block)
        else -> Resource.failure(message, block)
    }
}*/

// 결과 캡슐용 공용 구조체 생성자.
inline fun <T> stateResourceOf(
    states: Int,
    message: String = "",
    crossinline block: () -> T
) = StateResource.states(states, message, block)

// 결과 캡슐용 공용 구조체 생성자.
inline fun <T> stateResourceOf(
    isSuccess: Boolean = false,
    message: String = "",
    crossinline block: () -> T
): StateResource<T> {
    return when (isSuccess) {
        true -> StateResource.states(SUCCEEDED, message, block)
        else -> StateResource.states(FAILED, message, block)
    }
}

// 결과 캡슐용 공용 구조체 생성자.
inline fun <T> modifyStateResourceOf(
    isModified: Boolean = false,
    message: String = "",
    crossinline block: () -> T
): StateResource<T> {
    return when (isModified) {
        true -> StateResource.states(MODIFIED, message, block)
        else -> StateResource.states(NONE, message, block)
    }
}

// 결과 캡슐용 공용 구조체 생성자.
inline fun <T> availabilityStateResourceOf(
    isAvailable: Boolean = false,
    message: String = "",
    crossinline block: () -> T
): StateResource<T> {
    return when (isAvailable) {
        true -> StateResource.states(SUCCEEDED or AVAILABLE, message, block)
        else -> StateResource.states(FAILED, message, block)
    }
}

inline fun <T> Resource<T>.onCache(block: (T) -> Unit): Resource<T> {
    if (isCache) block(value)
    return this
}

inline fun <T> Resource<T>.onSuccess(block: (T) -> Unit): Resource<T> {
    if (isSuccess) block(value)
    return this
}

inline fun <T> Resource<T>.onFailure(block: (T) -> Unit): Resource<T> {
    if (isFailure) block(value)
    return this
}

inline fun <T> Resource<T>.onSuccessResource(block: (Resource<T>) -> Unit): Resource<T> {
    if (isSuccess) block(this)
    return this
}

inline fun <T> Resource<T>.onFailureResource(block: (Resource<T>) -> Unit): Resource<T> {
    if (isFailure) block(this)
    return this
}

@OptIn(ExperimentalContracts::class)
inline fun <R, T> Resource<T>.map(transform: (T) -> R): Resource<R> {
    contract {
        callsInPlace(transform, InvocationKind.AT_MOST_ONCE)
    }
    return when {
        isCache -> Resource.cache(message) { transform(value) }
        isSuccess -> Resource.success(message) { transform(value) }
        else -> Resource.failure(message) { transform(value) }
    }
}

@OptIn(ExperimentalContracts::class)
inline fun <R, T> StateResource<T>.map(transform: (T) -> R): StateResource<R> {
    contract {
        callsInPlace(transform, InvocationKind.AT_MOST_ONCE)
    }
    return StateResource(states, message, transform(value))
}

fun <T> Resource<T>.toStateResource(): StateResource<T> {
    return when {
        isCache -> StateResource.cache(message, value)
        isSuccess -> StateResource.success(message, value)
        else -> StateResource.failure(message, value)
    }
}

fun <T> Resource<T>.toCache(): Resource<T> {
    return Resource.cache(message) { value }
}

fun <T> Resource<T>.toSuccess(): Resource<T> {
    return Resource.success(message) { value }
}

fun <T> Resource<T>.toFailure(): Resource<T> {
    return Resource.failure(message) { value }
}
