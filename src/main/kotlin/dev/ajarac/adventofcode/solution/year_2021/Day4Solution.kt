package dev.ajarac.adventofcode.solution.year_2021

import dev.ajarac.adventofcode.solution.Solution

object Day4Solution : Solution(2021, 4, 1) {

    data class BoardNumber(val value: Int, val x: Int, val y: Int) {
        private var called = false

        fun called() {
            called = true
        }

        fun isCalled() = called
    }

    data class Board(val numbers: List<BoardNumber>) {
        private var hasWon = false

        fun callNumber(wonNumber: Int) {
            if (!hasWon) {
                for (num in numbers) {
                    if (wonNumber == num.value) {
                        num.called()
                    }
                }
            }
            hasWon = hasCompleteRow() || hasCompleteColumn()
        }

        fun hasWon() = hasWon

        fun calcResult(wonNumber: Int): Int {
            return numbers.filterNot { it.isCalled() }
                .sumOf { it.value } * wonNumber
        }

        private fun hasCompleteRow() =
            numbers.groupBy { it.x }.any { (_, value) -> value.all(BoardNumber::isCalled) }

        private fun hasCompleteColumn() =
            numbers.groupBy { it.y }.any { (_, value) -> value.all(BoardNumber::isCalled) }
    }


    private const val SIZE = 5

    override fun solvePart1(input: List<String>): String {
        val wonNumbers = getWonNumbers(input)
        val boardNumbers = getBoardNumbers(input)
        val boards = buildBoards(boardNumbers)

        for (wonNumber in wonNumbers) {
            for (board in boards) {
                board.callNumber(wonNumber)
                if (board.hasWon()) {
                    return board.calcResult(wonNumber).toString()
                }
            }
        }


        return "boards not won"
    }

    private fun calcResult(
        board: Board,
        wonNumber: Int
    ) = (board.numbers.filterNot { it.isCalled() }
        .sumOf { it.value } * wonNumber).toString()

    override fun solvePart2(input: List<String>): String {
        val wonNumbers = getWonNumbers(input)
        val boardNumbers = getBoardNumbers(input)
        val boards = buildBoards(boardNumbers)

        for (wonNumber in wonNumbers) {
            for (board in boards) {
                board.callNumber(wonNumber)
                if (boards.all { it.hasWon() }) {
                    return board.calcResult(wonNumber).toString()
                }
            }
        }

        return "Boards not won"
    }

    private fun buildBoards(boardNumbers: List<Int>): MutableList<Board> {
        val boards = mutableListOf<Board>()
        val numberOfBoards = boardNumbers.size / (SIZE * SIZE)
        repeat(numberOfBoards) { boardIndex ->
            val numbers = mutableListOf<BoardNumber>()
            repeat(SIZE) { rowIndex ->
                repeat(SIZE) { columnIndex ->
                    val numberIndex = 25 * boardIndex + 5 * rowIndex + columnIndex
                    numbers.add(BoardNumber(boardNumbers[numberIndex], rowIndex, columnIndex))
                }
            }

            boards.add(Board(numbers))
        }
        return boards
    }

    private fun getWonNumbers(input: List<String>) =
        input.first().split(",").map(String::toInt)

    private fun getBoardNumbers(input: List<String>) =
        input.subList(2, input.size).flatMap {
            it.split(" ").filterNot(String::isBlank).map(String::toInt)
        }.toList()
}
