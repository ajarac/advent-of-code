package dev.ajarac.adventofcode.solution.year_2024

import dev.ajarac.adventofcode.solution.Solution

object Day2Solution : Solution(2024, 2, 1) {
    override fun solvePart1(input: List<String>): String {
        var counter = 0

        for (list in input) {
            val numbers = list.split(" ").map { it.toInt() }

            var isIncreasing = true
            var isDecreasing = true
            var previous = numbers.first()
            for (index in 1..numbers.size - 1) {
                var compare = numbers[index]
                if (isIncreasing && previous >= compare || compare - previous > 3) {
                    isIncreasing = false
                }
                if (isDecreasing && previous <= compare || previous - compare > 3) {
                    isDecreasing = false
                }
                previous = numbers[index]
            }

            if (isIncreasing || isDecreasing) {
                counter++
            }
        }

        return counter.toString()
    }
}