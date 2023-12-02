package dev.ajarac.adventofcode

import dev.ajarac.adventofcode.solution.Solution
import dev.ajarac.adventofcode.util.SolutionUtils
import com.github.ajalt.mordant.terminal.Terminal

val terminal = Terminal()

fun main(args: Array<String>) {
    SolutionUtils.Solutions.forEach(Solution::solve)
}
