package dev.ajarac.adventofcode.solution.year_2023

import dev.ajarac.adventofcode.solution.Solution

object Day8Solution : Solution(2023, 8, 1) {
    private const val INIT = "AAA"
    private const val END = "ZZZ"

    enum class Instruction {
        LEFT, RIGHT;

        companion object {
            fun fromLine(line: String) = line.trim().toCharArray().map { fromChar(it) }
            private fun fromChar(char: Char) = when (char) {
                'L' -> LEFT
                'R' -> RIGHT
                else -> throw IllegalArgumentException("Invalid instruction: $char")
            }
        }
    }

    data class Intersection(val from: String, val left: String, val right: String) {
        fun getDirection(instruction: Instruction) = when (instruction) {
            Instruction.LEFT -> left
            Instruction.RIGHT -> right
        }

        fun isTheEnd() = from == END
        fun isTheEnd2() = from.endsWith('Z')
    }

    private fun buildIntersectionsMap(input: List<String>) = input.map { line ->
        val split = line.split("=")
        val from = split[0].trim()
        val left = split[1].split(",")[0].replace("(", "").trim()
        val right = split[1].split(",")[1].replace(")", "").trim()
        Intersection(from, left, right)
    }.fold(mutableMapOf<String, Intersection>()) { acc, intersection ->
        acc[intersection.from] = intersection
        acc
    }

    private fun walk(
        from: String,
        instructions: List<Instruction>,
        intersectionsMap: Map<String, Intersection>,
        counter: Int
    ): Int {
        val intersection = intersectionsMap[from] ?: throw IllegalArgumentException("Invalid intersection: $from")
        if (intersection.isTheEnd()) {
            return counter
        }

        val next = instructions.fold(from) { acc, instruction ->
            val currentIntersection =
                intersectionsMap[acc] ?: throw IllegalArgumentException("Invalid intersection: $acc")
            currentIntersection.getDirection(instruction)
        }

        return walk(next, instructions, intersectionsMap, counter + instructions.size)
    }

    private fun walk2(
        from: String,
        instructions: List<Instruction>,
        intersectionsMap: Map<String, Intersection>,
        counter: Long
    ): Long {
        val intersection = intersectionsMap[from] ?: throw IllegalArgumentException("Invalid intersection: $from")
        if (intersection.isTheEnd2()) {
            return counter
        }

        val next = instructions.fold(from) { acc, instruction ->
            val currentIntersection =
                intersectionsMap[acc] ?: throw IllegalArgumentException("Invalid intersection: $acc")
            currentIntersection.getDirection(instruction)
        }

        return walk2(next, instructions, intersectionsMap, counter + instructions.size)
    }

    private fun walkList(
        locations: List<String>,
        instructions: List<Instruction>,
        intersectionsMap: Map<String, Intersection>
    ): Int {
        if (locations.isEmpty()) {
            return 0
        }

        var counter = 0
        var fromList = locations
        while (!fromList.all { intersectionsMap[it]!!.isTheEnd2() }) {
            fromList = locations.map {
                instructions.fold(it) { acc, instruction ->
                    val currentIntersection =
                        intersectionsMap[acc] ?: throw IllegalArgumentException("Invalid intersection: $acc")
                    currentIntersection.getDirection(instruction)
                }
            }
            counter += instructions.size

            if (counter % 1000 == 0) {
                println(counter)
            }
        }
        return counter
    }

    override fun solvePart2(input: List<String>): String {
        val instruction = Instruction.fromLine(input.first())
        val intersectionMap = buildIntersectionsMap(input.drop(2))
        val initList = intersectionMap.keys.filter { it.last() == INIT.first() }

        val counters = initList.map { walk2(it, instruction, intersectionMap, 0L) }

        return lcm(counters).toString()
    }

    private fun lcm(a: Long, b: Long): Long {
        return a * (b / gcd(a, b))
    }

    private fun lcm(input: List<Long>): Long {
        var result = input[0]
        for (i in 1 until input.size) result = lcm(result, input[i])
        return result
    }

    private fun gcd(a: Long, b: Long): Long {
        var aa = a
        var bb = b
        while (bb > 0) {
            val temp = bb
            bb = aa % bb // % is remainder
            aa = temp
        }
        return aa
    }

    private fun gcd(input: LongArray): Long {
        var result = input[0]
        for (i in 1 until input.size) result = gcd(result, input[i])
        return result
    }
}
