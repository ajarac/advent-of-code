package dev.ajarac.adventofcode.solution.year_2023

import dev.ajarac.adventofcode.solution.Solution

object Day3Solution : Solution(2023, 3, 2) {

    private const val AVOID_SYMBOL = '.'
    private const val GEAR_SYMBOL = '*'

    override fun solvePart1(input: List<String>): String {
        var result = 0
        for (i in input.indices) {
            result += processLinePart1(input, i)
        }
        return result.toString()
    }

    private fun processLinePart1(input: List<String>, i: Int): Int {
        var result = 0
        val line = input[i]

        var left = 0
        var right = 0
        while (right < line.length) {
            if (!line[right].isDigit()) {
                right++
                left = right
                continue
            }

            while ((right + 1) < line.length && line[right + 1].isDigit()) {
                right++
            }

            val isValid1 = isValidSidesNumber(left, right, line)
            val isValid2 = isValidTopAndBottomLine(i, input, left, right)
            if (isValid1 || isValid2) {
                result += line.substring(left, right + 1).toInt()
            }
            right++
            left = right
        }
        return result
    }

    private fun isValidSidesNumber(left: Int, right: Int, line: String) = listOf(left - 1, right + 1).any {
        if (it < 0 || it >= line.length) {
            return@any false
        }
        val value = line[it]
        value != AVOID_SYMBOL && !value.isDigit()
    }

    private fun isValidTopAndBottomLine(
        i: Int,
        input: List<String>,
        left: Int,
        right: Int,
    ) = listOf(i - 1, i + 1).any { ii ->
        if (ii < 0 || ii >= input.size) {
            return@any false
        }
        (left - 1..right + 1).any secondAny@{ jj ->
            if (jj < 0 || jj >= input[i].length) {
                return@secondAny false
            }
            val value = input[ii][jj]
            value != AVOID_SYMBOL && !value.isDigit()
        }
    }

    override fun solvePart2(input: List<String>): String {
        var result = 0
        for (inputIndex in input.indices) {
            if (input[inputIndex].contains(GEAR_SYMBOL)) {
                input[inputIndex].forEachIndexed { gearIndex, _ ->
                    if (input[inputIndex][gearIndex] == GEAR_SYMBOL) {
                        result += searchGearNumbers(input, inputIndex, gearIndex)
                    }
                }
            }
        }
        return result.toString()
    }

    private fun searchGearNumbers(input: List<String>, inputIndex: Int, gearIndex: Int): Int {
        val gearNumbers = mutableSetOf<Int>()
        (inputIndex - 1..inputIndex + 1)
            .filter { ii -> ii >= 0 && ii < input.size }
            .forEach { ii ->
                (gearIndex - 1..gearIndex + 1)
                    .filter { jj -> jj >= 0 && jj < input[inputIndex].length }
                    .filter { jj -> input[ii][jj].isDigit() }
                    .forEach { jj -> gearNumbers.add(searchGearNumber(input[ii], jj)) }
            }
        if (gearNumbers.size != 2) {
            return 0
        }
        return gearNumbers.reduce { acc, it -> acc * it }
    }

    private fun searchGearNumber(line: String, i: Int): Int {
        var left = i
        var right = i
        while (left > 0 && line[left - 1].isDigit()) {
            left--
        }
        while ((right + 1) < line.length && line[right + 1].isDigit()) {
            right++
        }
        return line.substring(left, right + 1).toInt()
    }
}
