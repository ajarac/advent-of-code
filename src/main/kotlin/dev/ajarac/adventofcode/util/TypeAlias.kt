package dev.ajarac.adventofcode.util

typealias Point = Pair<Int, Int>

fun Point.str() = "${this.first}-${this.second}"
typealias Matrix = HashMap<Int, HashMap<Int, Int>>

fun InitMatrix(sizeX: Int, sizeY: Int): Matrix {
    val matrix = HashMap<Int, HashMap<Int, Int>>()
    for (i in 0 until sizeY) {
        matrix[i] = HashMap()
        for (j in 0 until sizeX) {
            matrix[i]!![j] = 0
        }
    }
    return matrix
}

fun Matrix.has(point: Point): Boolean =
    this[point.first] != null && this[point.first]!![point.second] != null

fun Matrix.getValue(point: Point) = this[point.first]!![point.second]!!
fun Matrix.getValue(x: Int, y: Int) = this[y]!![x]!!
fun Matrix.setValue(x: Int, y: Int, value: Int) {
    this[y]!![x] = value
}

fun Matrix.setValue(point: Point, value: Int) {
    this[point.first]!![point.second] = value
}

fun Matrix.increment(point: Point) {
    val value = this[point.first]!![point.second] ?: 0
    this[point.first]!![point.second] = value + 1
}
