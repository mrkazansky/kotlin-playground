package com.example.playground

import org.junit.Test
import java.util.*
import kotlin.collections.ArrayList

/**
 *
 */

class Playground {

    val matrix = ArrayList<ArrayList<Int>>()
    val trace = ArrayList<ArrayList<Int>>()

    @Test
    fun example() {
//        val param = listOf(
//            listOf(1, 1).toIntArray(),
//            listOf(2, 1).toIntArray(),
//            listOf(1, 2).toIntArray(),
//            listOf(3, 3).toIntArray(),
//            listOf(6, 4).toIntArray(),
//            listOf(3, 1).toIntArray(),
//            listOf(3, 3).toIntArray(),
//            listOf(3, 3).toIntArray(),
//            listOf(3, 4).toIntArray(),
//            listOf(2, 1).toIntArray()
//        ).toTypedArray()
        val param = listOf(
            listOf(1, 1).toIntArray(),
            listOf(1, 2).toIntArray(),
            listOf(1, 4).toIntArray(),
            listOf(2, 1).toIntArray(),
            listOf(2, 2).toIntArray(),
            listOf(2, 3).toIntArray(),
            listOf(3, 4).toIntArray(),
            listOf(3, 1).toIntArray(),
            listOf(3, 2).toIntArray(),
            listOf(3, 3).toIntArray(),
            listOf(3, 4).toIntArray(),
            listOf(4, 4).toIntArray(),
            listOf(4, 3).toIntArray(),
            listOf(5, 4).toIntArray(),
            listOf(6, 1).toIntArray()
        ).toTypedArray()
        val result = solution3(param)
        result.forEach {
            println(it)
        }
        print(solution2(listOf(1, 10, 2, 9, 3, 8, 4, 7, 5, 6).toIntArray(), 20))
        print(solution1("2(3(hi)2(co)xab2(co))"))
    }

    //    Test 3
    fun solution3(macaron: Array<IntArray>): Array<String> {
        val answer = mutableListOf<String>()
        for (i in 0 until 6) {
            matrix.add(ArrayList<Int>().also {
                it.add(0)
                it.add(0)
                it.add(0)
                it.add(0)
                it.add(0)
                it.add(0)
            })
            trace.add(ArrayList<Int>().also {
                it.add(0)
                it.add(0)
                it.add(0)
                it.add(0)
                it.add(0)
                it.add(0)
            })
        }
        macaron.forEach {
            push(it)
        }
        for (i in 0 until 6) {
            var s = ""
            for (j in 0 until 6) {
                s += matrix[j][i]
            }
            answer.add(s)
        }
        return answer.toTypedArray()
    }

    fun push(step: IntArray) {
        val col = step[0] - 1
        val index = matrix[col].indexOfLast { it == 0 }
        if (index > -1) {
            matrix[col][index] = step[1]
            drop()
        }
    }

    fun drop() {
        var path = moveAvailable()
        while (path.size >= 3) {
            doDrop(path)
            path = moveAvailable()
        }
    }

    fun moveAvailable(): MutableList<IntArray> {
        for (i in 0..5)
            for (j in 0..5) {
                if (matrix[i][j] != 0) {
                    resetTracing()
                    val path = isDropable(i, j)
                    if (path.size >= 3)
                        return path
                }
            }
        return mutableListOf()
    }

    fun resetTracing() {
        trace.forEach {
            it.forEachIndexed { index, _ ->
                it[index] = 0
            }
        }
    }

    fun isDropable(x: Int, y: Int): MutableList<IntArray> {
        val result = mutableListOf<IntArray>()
        result.add(listOf(x, y).toIntArray())
        if (x >= 6 || y >= 6 || x <= -1 || y <= -1)
            return result
        val top = if (y <= 0) -2 else matrix[x][y - 1]
        val bot = if (y >= 5) -2 else matrix[x][y + 1]
        val left = if (x <= 0) -2 else matrix[x - 1][y]
        val right = if (x >= 5) -2 else matrix[x + 1][y]
        val current = matrix[x][y]
        trace[x][y] = -1
        if (current == top && trace[x][y - 1] != -1) {
            result.addAll(isDropable(x, y - 1))
        }
        if (current == bot && trace[x][y + 1] != -1) {
            result.addAll(isDropable(x, y + 1))
        }
        if (current == left && trace[x - 1][y] != -1) {
            result.addAll(isDropable(x - 1, y))
        }
        if (current == right && trace[x + 1][y] != -1) {
            result.addAll(isDropable(x + 1, y))
        }
        return result
    }

    fun doDrop(path: MutableList<IntArray>) {
        path.forEach {
            matrix[it[0]][it[1]] = -1
        }
        matrix.forEachIndexed { i, _ ->
            var index = matrix[i].indexOfFirst { item -> item == -1 }
            while (index >= 0) {
                matrix[i].removeAt(index)
                matrix[i].add(0, 0)
                index = matrix[i].indexOfFirst { item -> item == -1 }
            }
        }
    }

    //    Test 2
    fun solution2(A: IntArray, S: Int): Int {
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

    //    Test 1
    fun solution1(compressed: String): String {
        var answer = ""
        val stackResult = Stack<Char>()
        val stackCalculator = Stack<Char>()
        compressed.forEach {
            if (it == ')') {
                val tempStack = Stack<Char>()
                while (stackCalculator.peek() != '(') {
                    tempStack.push(stackCalculator.pop())
                }
                stackCalculator.pop()
                val multi = stackCalculator.pop().toString().toInt()

                val specStack = Stack<Char>()
                while (!stackCalculator.isEmpty() && stackCalculator.peek() in 'a'..'z') {
                    specStack.push(stackCalculator.pop())
                }
                val stack: CharArray
                if (tempStack.isEmpty()) {
                    stack = stackResult.toCharArray()
                    stackResult.removeAllElements()
                } else {
                    stack = tempStack.toCharArray()
                    tempStack.removeAllElements()
                }
                for (i in 0 until multi) {
                    stack.forEach { item -> tempStack.push(item) }
                }
                specStack.forEach { item ->
                    tempStack.push(item)
                }
                stackResult.forEach { item ->
                    tempStack.push(item)
                }
                stackResult.removeAllElements()
                tempStack.forEach { item ->
                    stackResult.push(item)
                }
            } else {
                stackCalculator.push(it)
            }
        }
        stackResult.reversed().forEach {
            answer += it
        }
        return answer
    }
}