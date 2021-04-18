package com.example.playground

import org.junit.Test
import java.util.*

/**
 *
 */

class LineCodeTest1 {


    @Test
    fun example() {
        print(solution("2(3(hi)2(co)xab2(co))"))
    }

    private fun solution(compressed: String): String {
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