package dev.ajarac.adventofcode.solution.year_2024

import dev.ajarac.adventofcode.solution.Solution

object Day2Solution : Solution(2024, 2, 1) {
    override fun solvePart1(input: List<String>): String {
        return input.count { list -> isSafeList(list.toListInt()) }.toString()
    }

    override fun solvePart2(input: List<String>): String {
        return input.count { list -> generatePermutations(list.toListInt()).any { isSafeList(it) } }.toString()
    }

    private fun generatePermutations(input: List<Int>) = sequence {
        yield(input)
        for (index in input.indices) {
            var newList = mutableListOf<Int>()
            for (i in input.indices) {
                if (i != index) {
                    newList.add(input[i])
                }
            }
            yield(newList)
        }
    }


    private fun isSafeList(numbers: List<Int>): Boolean {
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

        return isIncreasing || isDecreasing
    }

    private fun String.toListInt() = this.split(" ").map { it.toInt() }
}