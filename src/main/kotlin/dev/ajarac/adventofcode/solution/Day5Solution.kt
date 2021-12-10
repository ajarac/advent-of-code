package dev.ajarac.adventofcode.solution

import dev.ajarac.adventofcode.util.Point
import dev.ajarac.adventofcode.util.str

object Day5Solution : Solution(2021, 5, 1) {

    override fun solvePart1(input: List<String>): String {
        val lines = input.map { Line.create(it) }
        val mapPoints = hashMapOf<String, Int>()

        lines.forEach { line ->
            val pointA = line.pointA
            val pointB = line.pointB
            if (pointA.first == pointB.first) {
                val from = if (pointA.second < pointB.second) pointA.second else pointB.second
                val to = if (pointA.second > pointB.second) pointA.second else pointB.second
                for (y in from..to) {
                    val point = Point(pointA.first, y)
                    mapPoints[point.str()] = mapPoints[point.str()]?.inc() ?: 1
                }
            }
            if (pointA.second == pointB.second) {
                val from = if (pointA.first < pointB.first) pointA.first else pointB.first
                val to = if (pointA.first > pointB.first) pointA.first else pointB.first
                for (x in from..to) {
                    val point = Point(x, pointA.second)
                    mapPoints[point.str()] = mapPoints[point.str()]?.inc() ?: 1
                }
            }
        }
        val result = mapPoints.filter { it.value >= 2 }.count()
        return "$result"
    }

    override fun solvePart2(input: List<String>): String {
        val lines = input.map { Line.create(it) }
        val mapPoints = hashMapOf<String, Int>()
        lines.forEach { line ->
            val pointA = line.pointA
            val pointB = line.pointB
            if (pointA.first == pointB.first) {
                val from = if (pointA.second < pointB.second) pointA.second else pointB.second
                val to = if (pointA.second > pointB.second) pointA.second else pointB.second
                for (y in from..to) {
                    val point = Point(pointA.first, y)
                    mapPoints[point.str()] = mapPoints[point.str()]?.inc() ?: 1
                }
            } else if (pointA.second == pointB.second) {
                val from = if (pointA.first < pointB.first) pointA.first else pointB.first
                val to = if (pointA.first > pointB.first) pointA.first else pointB.first
                for (x in from..to) {
                    val point = Point(x, pointA.second)
                    mapPoints[point.str()] = mapPoints[point.str()]?.inc() ?: 1
                }
            } else {
                val from: Point = if (pointA.first < pointB.first) pointA else pointB
                val to: Point = if (pointA.first < pointB.first) pointB else pointA
                val diff = to.first - from.first
                for (it in 0..diff) {
                    val secondValue =
                        if (from.second < to.second) from.second + it else from.second - it
                    val point = Point(from.first + it, secondValue)
                    mapPoints[point.str()] = mapPoints[point.str()]?.inc() ?: 1
                }
            }
        }
        val result = mapPoints.filter { it.value >= 2 }.count()
        return "$result"
    }
}


data class Line(val pointA: Point, val pointB: Point) {
    companion object {
        fun create(lineString: String): Line {
            val points = lineString.split(" -> ").map {
                val pairs = it.split(",").map(String::toInt)
                Point(pairs.first(), pairs.last())
            }
            return Line(points.first(), points.last())
        }
    }
}