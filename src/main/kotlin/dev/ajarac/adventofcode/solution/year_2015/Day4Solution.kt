package dev.ajarac.adventofcode.solution.year_2015

import dev.ajarac.adventofcode.solution.Solution
import java.security.MessageDigest

object Day4Solution : Solution(2015, 4, 1) {

    private const val NUMBER_OF_ZEROS_PART_1 = 5
    private const val NUMBER_OF_ZEROS_PART_2 = 6

    override fun solvePart1(input: List<String>): String {
        return searchNumber(input.first(), NUMBER_OF_ZEROS_PART_1)
    }

    override fun solvePart2(input: List<String>): String {
        return searchNumber(input.first(), NUMBER_OF_ZEROS_PART_2)
    }

    private fun searchNumber(secret: String, numOfZeros: Int): String {
        var number = 0
        while (!isValidHash(secret, number, numOfZeros)) {
            number++
        }
        return "$number"
    }

    private fun isValidHash(secret: String, number: Int, numOfZeros: Int) = MessageDigest
        .getInstance("MD5")
        .digest("$secret$number".toByteArray())
        .fold("") { str, it -> str + "%02x".format(it) }
        .startsWith("0".repeat(numOfZeros))

}
