package dev.ajarac.adventofcode.solution.year_2023

import dev.ajarac.adventofcode.solution.Solution

object Day1Solution : Solution(2023, 1, 1) {

    override fun solvePart1(input: List<String>): String {
        return input.sumOf { processLinePart1(it) }.toString()
    }

    private fun processLinePart1(line: String): Int {
        var total = 0
        var left = 0
        var right = line.length - 1
        var found = Pair(false, false)
        while (left <= right && (!found.first || !found.second)) {
            if (!found.first && line[left].isDigit()) {
                total += line[left].digitToInt() * 10
                found = Pair(true, found.second)
            }
            if (!found.second && line[right].isDigit()) {
                total += line[right].digitToInt()
                found = Pair(found.first, true)
            }
            if (!found.first) {
                left++
            }
            if (!found.second) {
                right--
            }
        }
        return total
    }

    private val spellingNumbersMap = mapOf(
        "one" to 1,
        "two" to 2,
        "three" to 3,
        "four" to 4,
        "five" to 5,
        "six" to 6,
        "seven" to 7,
        "eight" to 8,
        "nine" to 9,
    )

    override fun solvePart2(input: List<String>): String {
        return input.sumOf { processLinePart2(it) }.toString()
    }

    private fun processLinePart2(line: String): Int {
        var total = 0
        var left = 0
        var right = line.length - 1
        var foundFirst = false
        var foundSecond = false
        while (left <= right && (!foundFirst || !foundSecond)) {
            if (!foundFirst) {
                val leftIsDigit = line.getDigit(left)
                if (leftIsDigit != null) {
                    total += leftIsDigit * 10
                    foundFirst = true
                }
            }

            if (!foundSecond) {
                val rightIsDigit = line.getDigit(right)
                if (rightIsDigit != null) {
                    total += rightIsDigit
                    foundSecond = true
                }
            }
            if (!foundFirst) left++
            if (!foundSecond) right--

        }
        return total
    }

    private fun String.getDigit(index: Int): Int? {
        if (this[index].isDigit()) {
            return this[index].digitToInt()
        }

        return (3..5).firstNotNullOfOrNull {
            if (index + it > this.length) {
                return@firstNotNullOfOrNull null
            }
            val subString = this.substring(index, index + it)
            spellingNumbersMap[subString]
        }
    }
}
