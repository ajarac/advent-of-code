package dev.ajarac.adventofcode.solution

import kotlin.streams.toList

object Day8Solution : Solution(2021, 8, 1) {
    private val String.sorted: String
        get() = this.toSortedSet().joinToString("")

    override fun solvePart1(input: List<String>): String {
        val lengths = intArrayOf(2, 3, 4, 7)
        val count = input.map { it.split("|").last().trim().split(" ") }.flatten()
            .count { lengths.contains(it.length) }
        return "$count"
    }

    override fun solvePart2(input: List<String>): String {
        val data = input.map { it.split(" | ").map { a-> a.split(" ") }}
        val sum = data.map {
            val numbers = extractAllNumbers(it[0].toMutableList())
            it[1].map { segments -> numbers [segments.sorted]}.fold(0) {output, number -> output * 10 + number!! }
        }.sum()
        return "$sum"
    }

    private fun extractAllNumbers(numbers: MutableList<String>): Map<String, Int> {
        val one = numbers.find { it.length == 2 }!!; numbers -= one
        val four = numbers.find { it.length == 4 }!!; numbers -= four
        val seven = numbers.find { it.length == 3 }!!; numbers -= seven
        val eight = numbers.find { it.length == 7 }!!; numbers -= eight
        val nine = numbers.find { nine ->
            val nineChars = nine.chars().toList(); four.chars().allMatch { nineChars.contains(it) }
        }!!; numbers -= nine
        val zero = numbers.find { zero ->
            val zeroChars = zero.chars().toList(); zero.length == 6 && one.chars()
            .allMatch { zeroChars.contains(it) }
        }!!; numbers -= zero
        val six = numbers.find { it.length == 6 }!!; numbers -= six
        val three = numbers.find { three ->
            val threeChars = three.chars().toList(); one.chars()
            .allMatch { threeChars.contains(it) }
        }!!; numbers -= three
        val five = numbers.find { five ->
            val twoChars = five.chars(); twoChars.allMatch {
            six.chars().toList().contains(it)
        }
        }!!; numbers -= five
        val two = numbers[0]

        return listOf(
            one.sorted to 1,
            two.sorted to 2,
            three.sorted to 3,
            four.sorted to 4,
            five.sorted to 5,
            six.sorted to 6,
            seven.sorted to 7,
            eight.sorted to 8,
            nine.sorted to 9,
            zero.sorted to 0
        ).toMap()
    }

}
