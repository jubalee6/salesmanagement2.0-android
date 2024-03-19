package com.intravan.salesmanagement

class KotlinEx {

    val items: List<Int> = listOf(1, 2, 3)
    val items2: List<Int> = listOf(3, 1, 2)
    val multipleItems: List<List<Int>> = listOf(listOf(1, 2, 3), listOf(4, 5, 6), listOf(7, 8, 9))

    private fun loopTest() {

        items.forEach {
            println(it)
        }
        items.forEach { item ->
            println(item)
        }
        // 결과.
        // 1
        // 2
        // 3

        items.forEachIndexed { index, item ->
            println("$index, $item")
        }
        // 결과.
        // 0, 1
        // 1, 2
        // 2, 3


        val resultForEach = items.forEach {
            if (it == 2) {
                println(it)
                return
            }
        }
        // 결과.
        // 2


        val list = mutableListOf<Int>()
        items.forEach {
            if (it == 2 || it == 3) {
                list.add(it)
            }
        }

        val result = items.filter {
            it == 2 || it == 3
        }
        // 결과.
        // result = [2, 3]

        val resut2 = items.map {
            "$it"
        }
        // 결과
        // resutl2 = ["1", "2", "3"]

        val resut3 = items.map {
            "${it + 1}"
        }
        // 결과
        // resutl2 = ["2", "3", "4"]

        val sortedItems1 = items2.sorted()
        // 결과
        // [1, 2, 3]

        val sortedItems2 = items2.sortedDescending()
        // 결과
        // [3, 2, 1]

        outer@ for (outerItems in multipleItems) {
            // 1, 2, 3
            // 4, 5, 6
            // 7, 8, 9
            for ((index, innerItem) in outerItems.withIndex()) {
                if (innerItem == 5) {
                    println("$index, $innerItem")
                    break@outer
                }
            }
        }
        // 결과.
        // 1, 5
    }

    fun test(): Int {
        return 1
    }

    fun test2(value: Int): Int {





        return value + 1
    }

    fun lamdaTest(action: (Int) -> Unit) {
        action(1)
    }

    fun lamdaTest2(action: () -> Int) {
        action() + 1
    }

    fun runTest() {

        val result1 = test()
        // result = 1

        lamdaTest {
            // it = 1
        }

        test2(2) // 3
        val result = lamdaTest2 {
            2
        }
        lamdaTest2({
            4
        })
        lamdaTest2 { test2(2) } // 4
        lamdaTest2 {
            test2(2)
        } // 4
        // 3


    }
}