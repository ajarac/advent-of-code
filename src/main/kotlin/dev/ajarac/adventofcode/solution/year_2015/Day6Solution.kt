package dev.ajarac.adventofcode.solution.year_2015

import dev.ajarac.adventofcode.solution.Solution


object Day6Solution: Solution(2015, 6, 1) {

        private enum class ActionType {
            TURN_ON,
            TURN_OFF,
            TOGGLE
        }

        private data class Action(val type: ActionType, val from: Pair<Int, Int>, val to: Pair<Int, Int>)

        private fun getAction(input: String): ActionType {
            return when {
                input.contains("toggle") -> ActionType.TOGGLE
                input.contains("on") -> ActionType.TURN_ON
                input.contains("off") -> ActionType.TURN_OFF
                else -> throw IllegalArgumentException("Invalid input: $input")
            }
        }

        private fun parseAction(input: String): Action {
            val split = input.split("through")
            val action = getAction(split.first())
            val part1 = split.first().split(" ")
            val from = part1[part1.size - 2].split(",").map { it.trim().toInt() }
            val to = split[1].split(",").map { it.trim().toInt() }
            return Action(action, Pair(from[0], from[1]), Pair(to[0], to[1]))
        }

        private fun doAction1(grid: Array<IntArray>, action: Action) {
            for (row in action.from.first..action.to.first) {
                for (column in action.from.second..action.to.second) {
                    when (action.type) {
                        ActionType.TOGGLE -> grid[row][column] = if (grid[row][column] == 1) 0 else 1
                        ActionType.TURN_ON -> grid[row][column] = 1
                        ActionType.TURN_OFF -> grid[row][column] = 0
                    }
                }
            }
        }

        private fun doAction2(grid: Array<IntArray>, action: Action) {
            for (row in action.from.first..action.to.first) {
                for (column in action.from.second..action.to.second) {
                    when (action.type) {
                        ActionType.TOGGLE -> grid[row][column] += 2
                        ActionType.TURN_ON -> grid[row][column]++
                        ActionType.TURN_OFF -> if (grid[row][column] > 0) grid[row][column]--
                    }
                }
            }
        }

        override fun solvePart1(input: List<String>): String {
            return common(input, ::doAction1).toString()
        }

        override fun solvePart2(input: List<String>): String {
            return common(input, ::doAction2).toString()
        }

    private fun common(input: List<String>, actionFunc: (grid: Array<IntArray>, action: Action) -> Unit): Int {
        val grid = Array(1000) { IntArray(1000) }
        val actions = input.filter { it.isNotEmpty() }
        for (actionData in actions) {
            val action = parseAction(actionData)
            actionFunc(grid, action)
        }
        var counter = 0
        for (row in grid.indices) {
            for (column in grid[row].indices) {
                counter += grid[row][column]
            }
        }
        return counter
    }

}
