package dev.ajarac.adventofcode.solution

import com.github.ajalt.mordant.rendering.TextColors.*
import dev.ajarac.adventofcode.util.BenchmarkUtils
import dev.ajarac.adventofcode.util.InputUtils
import dev.ajarac.adventofcode.terminal
import java.net.URL

abstract class Solution(private val year: Int, private val day: Int, private val iterations: Int) {

    companion object {
        private const val NOT_PRESENT = "not present!"
    }

    fun solve() {
        terminal.println(brightCyan("\uD83D\uDD25 Solving day ${day} of year ${year}..."))
        val input = InputUtils.getInput(year, day)
        val inputLines = input.readText().trimEnd().lines()
        val part1 = BenchmarkUtils.getAverageMs(iterations) {
            solvePart1(inputLines)
        }

        if (part1.result == NOT_PRESENT) {
            terminal.println(brightRed("=> ⭐  Skipped part one!"))
        } else {
            terminal.println(brightGreen("=> ⭐  Solved part one: ${part1.result}!"))
            terminal.println(brightMagenta("=>    ...in ${part1.average}ms."))
        }

        val part2 = BenchmarkUtils.getAverageMs(iterations) {
            solvePart2(inputLines)
        }

        if (part2.result == "not present!") {
            terminal.println(brightRed("=> ⭐  Skipped part two!"))
        } else {
            terminal.println(brightGreen("=> ⭐⭐ Solved part two: ${part2.result}!"))
            terminal.println(brightMagenta("=>    ...in ${part2.average}ms."))
        }

        terminal.println(brightYellow("⭐ Solved day ${day}!"))
    }

    open fun solvePart1(input: List<String>): String = NOT_PRESENT

    open fun solvePart2(input: List<String>): String = NOT_PRESENT
}
