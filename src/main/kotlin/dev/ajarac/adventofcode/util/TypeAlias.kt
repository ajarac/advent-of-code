package dev.ajarac.adventofcode.util

typealias Point = Pair<Int, Int>

fun Point.str() = "${this.first}-${this.second}"
typealias Matrix = HashMap<Int, HashMap<Int, Int>>

fun Matrix.has(point: Point): Boolean =
    this[point.first] != null && this[point.first]!![point.second] != null

fun Matrix.getValue(point: Point) = this[point.first]!![point.second]!!
fun Matrix.setValue(point: Point, value: Int) {
    this[point.first]!![point.second] = value
}
fun Matrix.increment(point: Point) {
    val value = this[point.first]!![point.second] ?: 0
    this[point.first]!![point.second] = value + 1
}