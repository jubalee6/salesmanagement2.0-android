@file:Suppress("unused")

package com.intravan.salesmanagement.core.extension

import java.text.DecimalFormat

/**
 * Int Extension.
 */
// 콤마를 포함한 숫자형식.
fun Int?.toNumberWithComma(): String {
	return try {
		DecimalFormat("#,###").format(this)
	} catch (ex: Exception) {
		"0"
	}
}

// toNotNull.
fun Int?.toNotNull(): Int {
	return this ?: 0
}

// convert to bit format.
fun Int.to32bitString(): String {
	return Integer.toBinaryString(this).padStart(Int.SIZE_BITS, '0')
}

// 비트연산 하나라도 포함.
fun Int.contains(vararg states: Int) = states.any {
	this and it != 0x00000000
}

// 비트연산 모두 포함.
fun Int.containsAll(vararg states: Int) = states.reduce { accumulator, state ->
	accumulator or state
}.let {
	this and it == it
}

// 비트연산 하나라도 포함하지 않음.
fun Int.notContains(vararg states: Int) = states.none {
	this and it != 0x00000000
}

// 비트연산 모두 포함하지 않음.
fun Int.notContainsAll(vararg states: Int) = states.reduce { accumulator, state ->
	accumulator or state
}.let {
	this and it != it
}