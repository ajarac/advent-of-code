package dev.ajarac.adventofcode.solution.year_2023

import dev.ajarac.adventofcode.solution.Solution

object Day9Solution : Solution(2023, 9, 1) {

    override fun solvePart1(input: List<String>): String {
        val history = input.asSequence().map { line -> line.split(" ").map { it.toInt() } }
        val result = history.sumOf { processHistory1(it) }
        return result.toString()
    }

    override fun solvePart2(input: List<String>): String {
        val history = input.asSequence().map { line -> line.split(" ").map { it.toInt() } }
        val result = history.sumOf { processHistory2(it) }
        return result.toString()
    }

    private fun processHistory2(history: List<Int>): Int {
        if (history.all { it == 0 }) {
            return 0
        }

        val newHistory = getNextHistory(history)

        return history.first() - processHistory2(newHistory)
    }

    private fun processHistory1(history: List<Int>): Int {
        if (history.all { it == 0 }) {
            return 0
        }

        val newHistory = getNextHistory(history)

        return history.last() + processHistory1(newHistory)
    }

    private fun getNextHistory(history: List<Int>): List<Int> {
        return history.foldIndexed(mutableListOf()) { index, list, value ->
            if (index == 0) {
                return@foldIndexed list
            }
            list.add(value - history[index - 1])
            list
        }
    }

}
