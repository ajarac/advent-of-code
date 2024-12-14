package dev.ajarac.adventofcode.solution.year_2024

import dev.ajarac.adventofcode.solution.Solution

object Day3Solution : Solution(2024, 3, 1) {
    override fun solvePart1(input: List<String>): String {
        val regex: Regex = "mul\\([0-9]+,[0-9]+\\)".toRegex()
        return input
            .flatMap { regex.findAll(it) }
            .sumOf { multiply(it.groupValues.first()) }
            .toString()
    }

    override fun solvePart2(input: List<String>): String {
        val regex: Regex = "mul\\([0-9]+,[0-9]+\\)|do\\(\\)|don't\\(\\)".toRegex()

        val instructions = input.flatMap { regex.findAll(it) }.map { it.groupValues.first() }
        var shouldOperate = true
        var result = 0
        for(instruction in instructions) {
            if (instruction.contains("do()")) {
                shouldOperate = true
            } else if(instruction.contains("don't()")) {
                shouldOperate = false
            } else if(shouldOperate && instruction.contains("mul")) {
                result += multiply(instruction)
            }
        }

        return result.toString()
    }

    private fun multiply(input: String): Int {
        var numbers = input.removePrefix("mul(").removeSuffix(")").split(",").map { it.toInt() }
        return numbers.first() * numbers.last()
    }
}