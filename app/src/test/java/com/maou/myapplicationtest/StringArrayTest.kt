package com.maou.myapplicationtest

import org.junit.Test

class StringArrayTest {

    @Test
    fun myFunction() {
        val arrChar1 = findRecurringChar(arrayOf("A","B","C","B","A"))
        val arrChar2 = findRecurringChar(arrayOf("A","B","C","D","E","C","F","Z"))
        val arrChar3 = findRecurringChar(arrayOf("A","B","C","X","Y","Z"))

        println("$arrChar1, $arrChar2, $arrChar3")
    }

    private fun findRecurringChar(arr: Array<String>): String {
        val recurringChar = HashSet<String>()
        for(c in arr) {
            if(!recurringChar.add(c)) {
                return c
            }
        }
        return "false"
    }

}