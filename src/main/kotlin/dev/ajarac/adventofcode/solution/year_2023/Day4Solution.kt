package dev.ajarac.adventofcode.solution.year_2023

import dev.ajarac.adventofcode.solution.Solution
import kotlin.math.pow

object Day4Solution : Solution(2023, 4, 1) {

    data class Card(val id: Int, val winNumbers: List<Int>, val numbers: List<Int>) {

        companion object {
            fun fromInput(input: String): Card {
                val split = input.split(":")
                val id = split.first().split(" ").last().toInt()
                val allNumbers = split.last().split("|")
                val winNumbers = getNumbers(allNumbers.first())
                val numbers = getNumbers(allNumbers.last())
                return Card(id, winNumbers, numbers)
            }

            private fun getNumbers(input: String): List<Int> =
                input.trim().split(" ").filter { it.isNotEmpty() }.map(String::toInt)
        }

        fun countWinNumbers() = winNumbers.count { numbers.contains(it) }
    }

    override fun solvePart1(input: List<String>): String {
        return input
            .asSequence()
            .map { Card.fromInput(it) }
            .map { it.countWinNumbers() }
            .sumOf { countWinNumbers ->
                when {
                    countWinNumbers == 1 -> 1.0
                    countWinNumbers > 1 -> 2.0.pow(countWinNumbers - 1)
                    else -> 0.0
                }
            }
            .toString()
    }

    override fun solvePart2(input: List<String>): String {
        return input
            .asSequence()
            .map { Card.fromInput(it) }
            .fold(List(input.size) { 1 }.toMutableList()) { list, card ->
                val countWinNumbers = card.countWinNumbers()
                val cardIndex = card.id - 1
                for (i in 1..countWinNumbers) {
                    val index = cardIndex + i
                    list[index] += list[cardIndex]
                }
                list
            }.sum().toString()
    }
}
