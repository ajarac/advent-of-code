package dev.ajarac.adventofcode.solution

import com.github.ajalt.mordant.rendering.TextColors.*
import dev.ajarac.adventofcode.util.BenchmarkUtils
import dev.ajarac.adventofcode.util.InputUtils
import dev.ajarac.adventofcode.terminal
import java.net.URL

abstract class Solution(private val year: Int, private val day: Int, private val iterations: Int) {
    fun solve() {
        terminal.println(brightCyan("\uD83D\uDD25 Solving day ${day} of year ${year}..."))

        val part1 = BenchmarkUtils.getAverageMs(iterations) {
            solvePart1(InputUtils.getInput(day))
        }

        if (part1.result == "not present!") {
            terminal.println(brightRed("=> ⭐  Skipped part one!"))
        } else {
            terminal.println(brightGreen("=> ⭐  Solved part one: ${part1.result}!"))
            terminal.println(brightMagenta("=>    ...in ${part1.average}ms."))
        }

        val part2 = BenchmarkUtils.getAverageMs(iterations) {
            solvePart2(InputUtils.getInput(day))
        }

        if (part2.result == "not present!") {
            terminal.println(brightRed("=> ⭐  Skipped part two!"))
        } else {
            terminal.println(brightGreen("=> ⭐⭐ Solved part two: ${part2.result}!"))
            terminal.println(brightMagenta("=>    ...in ${part2.average}ms."))
        }

        terminal.println(brightYellow("⭐ Solved day ${day}!"))
    }

    open fun solvePart1(input: URL): String = "not present!"

    open fun solvePart2(input: URL): String = "not present!"
}