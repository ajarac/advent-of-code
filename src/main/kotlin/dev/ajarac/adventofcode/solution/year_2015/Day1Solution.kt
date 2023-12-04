package dev.ajarac.adventofcode.solution.year_2015

import dev.ajarac.adventofcode.solution.Solution

object Day1Solution : Solution(2015, 1, 1) {

    override fun solvePart1(input: List<String>): String {
        return input.first().fold(0) { floor, char ->
            when (char) {
                '(' -> floor + 1
                ')' -> floor - 1
                else -> floor
            }
        }.toString()
    }

    override fun solvePart2(input: List<String>): String {
        var floor = 0
        var index = 0
        val line = input.first().toCharArray()
        while (floor > -1) {
            when (line[index]) {
                '(' -> floor++
                ')' -> floor--
            }
            index++;
        }
        return "$index"
    }

}
