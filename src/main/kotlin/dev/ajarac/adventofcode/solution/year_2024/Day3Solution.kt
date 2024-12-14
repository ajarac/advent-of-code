package dev.ajarac.adventofcode.solution.year_2024

import dev.ajarac.adventofcode.solution.Solution

object Day3Solution : Solution(2024, 3, 1) {
    override fun solvePart1(input: List<String>): String {
        val regex: Regex = "mul\\([0-9]+,[0-9]+\\)".toRegex()
        return input
            .flatMap { line -> regex.findAll(line) }
            .sumOf { multiply(it.groupValues.first()) }
            .toString()
    }

    private fun multiply(input: String): Int {
        var numbers = input.removePrefix("mul(").removeSuffix(")").split(",").map { it.toInt() }
        return numbers.first() * numbers.last()
    }
}