package com.maou.myapplicationtest

import org.junit.Test

import org.junit.Assert.*

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
class ExampleUnitTest {


    @Test
    fun myFunction() {
        val myIntArr = arrayOf(1,12,31,5,3,23,4,5,22)
        val myDoubleArr = arrayOf(-0.5,-0.76,0.45,-0.2,4.5,3.5)
        val myInt2Arr = arrayOf(98,12,42,13,13,56,100,99)

        val mySecondMaxInt = secondMax(myIntArr)
        val mySecondMaxDouble = secondMax(myDoubleArr)
        val mySecondMaxInt2 = secondMax(myInt2Arr)

        println("$mySecondMaxInt, $mySecondMaxDouble, $mySecondMaxInt2")
    }

    private fun<A: Comparable<A>> secondMax(arr: Array<out A>): A {
        val sortedArr = mySorterArray(arr)
        return sortedArr[sortedArr.size - 2]
    }

    private fun<A: Comparable<A>> mySorterArray(unsortedArr: Array<A>): Array<A> {
        val n = unsortedArr.size
        for (i in 0 until n - 1) {
            for (j in 0 until n - i - 1) {
                if (unsortedArr[j] > unsortedArr[j + 1]) {
                    val temp = unsortedArr[j]
                    unsortedArr[j] = unsortedArr[j + 1]
                    unsortedArr[j + 1] = temp
                }
            }
        }
        return unsortedArr
    }

}