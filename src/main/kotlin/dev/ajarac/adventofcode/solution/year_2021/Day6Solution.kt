package dev.ajarac.adventofcode.solution.year_2021

import dev.ajarac.adventofcode.solution.Solution

object Day6Solution : Solution(2021, 6, 1) {
    override fun solvePart1(input: List<String>): String {
        val lanternFishers = buildLanternFishers(input)

        simulateDays(80, lanternFishers)

        val count = lanternFishers.values.sum()
        return "$count"
    }

    override fun solvePart2(input: List<String>): String {
        val lanternFishers = buildLanternFishers(input)

        simulateDays(256, lanternFishers)

        val count = lanternFishers.values.sum()
        return "$count"
    }

    private fun buildLanternFishers(input: List<String>): LanternFishers {
        val lanternFishers = (0L..8L).associateWith { 0L }.toMutableMap()

        input.first().split(",")
            .map(String::toLong)
            .forEach { lanternFishers[it] = lanternFishers[it]!! + 1 }

        return lanternFishers
    }

    private fun simulateDays(days: Int, lanternFishers: LanternFishers) {

        repeat(days) {
            val prevAge0 = lanternFishers[0]!!
            lanternFishers[0] = lanternFishers[1]!!
            lanternFishers[1] = lanternFishers[2]!!
            lanternFishers[2] = lanternFishers[3]!!
            lanternFishers[3] = lanternFishers[4]!!
            lanternFishers[4] = lanternFishers[5]!!
            lanternFishers[5] = lanternFishers[6]!!
            lanternFishers[6] = lanternFishers[7]!! + prevAge0
            lanternFishers[7] = lanternFishers[8]!!
            lanternFishers[8] = prevAge0
        }
    }
}

typealias LanternFishers = MutableMap<Long, Long>
