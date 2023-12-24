package dev.ajarac.adventofcode.solution.year_2023

import dev.ajarac.adventofcode.solution.Solution

/*
?###???????? 3,2,1
.###.##.#...
.###.##..#..
.###.##...#.
.###.##....#
.###..##.#..
.###..##..#.
.###..##...#
.###...##.#.
.###...##..#
.###....##.#


n = 3
w = 13 - (3 + 2 + 1) - 3 + 1 = 5

     ?  #  #  #  ?  ?  ?  ?  ?  ?  ?  ?  .
  ┌----------------------------------------
3 |  0  0  0 [0  1  1  1  1] 0  0  0  0  0
2 |  0  0  0  0  0  0 [0  1  2  3  4] 0  0
1 |  0  0  0  0  0  0  0  0 [0  1  3  6 10]
 */

/*
  for (let line of input) {
    let [code, numbers] = line.split(" ");
    numbers = numbers.split(",").map(Number);

    let number = [0];
    let dictionary = code + "?";

    number = number.concat(numbers);

    let counts = [];
    for (let i = 0; i < dictionary.length; i++) {
      counts[i] = [];
    }

    let calculate = (m, n) => {
      if (m == -1 && n == 0) return 1;
      if (counts[m]) return counts[m][n] ?? 0;
      return 0;
    };

    for (let i = 0; i < number.length; i++) {
      for (let j = 0; j < dictionary.length; j++) {
        let cur = 0;
        if (dictionary[j] != "#") cur += calculate(j - 1, i);
        if (i > 0) {
          let d = true;
          for (let k = 1; k <= number[i]; k++) {
            if (dictionary[j - k] == ".") d = false;
          }
          if (dictionary[j] == "#") d = false;
          if (d) cur += calculate(j - number[i] - 1, i - 1);
        }
        counts[j][i] = cur;
      }
    }
    result += counts[dictionary.length - 1][number.length - 1];
  }
  console.log(result);
 */


object Day12Solution : Solution(2023, 12, 1) {
    override fun solvePart1(input: List<String>): String {
        val total = input.asSequence()
            .map { buildSpringLine(it) }
            .sumOf { (arrangements, numbers) -> process(arrangements, numbers) }
        return total.toString()
    }

    private fun process(arrangements: String, numbers: List<Int>): Int {
        var result = 0
        val counts = List(arrangements.length) { IntArray(numbers.size) }
        for (i in numbers.indices) {
            for (j in arrangements.indices) {
                var cur = 0
                if (arrangements[j] != '#') cur += calculate(counts, j - 1, i)
                if (i > 0) {
                    var d = true
                    for (k in 1..numbers[i]) {
                        if (arrangements.getOrNull(j - k) == '.') d = false
                    }
                    if (arrangements[j] == '#') d = false
                    if (d) cur += calculate(counts, j - numbers[i] - 1, i - 1)
                }
                counts[j][i] = cur
            }
        }
        result += counts[arrangements.length - 1][numbers.size - 1]
        return result
    }
//
//    override fun solvePart1(input: List<String>): String {
//
//        var result = 0
//
//        for (line in input) {
//            val (code, numbersStr) = line.split(" ")
//            val numbers = numbersStr.split(",").map { it.toInt() }
//
//            val number = mutableListOf(0)
//            val dictionary = "$code?"
//
//            number += numbers
//
//            val counts = List(dictionary.length) { IntArray(number.size) }
//
//            fun calculate(m: Int, n: Int): Int {
//                if (m == -1 && n == 0) return 1
//                if (counts.getOrNull(m) != null) return counts[m][n]
//                return 0
//            }
//
//            for (i in number.indices) {
//                for (j in dictionary.indices) {
//                    var cur = 0
//                    if (dictionary[j] != '#') cur += calculate(j - 1, i)
//                    if (i > 0) {
//                        var d = true
//                        for (k in 1..number[i]) {
//                            if (dictionary.getOrNull(j - k) == '.') d = false
//                        }
//                        if (dictionary[j] == '#') d = false
//                        if (d) cur += calculate(j - number[i] - 1, i - 1)
//                    }
//                    counts[j][i] = cur
//                }
//            }
//            result += counts[dictionary.length - 1][number.size - 1]
//        }
//        return result.toString()
//    }

    private fun buildSpringLine(line: String, repeats: Int = 1): Pair<String, List<Int>> {
        val split = line.split(" ")
        var arrangements = ""
        repeat(repeats) {
            arrangements += "?"
            arrangements += split.first()
        }
        val numbers = split.last().split(",").map { n -> n.toInt() }
        return Pair(arrangements.plus('.'), numbers)
    }

    private fun calculate(counts: List<IntArray>, m: Int, n: Int): Int {
        if (m == -1 && n == 0) {
            return 1
        }
        if (counts.getOrNull(m) != null) {
            return counts[m][n]
        }
        return 0
    }
}
