package dev.ajarac.adventofcode.solution.year_2015

import dev.ajarac.adventofcode.solution.Solution

object Day5Solution : Solution(2015, 5, 1) {

    private val vocals = setOf('a', 'e', 'i', 'o', 'u')
    private val badPairs = setOf("ab", "cd", "pq", "xy")

    override fun solvePart1(input: List<String>): String {
        return input.count { isNiceWord1(it) }.toString()
    }

    override fun solvePart2(input: List<String>): String {
        return input.count { isNiceWord2(it) }.toString()
    }

    private fun isNiceWord1(word: String): Boolean {
        var counterVocals = 0
        var containDouble = false
        for (i in word.indices) {
            if (vocals.contains(word[i])) {
                counterVocals++
            }
            if (i > 0 && !containDouble && word[i - 1] == word[i]) {
                containDouble = true
            }
            if (i > 0 && badPairs.contains("${word[i - 1]}${word[i]}")) {
                return false
            }
        }
        return counterVocals >= 3 && containDouble
    }

    private fun isNiceWord2(word: String): Boolean {
        val size = word.length
        val mapPair = mutableMapOf<String, Int>()
        var hasPair = false
        var overlapped = false
        for (i in word.indices) {
            if (i > 0 && !hasPair) {
                val pair = "${word[i - 1]}${word[i]}"
                if (mapPair.containsKey(pair) && mapPair[pair]!! < (i - 1)) {
                    hasPair = true
                } else if (!mapPair.containsKey(pair)) {
                    mapPair[pair] = i
                }
            }
            if (!overlapped && i > 0 && i < size - 1 && word[i - 1] == word[i + 1]) {
                overlapped = true
            }
        }
        return overlapped && hasPair
    }
}
