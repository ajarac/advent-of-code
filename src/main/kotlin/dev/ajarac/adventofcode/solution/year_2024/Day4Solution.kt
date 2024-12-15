package dev.ajarac.adventofcode.solution.year_2024

import dev.ajarac.adventofcode.solution.Solution

object Day4Solution : Solution(2024, 4, 1) {
    private val directions = listOf(
        Pair(0, 1),
        Pair(0, -1),
        Pair(1, 0),
        Pair(-1, 0),
        Pair(1, 1),
        Pair(-1, -1),
        Pair(1, -1),
        Pair(-1, 1)
    )
    
    private fun inBounds(r: Int, c: Int, rows: Int, cols: Int) = r in 0 until rows && c in 0 until cols

    override fun solvePart1(input: List<String>): String {
        val grid = input
        val rows = grid.size
        val cols = if (rows == 0) 0 else grid[0].length
        val word = "XMAS"
        val wordLen = word.length
        var count = 0

        for (r in 0 until rows) {
            for (c in 0 until cols) {
                if (grid[r][c] == word[0]) {
                    // Check all directions
                    for ((dr, dc) in directions) {
                        var nr = r
                        var nc = c
                        var matched = true
                        for (i in 1 until wordLen) {
                            nr += dr
                            nc += dc
                            if (!inBounds(nr, nc, rows, cols) || grid[nr][nc] != word[i]) {
                                matched = false
                                break
                            }
                        }
                        if (matched) {
                            count++
                        }
                    }
                }
            }
        }

        return count.toString()
    }

    override fun solvePart2(input: List<String>): String {
        val grid = input
        val rows = grid.size
        val cols = if (rows == 0) 0 else grid[0].length

        var count = 0

        for (r in 1 until rows - 1) {
            for (c in 1 until cols - 1) {
                if (grid[r][c] == 'A') {
                    val tl = if (inBounds(r - 1, c - 1, rows, cols)) grid[r - 1][c - 1] else ' '
                    val br = if (inBounds(r + 1, c + 1, rows, cols)) grid[r + 1][c + 1] else ' '
                    val tr = if (inBounds(r - 1, c + 1, rows, cols)) grid[r - 1][c + 1] else ' '
                    val bl = if (inBounds(r + 1, c - 1, rows, cols)) grid[r + 1][c - 1] else ' '

                    // Check if diagonal1 is MAS or SAM
                    val diag1 = "" + tl + 'A' + br
                    val diag2 = "" + tr + 'A' + bl

                    val validDiag = { d: String -> d == "MAS" || d == "SAM" }

                    if (validDiag(diag1) && validDiag(diag2)) {
                        count++
                    }
                }
            }
        }

        return count.toString()
    }

}