package dev.ajarac.adventofcode.solution.year_2023

import dev.ajarac.adventofcode.solution.Solution

object Day5Solution : Solution(2023, 5, 1) {

    data class Mapper(val destinationStart: Long, val sourceStart: Long, val range: Long) {
        fun isInRange(value: Long): Boolean {
            return value in sourceStart..(sourceStart + range)
        }

        fun isInRangeBack(value: Long): Boolean {
            return value in destinationStart .. (destinationStart + range)
        }

        // destination = source - sourceStart + destinationStart
        fun map(value: Long): Long {
            return value - sourceStart + destinationStart
        }

        // source = destination - destinationStart + sourceStart
        fun mapBack(value: Long): Long {
            return value - destinationStart + sourceStart
        }
    }

    data class Converter(val from: String, val to: String, val mappers: List<Mapper>) {
        fun map(input: Long): Long {
            val mapperInRange = mappers.find { it.isInRange(input) }
            if (mapperInRange != null) {
                return mapperInRange.map(input)
            }
            return input
        }

        fun mapBack(input: Long): Long {
            val mapperInRange = mappers.find { it.isInRangeBack(input) }
            if (mapperInRange != null) {
                return mapperInRange.mapBack(input)
            }
            return input
        }
    }

    override fun solvePart1(input: List<String>): String {
        val seeds = getSeeds1(input.first())
        val converterMap = buildConverterMap1(input.subList(1, input.size))
        val result = seeds.minOf { walk("seed", it, converterMap) }
        return result.toString()
    }

    override fun solvePart2(input: List<String>): String {
        val converterMap = buildConverterMap2(input.subList(1, input.size))
        val pairSeeds = getSeeds2(input.first())
        var result = 0L
        while (result < Long.MAX_VALUE) {
            val resultBack = walkBack("location", result, converterMap)

            val found = pairSeeds.any { (start, end) -> resultBack in start..end }
            if (found) {
                break
            }
            result++
        }
        return result.toString()
    }

    private fun walk(source: String, input: Long, converterMap: Map<String, Converter>): Long {
        val converter: Converter = converterMap[source] ?: return input

        val result = converter.map(input)

        return walk(converter.to, result, converterMap)
    }

    private fun walkBack(destination: String, input: Long, converterMap: Map<String, Converter>): Long {
        val converter: Converter = converterMap[destination] ?: return input

        val result = converter.mapBack(input)

        return walkBack(converter.from, result, converterMap)
    }

    private fun getSeeds1(line: String): List<Long> {
        return line.split(":").last().trim().split(" ").map { it.toLong() }
    }

    private fun getSeeds2(line: String): List<Pair<Long, Long>> {
        return line
            .split(":").last().trim()
            .split(" ")
            .map { it.toLong() }
            .chunked(2)
            .map { it.first() to it.first() +  it.last() }
    }

    private fun buildConverterMap1(input: List<String>): Map<String, Converter> {
        return buildConverterListBuilder(input).fold(mutableMapOf()) { map, list ->
            val from = list.first().split("-").first()
            map[from] = buildConverter(list)
            map
        }
    }

    private fun buildConverterMap2(input: List<String>): Map<String, Converter> {
        return buildConverterListBuilder(input).fold(mutableMapOf()) { map, list ->
            val to = list.first().split("-").last()
            map[to] = buildConverter(list)
            map
        }
    }

    private fun buildConverterListBuilder(input: List<String>): List<List<String>> {
        return input.filter { it.isNotBlank() }
            .fold(mutableListOf<MutableList<String>>()) { list, line ->
                if (line.contains("map")) {
                    val trip = line.split(" ").first()
                    list.add(mutableListOf(trip))
                } else {
                    list.last().add(line)
                }
                list
            }
    }


    private fun buildConverter(input: List<String>): Converter {
        val trip = input.first().split(" ").first().split("-")
        val mappers = input.subList(1, input.size).map {
            val split = it.split(" ")
            val destinationStart = split[0].toLong()
            val sourceStart = split[1].toLong()
            val range = split[2].toLong()
            Mapper(destinationStart, sourceStart, range)
        }
        return Converter(trip[0], trip[2], mappers)
    }
}
