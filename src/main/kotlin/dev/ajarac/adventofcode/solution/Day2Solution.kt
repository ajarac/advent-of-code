package dev.ajarac.adventofcode.solution

object Day2Solution : Solution(2021, 2, 1) {
    override fun solvePart1(input: List<String>): String {
        var forward = 0
        var depth = 0

        input.forEach { line ->
            val value = line.substringAfter(' ').toInt()

            when (line[0]) {
                'f' -> forward += value
                'u' -> depth -= value
                'd' -> depth += value
            }
        }

        return "${forward * depth}"
    }

    override fun solvePart2(input: List<String>): String {
        var aim = 0
        var depth = 0
        var forward = 0

        input.forEach { line ->
            val value = line.substringAfter(' ').toInt()

            when (line[0]) {
                'f' -> {
                    forward += value
                    depth += value * aim
                }

                'u' -> aim -= value
                'd' -> aim += value
            }
        }

        return "${forward * depth}"
    }
}