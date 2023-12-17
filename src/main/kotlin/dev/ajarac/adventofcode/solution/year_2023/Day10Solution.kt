package dev.ajarac.adventofcode.solution.year_2023

import dev.ajarac.adventofcode.solution.Solution
import dev.ajarac.adventofcode.util.InitMatrix
import dev.ajarac.adventofcode.util.Matrix
import dev.ajarac.adventofcode.util.getValue
import dev.ajarac.adventofcode.util.setValue

typealias Grid = HashMap<Int, HashMap<Int, Day10Solution.Points>>

object Day10Solution : Solution(2023, 10, 1) {
    data class Point(val x: Int, val y: Int)

    enum class Points {
        HORIZONTAL,
        VERTICAL,
        NORTH_EAST,
        NORTH_WEST,
        SOUTH_EAST,
        SOUTH_WEST,
        START,
        EMPTY;

        fun move(previousPoint: Point, point: Point): Point {
            return when (this) {
                HORIZONTAL -> {
                    if (previousPoint.y > point.y) {
                        Point(point.x, point.y - 1)
                    } else {
                        Point(point.x, point.y + 1)
                    }
                }

                VERTICAL -> {
                    if (previousPoint.x > point.x) {
                        Point(point.x - 1, point.y)
                    } else {
                        Point(point.x + 1, point.y)
                    }
                }

                NORTH_EAST -> {
                    if (previousPoint.x == point.x) {
                        Point(point.x + 1, point.y)
                    } else {
                        Point(point.x, point.y - 1)
                    }
                }

                NORTH_WEST -> {
                    if (previousPoint.x == point.x) {
                        Point(point.x - 1, point.y)
                    } else {
                        Point(point.x, point.y - 1)
                    }
                }

                SOUTH_EAST -> {
                    if (previousPoint.x == point.x) {
                        Point(point.x + 1, point.y)
                    } else {
                        Point(point.x, point.y + 1)
                    }
                }

                SOUTH_WEST -> {
                    if (previousPoint.x == point.x) {
                        Point(point.x - 1, point.y)
                    } else {
                        Point(point.x, point.y + 1)
                    }
                }

                START -> Point(point.x, point.y)
                EMPTY -> throw IllegalArgumentException("Invalid point: $this")
            }
        }

        companion object {
            fun fromChar(char: Char) = when (char) {
                '|' -> HORIZONTAL
                '-' -> VERTICAL
                'L' -> NORTH_EAST
                'J' -> NORTH_WEST
                '7' -> SOUTH_WEST
                'F' -> SOUTH_EAST
                'S' -> START
                else -> EMPTY
            }
        }
    }

    private fun buildGrid(input: List<String>): Grid {
        val grid = Grid()
        input.forEachIndexed { y, line ->
            grid[y] = HashMap(line.length)
            line.forEachIndexed { x, char ->
                val pipe = Points.fromChar(char)
                grid[y]!![x] = pipe
            }
        }
        return grid
    }

    data class Path(var previousPoint: Point, var currentPoint: Point) {
        fun nextPoint(grid: Grid): Point? {
            val pipe = grid.getPipeOrNull(currentPoint)
            if (pipe == null || pipe == Points.EMPTY || pipe == Points.START) {
                return null
            }
            return pipe.move(previousPoint, currentPoint)
        }

        fun setNextPoint(point: Point) {
            this.previousPoint = this.currentPoint
            this.currentPoint = point
        }
    }

    override fun solvePart1(input: List<String>): String {
        val grid = buildGrid(input)
        val maxPath = initWalk(grid)
        return maxPath.toString()
    }

    private fun initWalk(grid: Grid): Int {
        val animal = grid.getStart()
        val paths = grid.getSurroundedPoints(animal).map { Path(animal, it) }

        return paths.maxOf {
            val sizeY = grid.size
            val sizeX = grid[grid.keys.first()]!!.size
            val visits = InitMatrix(sizeX, sizeY)
            visits.setValue(it.currentPoint.x, it.currentPoint.y, 1)
            walk(grid, it, visits)
        }.div(2)
    }

    private fun walk(grid: Grid, path: Path, visits: Matrix): Int {
        var counter = 1
        while (grid.getPipeOrNull(path.currentPoint) != Points.START) {
            val nextPoint = path.nextPoint(grid) ?: return 0
            val visited = visits.getValue(nextPoint.x, nextPoint.y)

            if (visited > 0) {
                return 0
            }

            val nextPipe = grid.getPipeOrNull(nextPoint) ?: return 0

            if (nextPipe == Points.EMPTY) {
                return 0
            }
            counter++
            visits.setValue(Pair(nextPoint.y, nextPoint.x), counter)
            path.setNextPoint(nextPoint)
        }
        return counter
    }


    override fun solvePart2(input: List<String>): String {
        val grid = buildGrid(input)
        val loop = buildLoopGrip(grid)
        val tiles = countTiles(loop)
        return tiles.toString()
    }

    private fun buildLoopGrip(grid: Grid): Matrix {
        val animal = grid.getStart()
        val paths = grid.getSurroundedPoints(animal).map { Path(animal, it) }

        var longerLoop: Matrix? = null;
        var longerSteps = 0
        for (path in paths) {
            val sizeY = grid.size
            val sizeX = grid[grid.keys.first()]!!.size
            val visits = InitMatrix(sizeX, sizeY)
            visits.setValue(path.currentPoint.x, path.currentPoint.y, 1)
            val steps = walk(grid, path, visits)
            if (steps > longerSteps) {
                longerSteps = steps
                longerLoop = visits
            }
        }

        return longerLoop!!
    }

    private fun countTiles(matrix: Matrix): Int {
        var tiles = 0
        for (line in matrix.values) {
            var buffer = 0
            for (value in line.values) {
                if (value == 0) {
                    if (buffer > 0) {
                        tiles++
                    } else {
                        buffer = 0
                    }
                }

                if (value > 0) {
                    if (buffer == 0) {
                        buffer++
                    } else {
                        buffer = 0
                    }
                } else {
                    if (buffer > 0) {
                        tiles++
                        // buffer = 0
                    } else {
                        buffer = 0
                    }
                }
            }
        }
        return tiles
    }

    private fun Grid.getPipeOrNull(point: Point): Points? {
        if (this[point.y] == null) {
            return null
        }
        if (this[point.y]!![point.x] == null) {
            return null
        }
        return this[point.y]!![point.x]
    }

    private fun Grid.getStart(): Point {
        this.forEach { (y, line) ->
            line.forEach { (x, pipe) ->
                if (pipe == Points.START) {
                    return Point(x, y)
                }
            }
        }
        throw IllegalArgumentException("No start found")
    }

    private fun Grid.getSurroundedPoints(point: Point): List<Point> {
        val pointsToCheck = mutableListOf(
            Point(point.x, point.y - 1),
            Point(point.x, point.y + 1),
            Point(point.x - 1, point.y),
            Point(point.x + 1, point.y)
        )
        return pointsToCheck
            .filter { (x, y) -> this[y]?.get(x) != null }
            .filter { (x, y) -> this[y]!![x] != Points.EMPTY }
    }

    private fun <T> HashMap<Int, HashMap<Int, T>>.str(): String {
        val sb = StringBuilder()
        this.forEach { (_, line) ->
            line.forEach { (_, v) ->
                sb.append(".$v.")
            }
            sb.append("\n")
        }
        return sb.toString()
    }
}
