package dev.ajarac.adventofcode.solution.year_2021

import dev.ajarac.adventofcode.solution.Solution
import dev.ajarac.adventofcode.util.*


fun Point.getNeighbours(): List<Point> = listOf(
    Point(this.first - 1, this.second),
    Point(this.first + 1, this.second),
    Point(this.first, this.second - 1),
    Point(this.first, this.second + 1)
)

object Day9Solution : Solution(2021, 9, 1) {

    override fun solvePart1(input: List<String>): String {
        val matrix = buildMatrix(input)
        val lowPoints = getLowPoints(matrix)
        return lowPoints.sumOf { matrix.getValue(it) + 1 }.toString()
    }

    private fun getLowPoints(matrix: Matrix): ArrayList<Point> {
        val lowPoints: ArrayList<Point> = arrayListOf()
        for (x in matrix.keys) {
            for (y in matrix[x]!!.keys) {
                val point = Point(x, y)
                if (isTheLowerPoint(matrix, point)) {
                    lowPoints.add(point)
                }
            }
        }
        return lowPoints
    }

    override fun solvePart2(input: List<String>): String {
        val matrix = buildMatrix(input)
        val lowPoints = getLowPoints(matrix)
        val basins = lowPoints
            .map { calculateSizeBasin(matrix, it) }
            .sortedDescending()
            .take(3)
            .reduce { acc, i -> acc * i }
        return "$basins"
    }

    private fun calculateSizeBasin(matrix: Matrix, point: Point): Int =
        calculateBasin(matrix, point).size

    private fun calculateBasin(
        matrix: Matrix,
        point: Point,
        points: HashSet<String> = hashSetOf()
    ): Set<String> {
        points.add(point.str())
        point.getNeighbours()
            .filter { matrix.has(it) && !points.contains(it.str()) && matrix.getValue(it) != 9 }
            .forEach { calculateBasin(matrix, it, points) }
        return points
    }

    private fun buildMatrix(input: List<String>): Matrix {
        val matrix = hashMapOf<Int, HashMap<Int, Int>>()
        for (x in input.indices) {
            matrix[x] = hashMapOf()
            for (y in input[x].indices) {
                matrix[x]!![y] = input[x][y].toString().toInt()
            }
        }
        return matrix
    }


    private fun isTheLowerPoint(matrix: Matrix, point: Point): Boolean {
        return point.getNeighbours().filter { matrix.has(it) }
            .all { matrix.getValue(it) > matrix.getValue(point) }

    }
}
