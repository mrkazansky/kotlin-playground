package com.example.playground

import org.junit.Test

/**
 *
 */

class LineCodeTest2 {

    @Test
    fun example() {
        print(solution(listOf(1, 10, 2, 9, 3, 8, 4, 7, 5, 6).toIntArray(), 20))
    }

    private fun solution(A: IntArray, S: Int): Int {
        var counter: Int = 0
        var firstIndex = 0
        var sum = 0L
        var min = 99999
        A.forEachIndexed { index, value ->
            sum += value
            counter++
            if (sum >= S) {
                if (counter < min) {
                    min = counter
                }
                counter--
                sum -= A[firstIndex]
                firstIndex = index + 1
            }
        }
        return counter
    }


}