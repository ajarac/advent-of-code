package dev.ajarac.adventofcode.util


import kotlin.time.ExperimentalTime
import kotlin.time.measureTimedValue

object BenchmarkUtils {
    @OptIn(ExperimentalTime::class)
    fun <T> getAverageMs(iterations: Int, code: () -> T): BenchmarkResult<T> {
        var count = 0F
        var total = 0F

        val result = code()

        for (i in 1..iterations) {
            val timedValue = measureTimedValue(code)

            total += timedValue.duration.inWholeMilliseconds
            count += 1
        }

        return BenchmarkResult(count, total, total / count, result!!)
    }

    data class BenchmarkResult<T>(
        val count: Float,
        val total: Float,
        val average: Float,
        val result: T
    )
}