package com.example.playground

import org.junit.Test
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
        val result = solution(param)
        result.forEach {
            println(it)
        }
    }

    private fun solution(macaron: Array<IntArray>): Array<String> {
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

    private fun push(step: IntArray) {
        val col = step[0] - 1
        val index = matrix[col].indexOfLast { it == 0 }
        if (index > -1) {
            matrix[col][index] = step[1]
            drop()
        }
    }

    private fun drop() {
        var path = moveAvailable()
        while (path.size >= 3) {
            doDrop(path)
            path = moveAvailable()
        }
    }

    private fun moveAvailable(): MutableList<IntArray> {
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

    private fun resetTracing() {
        trace.forEach {
            it.forEachIndexed { index, _ ->
                it[index] = 0
            }
        }
    }

    private fun isDropable(x: Int, y: Int): MutableList<IntArray> {
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

    private fun doDrop(path: MutableList<IntArray>) {
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
}