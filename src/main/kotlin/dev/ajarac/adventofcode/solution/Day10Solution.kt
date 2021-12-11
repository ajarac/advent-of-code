package dev.ajarac.adventofcode.solution

import java.util.*


val mapper1 = mapOf(
    Pair(")", "("),
    Pair("]", "["),
    Pair("}", "{"),
    Pair(">", "<")
)

val mapper2 = mapOf(
    Pair("(", ")"),
    Pair("[", "]"),
    Pair("{", "}"),
    Pair("<", ">")
)

val points1 = mapOf(
    Pair(")", 3),
    Pair("]", 57),
    Pair("}", 1197),
    Pair(">", 25137)
)

val points2 = mapOf(
    Pair(")", 1L),
    Pair("]", 2L),
    Pair("}", 3L),
    Pair(">", 4L)
)

fun Char.isCloser() = mapper1[this.toString()] != null

object Day10Solution : Solution(2021, 10, 1) {

    override fun solvePart1(input: List<String>): String {
        var score = 0
        for (line in input) {
            val char = searchCorrupterChar(line)
            score += points1[char] ?: 0
        }
        return "$score"
    }

    private fun searchCorrupterChar(line: String): String? {
        val stack = Stack<String>()
        var str: String? = null
        for (char in line) {
            if (char.isCloser()) {
                if (mapper1[char.toString()] == stack.peek()) {
                    stack.pop()
                } else {
                    str = char.toString()
                    break
                }
            } else {
                stack.add(char.toString())
            }
        }
        return str
    }

    override fun solvePart2(input: List<String>): String {
        val scores = input.mapNotNull { getAutocomplete(it) }.map { calculateScore(it) }.sorted()
        return "${scores[scores.size / 2]}"
    }

    private fun getAutocomplete(line: String): String? {
        val stack = Stack<String>()
        for (char in line) {
            if (char.isCloser()) {
                if (mapper1[char.toString()] == stack.peek()) {
                    stack.pop()
                } else {
                    return null
                }
            } else {
                stack.add(char.toString())
            }
        }
        return stack.reversed().joinToString("") { mapper2[it]!! }
    }

    private fun calculateScore(autoComplete: String): Long {
        return autoComplete.fold(0L) { acc: Long, c: Char ->
            acc * 5L + points2[c.toString()]!!
        }
    }

}