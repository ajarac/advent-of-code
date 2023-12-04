package dev.ajarac.adventofcode.solution.year_2015

import dev.ajarac.adventofcode.solution.Solution

object Day3Solution : Solution(2015, 3, 1) {
    data class Point(var row: Int, var column: Int) {
        fun str() = "$row,$column"
        fun move(move: Char) {
            when (move) {
                '^' -> row++
                'v' -> row--
                '>' -> column++
                '<' -> column--
            }
        }
    }


    override fun solvePart1(input: List<String>): String {
        val set = mutableSetOf<String>()
        val position = Point(0, 0)
        set.add(position.str())
        for (move in input.first()) {
            position.move(move)
            set.add(position.str())
        }
        return "${set.size}"
    }

    override fun solvePart2(input: List<String>): String {
        val set = mutableSetOf<String>()
        val positionSanta = Point(0, 0)
        val positionRobot = Point(0, 0)
        set.add(positionSanta.str())
        for ((i, move) in input.first().withIndex()) {
            if (i % 2 == 0) {
                positionSanta.move(move)
                set.add(positionSanta.str())
            } else {
                positionRobot.move(move)
                set.add(positionRobot.str())
            }
        }
        return "${set.size}"
    }
}
