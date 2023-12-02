package dev.ajarac.adventofcode.solution.year_2021

import dev.ajarac.adventofcode.solution.Solution
import dev.ajarac.adventofcode.util.*


object Day11Solution : Solution(2021, 11, 1) {

    fun Point.getNeighbours() = listOf(
        Point(this.first + 1, this.second),
        Point(this.first + 1, this.second + 1),
        Point(this.first, this.second + 1),
        Point(this.first - 1, this.second + 1),
        Point(this.first - 1, this.second),
        Point(this.first - 1, this.second - 1),
        Point(this.first, this.second - 1),
        Point(this.first + 1, this.second - 1),
    )

    fun Point.isOutside(size: Int) = this.first !in 0 until size || this.second !in 0 until size

    class Cavern(private val matrix: Matrix) {
        private var flashes = 0
        fun getFlashes() = flashes

        companion object {
            private val SIZE = 10
            fun build(input: List<String>): Cavern {
                val matrix: Matrix = hashMapOf()
                for (x in input.indices) {
                    matrix[x] = hashMapOf()
                    for (y in input[x].indices) {
                        matrix[x]!![y] = input[x][y].toString().toInt()
                    }
                }
                return Cavern(matrix)
            }
        }

        fun iterate(): Boolean {
            var flashesInThisIterate = 0
            for (x in matrix.keys) {
                for (y in matrix[x]!!.keys) {
                    increase(Point(x, y))
                }
            }
            for (x in matrix.keys) {
                for (y in matrix[x]!!.keys) {
                    val point = Point(x, y)
                    if (matrix.getValue(point) > 9) {
                        flashesInThisIterate++
                        matrix.setValue(point, 0)
                    }
                }
            }
            return flashesInThisIterate == SIZE * SIZE
        }

        private fun increase(point: Point) {
            if (point.isOutside(SIZE)) {
                return
            }
            if (!matrix.has(point)) {
                return
            }
            matrix.increment(point)
            if (matrix.getValue(point) == 10) {
                flashes++
                point.getNeighbours().forEach { increase(it) }
            }
        }

    }

    override fun solvePart1(input: List<String>): String {
        val cavern = Cavern.build(input)
        repeat(100) {
            cavern.iterate()
        }

        return "${cavern.getFlashes()}"
    }

    override fun solvePart2(input: List<String>): String {
        val cavern = Cavern.build(input)
        var completeFlashed = false
        var counter = 0
        while (!completeFlashed) {
            completeFlashed = cavern.iterate()
            counter++
        }
        return "$counter"
    }
}
