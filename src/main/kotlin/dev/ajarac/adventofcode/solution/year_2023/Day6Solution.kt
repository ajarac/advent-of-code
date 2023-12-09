package dev.ajarac.adventofcode.solution.year_2023

import dev.ajarac.adventofcode.solution.Solution
import kotlin.math.ceil
import kotlin.math.floor
import kotlin.math.pow
import kotlin.math.sqrt

object Day6Solution : Solution(2023, 6, 1) {

    //
    data class RecordRace(val time: Double, val distance: Double) {
        fun getWaysToImprove(): Int {
            val rootSquare = sqrt(time.pow(2.0) - 4 * distance)
            return floor((time + rootSquare) / 2).toInt() - ceil((time - rootSquare) / 2).toInt() + 1
        }
    }

    override fun solvePart1(input: List<String>): String {
        val parsed = input.map { line ->
            line.substringAfter(":").split(" ")
                .filter { it.isNotBlank() }
                .map { it.trim().toDouble() }
        }
        val recordRaces = parsed.first().zip(parsed.last()) { time, distance -> RecordRace(time, distance) }
            .map { it.getWaysToImprove() }
        return recordRaces.reduce { acc, i -> acc * i }.toString()
    }


    override fun solvePart2(input: List<String>): String {
        val parsed = input.map { it.substringAfter(":").trim().replace(" ", "") }
        val recordRace = RecordRace(parsed.first().toDouble(), parsed.last().toDouble())
        return recordRace.getWaysToImprove().toString()
    }
}
