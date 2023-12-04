package dev.ajarac.adventofcode.solution.year_2015

import dev.ajarac.adventofcode.solution.Solution

object Day2Solution : Solution(2015, 2, 1) {

    override fun solvePart1(input: List<String>): String {
        return input
            .map { it.split("x").map { char -> char.toInt() } }
            .sumOf { (l, w, h) ->
                val lw = l * w
                val wh = w * h
                val hl = h * l
                2 * lw + 2 * wh + 2 * hl + minOf(lw, wh, hl)
            }.toString()
    }

    override fun solvePart2(input: List<String>): String {
        return input
            .map { it.split("x").map { char -> char.toInt() } }
            .sumOf { (l, w, h) ->
                val lw = 2 * l + 2 * w
                val wh = 2 * w + 2 * h
                val hl = 2 * h + 2 * l
                minOf(lw, wh, hl) + l * w * h
            }.toString()
    }
}
