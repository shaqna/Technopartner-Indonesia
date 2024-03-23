package com.maou.myapplicationtest

import org.junit.Test

class SumArray {

    @Test
    fun myFunction() {
        val intArray = arrayOf(1,2,4,4,5,6,7,7,8,8)
        val int2Array = arrayOf(1,2,4,4,5,8,9,9,12,19)
        val doubleArray = arrayOf(-9.3,-0.5, 0.25,0.3,1.34)

        val sortedInt = sortArray(intArray)
        val sortedInt2 = sortArray(int2Array)
        val sortedDouble = sortArray(doubleArray)

        val resultInt = findSumArray(sortedInt, 12)
        val resultInt2 = findSumArray(sortedInt2, 4)
        val resultDouble = findSumArray(sortedDouble, -7.96)

        println("$resultInt, $resultInt2, $resultDouble")
    }

    private fun<A: Number> findSumArray(array: Array<A>, n:A): Boolean {
        for(i in array.indices) {
            for(j in i until array.size) {
                val sum = array[j].toDouble() + array[i].toDouble()
                println(sum)
                if(sum == n.toDouble()) {
                    return true
                }
            }
        }
        return false
    }

    private fun<A: Comparable<A>> sortArray(array: Array<A>): Array<A> {
        val n = array.size
        for (i in 0 until n - 1) {
            for (j in 0 until n - i - 1) {
                if (array[j] > array[j + 1]) {
                    val temp = array[j]
                    array[j] = array[j + 1]
                    array[j + 1] = temp
                }
            }
        }

        return array
    }


}

