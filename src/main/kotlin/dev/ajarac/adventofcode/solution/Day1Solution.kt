package dev.ajarac.adventofcode.solution

object Day1Solution : Solution(2021, 1, 1) {
    override fun solvePart1(input: List<String>): String {
        val lines = input.map(String::toInt)
        val count = (1 until lines.size).count { index -> lines[index] > lines[index - 1] }

        return "$count"
    }

    override fun solvePart2(input: List<String>): String {
        val lines = input.map(String::toInt).windowed(3) { it.sum() }
        val count = (1 until lines.size).count { index -> lines[index] > lines[index - 1] }

        return "$count"
    }
}