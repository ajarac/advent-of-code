package dev.ajarac.adventofcode.solution.year_2023

import dev.ajarac.adventofcode.solution.Solution
import kotlin.math.abs


typealias Point = Pair<Long, Long>

object Day11Solution : Solution(2023, 11, 1) {


    override fun solvePart1(input: List<String>): String {
        val galaxies = getGalaxies(input, 1L)
        val totalSteps = calculatePaths(galaxies)
        return totalSteps.toString()
    }

    override fun solvePart2(input: List<String>): String {
        val galaxies = getGalaxies(input, 1000000L - 1L)
        val totalSteps = calculatePaths(galaxies)
        return totalSteps.toString()
    }

    private fun getGalaxies(input: List<String>, expansionBySpace: Long): List<Point> {
        val rowsWithSpaces = calculateRowsWithSpaces(input)
        val columnsWithSpaces = calculateColumnsWithSpaces(input)
        return calculateGalaxies(input, rowsWithSpaces, columnsWithSpaces, expansionBySpace)
    }

    private fun calculateGalaxies(
        input: List<String>,
        rowsWithSpaces: List<Int>,
        columnsWithSpaces: List<Int>,
        expansionBySpace: Long
    ): List<Point> {
        val galaxies = mutableListOf<Point>()
        input.forEachIndexed { y, line ->
            line.forEachIndexed { x, c ->
                if (c == '#') {
                    val rowSpaces = rowsWithSpaces.indexOfLast { y > it }.toLong() + 1L
                    val columnSpaces = columnsWithSpaces.indexOfLast { x > it }.toLong() + 1L
                    val pointX = if (columnSpaces > 0L) x + columnSpaces * expansionBySpace else x.toLong()
                    val pointY = if (rowSpaces > 0L) y + rowSpaces * expansionBySpace else y.toLong()
                    val point = Point(pointX, pointY)
                    galaxies.add(point)
                }
            }
        }
        return galaxies
    }

    private fun calculateRowsWithSpaces(input: List<String>): MutableList<Int> {
        val rowsWithSpaces = mutableListOf<Int>()
        input.forEachIndexed { index, line ->
            if (!line.contains('#')) {
                rowsWithSpaces.add(index)
            }
        }
        return rowsWithSpaces
    }

    private fun calculateColumnsWithSpaces(input: List<String>): MutableList<Int> {
        val columnsWithSpaces = mutableListOf<Int>()
        var index = 0
        while (index < input[0].length) {
            val isEmptyColumn = input.all { it[index] != '#' }
            if (isEmptyColumn) {
                columnsWithSpaces.add(index)
            }
            index++
        }
        return columnsWithSpaces
    }

    private fun calculatePaths(galaxies: List<Point>): Long {
        var totalSteps = 0L
        for (i in galaxies.indices) {
            for (j in i + 1 until galaxies.size) {
                val distanceX = abs(galaxies[i].first - galaxies[j].first)
                val distanceY = abs(galaxies[i].second - galaxies[j].second)
                totalSteps += distanceX + distanceY
            }
        }
        return totalSteps
    }
}

