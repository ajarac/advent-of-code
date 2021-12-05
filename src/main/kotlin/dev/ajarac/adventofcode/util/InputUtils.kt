package dev.ajarac.adventofcode.util

import java.net.URL

object InputUtils {
    fun getInput(day: Int): URL {
        return ResourceUtils.getResource("/day_${day}")
    }
}