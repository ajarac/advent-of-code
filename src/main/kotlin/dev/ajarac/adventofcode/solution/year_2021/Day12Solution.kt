package dev.ajarac.adventofcode.solution.year_2021

import dev.ajarac.adventofcode.solution.Solution

object Day12Solution : Solution(2021, 12, 1) {
    private const val START = "start"
    private val END = "end"

    override fun solvePart1(input: List<String>): String {
        val paths = generatePaths(input)
        return "${calculate(paths, true)}"
    }

    override fun solvePart2(input: List<String>): String {
        val paths = generatePaths(input)
        return "${calculate(paths, false)}"
    }

    private fun generatePaths(input: List<String>): MutableMap<String, List<String>> {
        val paths = mutableMapOf<String, List<String>>()
        input.map { it.split("-") }.forEach { (a, b) ->
            paths[a] = (paths[a] ?: listOf()) + b
            paths[b] = (paths[b] ?: listOf()) + a
        }
        return paths
    }

    private fun calculate(paths: MutableMap<String, List<String>>, isPart1: Boolean): Int {
        var count = 0
        val queue = ArrayDeque<Triple<String, List<String>, Boolean>>()
        queue.add(Triple(START, listOf(START), false))
        while (queue.isNotEmpty()) {
            val (curr, visited, twice) = queue.removeFirst()
            if (curr == END) {
                count++
            } else {
                for (next in paths[curr]!!) {
                    if (isPart1) {
                        calculateFirst(next, visited, queue)
                    } else {
                        calculateSecond(next, visited, queue, twice)
                    }

                }
            }
        }
        return count
    }

    private fun calculateFirst(
        next: String,
        visited: List<String>,
        queue: ArrayDeque<Triple<String, List<String>, Boolean>>
    ) {
        if (next !in visited) {
            calculateNewVisited(next, visited, queue, false)
        }
    }

    private fun calculateSecond(
        next: String,
        visited: List<String>,
        queue: ArrayDeque<Triple<String, List<String>, Boolean>>,
        twice: Boolean
    ) {
        if (next !in visited) {
            calculateNewVisited(next, visited, queue, twice)
        } else if (!twice && next != "start") {
            queue.add(Triple(next, visited, true))
        }
    }

    private fun calculateNewVisited(
        next: String,
        visited: List<String>,
        queue: ArrayDeque<Triple<String, List<String>, Boolean>>,
        twice: Boolean
    ) {
        val newVisited =
            if (next == next.lowercase()) visited + next else visited
        queue.add(Triple(next, newVisited, twice))
    }
}
