package dev.ajarac.adventofcode.solution.year_2021

import dev.ajarac.adventofcode.solution.Solution
import dev.ajarac.adventofcode.util.Point

object Day13Solution : Solution(2021, 13, 1) {
    private fun Int.creaseAt(crease: Int): Int =
        if (this < crease) this else (crease * 2) - this

    private fun Set<Point>.crease(instruction: Point): Set<Point> =
        if (instruction.first != 0) this.map { it.copy(first = it.first.creaseAt(instruction.first)) }.toSet()
        else this.map { it.copy(second = it.second.creaseAt(instruction.second)) }.toSet()

    override fun solvePart1(input: List<String>): String {
        val paper: Set<Point> = parsePoints(input)
        val instructions: List<Point> = parseInstructions(input)
        return paper.crease(instructions.first()).size.toString()
    }

    override fun solvePart2(input: List<String>): String {
        val paper: Set<Point> = parsePoints(input)
        val instructions: List<Point> = parseInstructions(input)
        instructions.fold(paper) { p, instruction -> p.crease(instruction) }.printout()
        return ""
    }


    private fun parsePoints(input: List<String>): Set<Point> =
        input.takeWhile { it.isNotEmpty() }
            .map { it.split(",") }
            .map { Point(it.first().toInt(), it.last().toInt()) }
            .toSet()

    private fun parseInstructions(input: List<String>): List<Point> =
        input.takeLastWhile { it.isNotEmpty() }
            .map { it.split("=") }
            .map {
                if (it.first().endsWith("y")) {
                    Point(0, it.last().toInt())
                } else {
                    Point(it.last().toInt(), 0)
                }
            }


    private fun Set<Point>.printout() {
        (0..this.maxOf { it.second }).forEach { y ->
            (0..this.maxOf { it.first }).forEach { x ->
                print(if (Point(x, y) in this) "█" else " ")
            }
            println()
        }
    }

}
