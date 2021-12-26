package dev.ajarac.adventofcode.solution

object Day14Solution : Solution(2021, 14, 1) {

    override fun solvePart1(input: List<String>): String {
        var polymer = buildPolymer(input)
        val counterLetters = buildCounterLetters(input)
        val rules = buildRules(input)
        repeat(10) {
            polymer = iterateRules(polymer, rules, counterLetters)
        }
        return calcResult(counterLetters).toString()
    }

    override fun solvePart2(input: List<String>): String {
        var polymer = buildPolymer(input)
        val counterLetters = buildCounterLetters(input)
        val rules = buildRules(input)
        repeat(40) {
            polymer = iterateRules(polymer, rules, counterLetters)
        }
        return calcResult(counterLetters).toString()
    }

    private fun buildPolymer(input: List<String>): HashMap<String, Long> {
        val hashMap = hashMapOf<String, Long>()
        val polymer = input.first()
        for (i in 1 until polymer.length) {
            update(hashMap, polymer[i - 1].toString() + polymer[i]).toString()
        }
        return hashMap
    }

    private fun buildCounterLetters(input: List<String>) =
        input.first().fold(hashMapOf<Char, Long>()) { map, char ->
            update(map, char)
            map
        }

    private fun buildRules(input: List<String>) =
        input.subList(2, input.size)
            .map { it.split(" -> ") }
            .fold(hashMapOf<String, String>()) { hashMap, (first, second) ->
                hashMap[first] = second
                hashMap
            }

    private fun iterateRules(
        polymer: HashMap<String, Long>,
        rules: HashMap<String, String>,
        counterLetters: HashMap<Char, Long>
    ): HashMap<String, Long> {
        return polymer.keys.fold(hashMapOf()) { hashMap, it ->
            val counter = polymer[it]!!
            val letter = rules[it]!!
            val letterChar = letter.toCharArray().first()
            update(counterLetters, letterChar, counter)
            update(hashMap, it.first().toString() + letter, counter)
            update(hashMap, letter + it.last().toString(), counter)
            hashMap
        }
    }


    private fun calcResult(polymer: HashMap<Char, Long>): Long {
        val counter = polymer.toList().fold(hashMapOf<Char, Long>()) { hashMap, (key, count) ->
            update(hashMap, key, count)
            hashMap
        }
        return counter.maxOf { it.value } - polymer.minOf { it.value }
    }

    private fun <T> update(hashMap: HashMap<T, Long>, key: T, toSum: Long = 1L) {
        hashMap[key] = hashMap.getOrDefault(key, 0L) + toSum
    }
}