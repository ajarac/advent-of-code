package dev.ajarac.adventofcode.solution.year_2021

import dev.ajarac.adventofcode.solution.Solution
import java.util.*
import kotlin.math.abs


object Day15Solution : Solution(2021, 15, 1) {
    private enum class Direction {
        UP, DOWN, LEFT, RIGHT
    }

    override fun solvePart1(input: List<String>): String {
        val rowSize = input.size
        val colSize = input[0].length
        val matrix = Array(rowSize) { IntArray(colSize) }
        input.initMatrix(matrix = matrix)
        return dijkstra(Pair(0, 0), matrix).toString()
    }

    override fun solvePart2(input: List<String>): String {
        val rowSize = input.size
        val colSize = input[0].length
        val matrix = Array(rowSize * 5) { IntArray(colSize * 5) }

        fun Int.nextLevel(): Int = if (this > 9) abs(this - 9) else this

        for (row in 0 until rowSize) {
            for (col in 0 until colSize) {
                var counter = 1
                matrix[row][col] = input[row][col].digitToInt()

                repeat(4) {
                    val newCol = col + (colSize * counter)
                    val newRow = row + (rowSize * counter)
                    matrix[row][newCol] = (matrix[row][col] + it + 1).nextLevel()
                    matrix[newRow][col] = (matrix[row][col] + it + 1).nextLevel()

                    var newCounter = 1
                    repeat(4) { count ->
                        matrix[row + (rowSize * newCounter)][col + (colSize * counter)] =
                            (matrix[row][newCol] + count + 1).nextLevel()
                        ++newCounter
                    }

                    ++counter
                }
            }
        }

        return dijkstra(Pair(0, 0), matrix).toString()
    }

    private val isLocationValid: (row: Int, col: Int, rowSize: Int, colSize: Int) -> Boolean =
        { row, col, rowSize, colSize ->
            (row in 0 until rowSize) && (col in 0 until colSize)
        }

    private val getLocationBasedOnDirection: (row: Int, col: Int, direction: Direction) -> Pair<Int, Int> =
        { row, col, direction ->
            when (direction) {
                Direction.UP -> Pair(row - 1, col)
                Direction.DOWN -> Pair(row + 1, col)
                Direction.LEFT -> Pair(row, col - 1)
                Direction.RIGHT -> Pair(row, col + 1)
            }
        }


    private fun Array<IntArray>.getAdjacent(row: Int, col: Int): List<Pair<Int, Int>> {
        val adjacentList = mutableListOf<Pair<Int, Int>>()
        val rowSize = this.size
        val colSize = this[0].size
        for (direction in Direction.values()) {
            val adjacent = getLocationBasedOnDirection(row, col, direction)
            if (isLocationValid(adjacent.first, adjacent.second, rowSize, colSize)) {
                adjacentList.add(Pair(adjacent.first, adjacent.second))
            }
        }

        return adjacentList
    }

    private fun List<String>.initMatrix(matrix: Array<IntArray>) {
        val rowSize = size
        val colSize = this[0].length
        for (row in 0 until rowSize) {
            for (col in 0 until colSize) {
                matrix[row][col] = this[row][col].digitToInt()
            }
        }
    }

    private data class VertexDistancePair(
        val location: Pair<Int, Int>,
        val riskLevel: Int
    )

    private class VertexDistancePairComparator : Comparator<VertexDistancePair> {
        override fun compare(first: VertexDistancePair, second: VertexDistancePair): Int {
            return first.riskLevel.compareTo(second.riskLevel)
        }
    }

    private fun dijkstra(start: Pair<Int, Int>, matrix: Array<IntArray>): Int {
        val priorityQueue = PriorityQueue(VertexDistancePairComparator())
        val visited = hashSetOf<Pair<Int, Int>>()
        val totalRiskLevel = hashMapOf<Pair<Int, Int>, Int>()

        totalRiskLevel[start] = 0
        priorityQueue.add(VertexDistancePair(start, 0))

        while (priorityQueue.isNotEmpty()) {
            val (location, riskLevel) = priorityQueue.remove()
            visited.add(location)

            if (totalRiskLevel.getOrDefault(location, Int.MAX_VALUE) < riskLevel) continue

            for (adj in matrix.getAdjacent(location.first, location.second)) {
                if (visited.contains(adj)) continue
                val newRiskLevel = totalRiskLevel.getOrDefault(
                    location,
                    Int.MAX_VALUE
                ) + matrix[adj.first][adj.second]
                if (newRiskLevel < totalRiskLevel.getOrDefault(adj, Int.MAX_VALUE)) {
                    totalRiskLevel[adj] = newRiskLevel
                    priorityQueue.add(VertexDistancePair(adj, newRiskLevel))
                }
            }
        }

        return totalRiskLevel[Pair(matrix.size - 1, matrix[0].size - 1)]!!
    }
}
