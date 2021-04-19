package com.example.playground

import org.junit.Test

/**
 *
 */

@ExperimentalStdlibApi
class LineCodeTest1 {

    @Test
    fun example() {
        print(solution("2(3(xixi)2(yiyi)zizi2(wiwi)mimimi)"))
    }

    private fun solution(compressed: String): String {
        var answer = ""
        val stackCalculator = ArrayDeque<Char>()
        compressed.forEach {
            if (it == ')') {
                val tempStack = ArrayDeque<Char>()
                while (stackCalculator.last() != '(' && !stackCalculator.isEmpty()) {
                    tempStack.addLast(stackCalculator.removeLast())
                }
                stackCalculator.removeLast()
                val multi = stackCalculator.removeLast().toString().toInt()
                val stack: CharArray = tempStack.toCharArray()
                tempStack.clear()
                for (i in 0 until multi) {
                    stack.forEach { item -> tempStack.addLast(item) }
                }
                val specStack = ArrayDeque<Char>()
                while (!stackCalculator.isEmpty() && stackCalculator.last() in 'a'..'z') {
                    specStack.addLast(stackCalculator.removeLast())
                }
                tempStack.addAll(specStack)
                tempStack.reverse()
                stackCalculator.addAll(tempStack)
            } else {
                stackCalculator.addLast(it)
            }
        }
        stackCalculator.forEach {
            answer += it
        }
        return answer
    }
}