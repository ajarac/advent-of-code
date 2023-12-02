package dev.ajarac.adventofcode.solution.year_2021

import dev.ajarac.adventofcode.solution.Solution
import kotlin.math.abs

object Day7Solution : Solution(2021, 7, 1) {

    object Fibonacci {
        private val cache = hashMapOf<Long, Long>()

        operator fun invoke(value: Long): Long {
            return cache[value] ?: calc(value)
        }

        private fun calc(value: Long): Long {
            return if (value == 0L || value == 1L) {
                cache[value] = value
                value
            } else {
                cache[value] = calc(value - 1) + value
                cache[value]!!
            }
        }
    }

    override fun solvePart1(input: List<String>): String {
        val list = input.first().split(",").map(String::toInt)
        val max = list.maxOrNull() ?: 0
        var fuel = Int.MAX_VALUE
        for (i in 0..max) {
            val sum = list.sumOf { abs(i - it) }
            if (sum < fuel) {
                fuel = sum
            }
        }
        return "$fuel"
    }

    override fun solvePart2(input: List<String>): String {
        val list = input.first().split(",").map(String::toLong)
        val max = list.maxOrNull() ?: 0
        var fuel = Long.MAX_VALUE
        for (i in 0..max) {
            val sum = list.sumOf { Fibonacci(abs(i - it)) }
            if (sum < fuel) {
                fuel = sum
            }
        }
        return "$fuel"
    }
}
