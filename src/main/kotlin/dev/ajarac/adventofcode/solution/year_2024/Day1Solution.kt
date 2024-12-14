package dev.ajarac.adventofcode.solution.year_2024

import dev.ajarac.adventofcode.solution.Solution
import java.util.PriorityQueue
import kotlin.math.abs

object Day1Solution : Solution(2024, 1, 1) {
    override fun solvePart1(input: List<String>): String {
        var queue1 = PriorityQueue<Int>()
        var queue2 = PriorityQueue<Int>()
        for(value in input) {
            var numbers = value.split("   ").map { it.toInt() }
            queue1.add(numbers.first())
            queue2.add(numbers.last())
        }
        var result = 0
        while(queue1.isNotEmpty()) {
            result += abs(queue1.poll() - queue2.poll())
        }

        return result.toString()
    }
}