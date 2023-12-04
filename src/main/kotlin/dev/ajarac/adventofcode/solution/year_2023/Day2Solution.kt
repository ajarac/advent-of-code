package dev.ajarac.adventofcode.solution.year_2023

import dev.ajarac.adventofcode.solution.Solution

enum class Color {
    RED,
    GREEN,
    BLUE;

    companion object {
        fun fromString(color: String): Color {
            return when (color.lowercase()) {
                "red" -> RED
                "green" -> GREEN
                "blue" -> BLUE
                else -> throw IllegalArgumentException("Unknown color $color")
            }
        }
    }
}

data class Play(val mapOfBoxes: Map<Color, Int>) {
    fun isValid(rules: Map<Color, Int>) = mapOfBoxes
        .all { rules[it.key]!! >= it.value }
}

data class Game(val id: Int, val listPlays: List<Play>) {
    fun isValid(rules: Map<Color, Int>) = listPlays
        .all { it.isValid(rules) }

    fun getMinimumBoxesNeeded(): Map<Color, Int> {
        return listPlays.fold(mutableMapOf()) { map, play ->
            play.mapOfBoxes.forEach {
                if (map[it.key] == null) {
                    map[it.key] = 0
                }
                map[it.key] = map[it.key]!!.coerceAtLeast(it.value)
            }
            map
        }
    }
}

object Day2Solution : Solution(2023, 2, 1) {

    private val RULES_VALID_GAME = mapOf(
        Color.RED to 12,
        Color.GREEN to 13,
        Color.BLUE to 14
    )

    private fun buildGame(line: String): Game {
        val split = line.split(":")
        val game = split.first().split(" ").last().toInt()
        val rawPlays = split.last().split(";")
        val listGroupOfPlays = rawPlays.map { buildPlay(it) }
        return Game(game, listGroupOfPlays)
    }

    private fun buildPlay(subSetOfCubes: String): Play {
        return subSetOfCubes.split(",")
            .asSequence()
            .map { boxes -> boxes.trim().split(" ") }
            .fold(mutableMapOf<Color, Int>()) { map, box ->
                val count = box.first().toInt()
                val color = Color.fromString(box.last().trim())
                if (map[color] == null) {
                    map[color] = 0
                }
                map[color] = map[color]!! + count
                map
            }
            .let { Play(it) }
    }

    override fun solvePart1(input: List<String>): String {
        return input
            .sumOf { line ->
                val game = buildGame(line)
                if (game.isValid(RULES_VALID_GAME)) {
                    game.id
                } else {
                    0
                }
            }.toString()

    }

    override fun solvePart2(input: List<String>): String {
        return input.sumOf { line ->
            val game = buildGame(line)
            val minimumBoxesNeeded = game.getMinimumBoxesNeeded()
            var result = 1
            minimumBoxesNeeded.forEach { result *= it.value }
            result
        }.toString()
    }
}
